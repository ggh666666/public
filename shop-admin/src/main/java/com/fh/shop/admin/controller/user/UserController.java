package com.fh.shop.admin.controller.user;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.fh.shop.admin.biz.user.IUserService;
import com.fh.shop.admin.biz.wealth.IWealthService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.Log;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.user.UserPasswordParam;
import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.util.*;
import com.fh.shop.admin.vo.user.UserVo;
import com.itextpdf.text.DocumentException;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private HttpServletResponse response;

    @Resource(name = "userService")
    private IUserService userService;

    @Resource(name = "wealthService")
    private IWealthService wealthService;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * 登录
     *
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public ServerResponse login(User user, HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        if (StringUtils.isEmpty(code))
            return ServerResponse.error(ResponseEnum.CODE_IS_NULL);
        //验证验证码
        String sessionId = DistributedSession.getSessionId(request, response);
        String code2 = RedisUtil.get(KeyUtil.buildCodeKey(sessionId));
        if (!code.equalsIgnoreCase(code2)) {
            return ServerResponse.error(ResponseEnum.CODE_IS_ERROR);
        }

        String userName = user.getUserName();
        String password = user.getPassword();
        //判断用户和密码不能为空
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return ServerResponse.error(ResponseEnum.USERNAME_PASSWORD_IS_EMPTY);
        }
        //验证用户名

        User userDb = userService.findUserByUserName(userName);
        if (userDb == null) {
            return ServerResponse.error(ResponseEnum.USERNAME_IS_ERROR);
        }
        //判断锁定状态
        String lastErrorDate = DateUtil.date2str(userDb.getLastErrorTime(), DateUtil.Y_M_D);
        String today = DateUtil.date2str(new Date(), DateUtil.Y_M_D);
        if (userDb.getLockState() == ResponseEnum.USER_IS_LOCK.getCode()) {
            if (!lastErrorDate.equals(today)) {//如果最后输错日期不是今天则解锁用户
                userDb.setErrors(0);
                userService.updateLockInfo(0, 0, userDb.getId());
            } else
                return ServerResponse.error(ResponseEnum.USER_IS_LOCK);
        } else {
            //如果错误次数不为0
            //并且最后输错日期不是今天  说明之前密码输错过 更新锁定信息
            if (userDb.getErrors() != 0 && !lastErrorDate.equals(today)) {
                userDb.setErrors(0);
                userService.updateLockInfo(0, 0, userDb.getId());
            }
        }

        //验证密码
        if (!Md5Util.salt_md5_2(password, userDb.getSalt()).equals(userDb.getPassword())) {
            int errors = userDb.getErrors();//密码错误次数
            errors++;//密码错误次数+1
            int lockState = 0;
            if (errors >= SystemConst.IF_ERRORS_LOCK)//如果密码错误次数大于等于需锁定的次数则锁定用户
            {
                lockState = ResponseEnum.USER_IS_LOCK.getCode();
                //给用户发邮件提示用户被锁定
                EmailUtil.sendEmail(userDb.getEmail());

            }

            //更新锁定信息
            userService.updateLockInfo(errors, lockState, userDb.getId());

            return ServerResponse.error(ResponseEnum.PASSWORD_IS_ERROR);
        }


        //登陆成功 更新锁定信息
        //如果错误次数不为0 说明之前密码输错过 更新锁定信息
        if (userDb.getErrors() != 0)
            userService.updateLockInfo(0, 0, userDb.getId());

        //登录成功后 记录今天登录次数 并 更新最后登录时间
        Integer todayLogins = userDb.getTodayLogins();
        String lastLoginDate = DateUtil.date2str(userDb.getLastLoginTime(), DateUtil.Y_M_D);//上次登录日期
        String todayDate = DateUtil.date2str(new Date(), DateUtil.Y_M_D);//今天的日期
        if (lastLoginDate.equals(todayDate)) {//如果上次登录日期是今天就把今天登录次数+1
            todayLogins++;
            userDb.setTodayLogins(todayLogins);
            userService.updateLoginInfo(todayLogins, userDb.getId());//更新登录信息
        } else {//如果不是今天则把次数设为1
            todayLogins = 1;
            userDb.setTodayLogins(todayLogins);
            userService.updateLoginInfo(todayLogins, userDb.getId());//更新登录信息
        }
        //登录成功
        userDb.setPassword(null);//不把密码存到session
//        request.getSession().setAttribute(SystemConst.CURR_USER, userDb);
        //分布式session    cookie + redis
        String userDbJson = JSON.toJSONString(userDb);
        RedisUtil.setEx(KeyUtil.buildCurrUserKey(sessionId), userDbJson, SystemConst.EXPIRE);
//        request.getSession().setAttribute("lastLoginTime", DateUtil.date2str(userDb.getLastLoginTime(), DateUtil.FULL_YEAR));

        //分布式session    cookie + redis
        RedisUtil.setEx(KeyUtil.buildLastLoginTimeKey(sessionId), DateUtil.date2str(userDb.getLastLoginTime(), DateUtil.FULL_YEAR), SystemConst.EXPIRE);


        //将当前用户的资源信息放入session 用于重复获取 减少数据库压力
        //根据用户信息查询相关菜单
        List<Wealth> wealths = userService.getMenu(userDb.getId());
//        request.getSession().setAttribute(SystemConst.CURR_USER_MENU_INFO, wealths);
        //分布式session    cookie + redis
        RedisUtil.setEx(KeyUtil.buildCurrUserMenuInfoKey(sessionId), JSON.toJSONString(wealths), SystemConst.EXPIRE);


        //将所有资源信息放入到session中
        List<Wealth> wealthList = wealthService.findAllList();
//        request.getSession().setAttribute(SystemConst.MENU_ALL_INFO, wealthList);
        //分布式session    cookie + redis
        RedisUtil.setEx(KeyUtil.buildMenuAllInfo(sessionId), JSON.toJSONString(wealthList), SystemConst.EXPIRE);


        //将当前用户的资源url放入session 用于拦截器判断
        List<String> menuUrls = userService.getMenuUrls(userDb.getId());
//        request.getSession().setAttribute(SystemConst.CURR_USER_MENU_URLS, menuUrls);
        //分布式session    cookie + redis
        RedisUtil.setEx(KeyUtil.buildCurrUserMenuUrls(sessionId), StringUtils.join(menuUrls, ","), SystemConst.EXPIRE);
        //登录成功后删除redis中的验证码
        RedisUtil.del(KeyUtil.buildCodeKey(sessionId));

        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ServerResponse.success();
    }

    /**
     * 获取登录信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginInfo")
    @ResponseBody
    public ServerResponse loginInfo(HttpServletRequest request) {
        //分布式session    cookie + redis
        String sessionId = DistributedSession.getSessionId(request, response);
        String userJson = RedisUtil.get(KeyUtil.buildCurrUserKey(sessionId));
        User user = JSON.parseObject(userJson, User.class);

        String lastLoginTime = RedisUtil.get(KeyUtil.buildLastLoginTimeKey(sessionId));

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("lastLoginTime", lastLoginTime);
        return ServerResponse.success(map);
    }

    /**
     * 判断用户唯一
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/userUnique")
    @ResponseBody
    public String userUnique(String userName) {
        User user = userService.findUserByUserName(userName);
        if (user == null)
            return "{ \"valid\": true }";
        else
            return "{ \"valid\": false }";
    }

    /**
     * 获取菜单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMenu")
    @ResponseBody
    public ServerResponse getMenu(HttpServletRequest request) {
        //从session取出资源信息
//        List<Wealth> wealths = (List<Wealth>) request.getSession().getAttribute(SystemConst.CURR_USER_MENU_INFO);
        //分布式session    cookie + redis
        String sessionId = DistributedSession.getSessionId(request, response);
        String wealthsJson = RedisUtil.get(KeyUtil.buildCurrUserMenuInfoKey(sessionId));
        List<Wealth> wealths = JSON.parseArray(wealthsJson, Wealth.class);

        return ServerResponse.success(wealths);
    }

    /**
     * 注销
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    //@ResponseBody
//    public ServerResponse logout(HttpServletRequest request){
    public String logout(HttpServletRequest request) {
//        request.getSession().invalidate();//使session失效 失效的同时浏览器会立刻创建新的session
        //分布式session    cookie + redis  清空
        String sessionId = DistributedSession.getSessionId(request, response);
        DistributedSession.invalidate(sessionId);//根据sessionId 清空session数据

//        return ServerResponse.success();
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return "redirect:/";
    }

    /**
     * 导出excel
     *
     * @param userSearchParam
     * @param response
     */
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(UserSearchParam userSearchParam, HttpServletResponse response) {
        //查询用户列表
        List<UserVo> list = userService.list(userSearchParam);
        String[] headers = new String[]{"序号", "用户名", "真实姓名", "年龄", "薪资", "入职时间", "电话", "邮箱", "角色", "地区"};
        String[] props = new String[]{"id", "userName", "realName", "age", "pay", "entryTime", "phone", "email", "roleNames", "areaInfo"};
        XSSFWorkbook xssfw = ExcelUtil.buildWorkbook(list, "用户列表", headers, props, UserVo.class);

        //构建Workbook
//        XSSFWorkbook xssfw = userService.buildWorkbook(userSearchParam);
        //导出
        FileUtil.excelDownload(xssfw, response);
    }

    /**
     * 导出pdf
     *
     * @param userSearchParam
     * @param response
     */
    @RequestMapping(value = "/exportPdf")
    public void exportPdf(UserSearchParam userSearchParam, HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        //构建一个字节流数组
        ByteArrayOutputStream byo = userService.buildPdfOS(userSearchParam);
        PDFFileUtil.pdfDownload(response, byo);
    }

    /**
     * 导出word
     *
     * @param userSearchParam
     * @param response
     */
    @RequestMapping(value = "/exportWord")
    public void exportWord(UserSearchParam userSearchParam, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {
        File file = userService.buildWordFile(userSearchParam);
        // 调用下载方法
        FileUtil.downloadFile(request, response, file.getPath(), file.getName());
        //删除垃圾文件
        file.delete();
    }

    /**
     * 去添加页面
     *
     * @return
     */
    @RequestMapping(value = "/toAdd")
    public String toAdd() {
        return "user/add";
    }

    /**
     * 添加
     *
     * @param user
     * @return
     */
    @Log("添加用户")
    @RequestMapping(value = "/add")
    public String add(User user) {
        userService.add(user);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return "redirect:/user/list.jhtml";
    }

    /**
     * ajax添加
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/addAjax")
    @ResponseBody
    @Log("添加用户")
    public String addAjax(User user) {
        userService.add(user);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return "1";
    }

    /**
     * 去列表展示页面
     *
     * @return
     */
    @RequestMapping(value = "/toList")
    public String toList() {
        return "user/list";
    }

    /**
     * 查询list数据
     *
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    //public Map list(UserSearchParam userSearchParam, @RequestParam("ids[]")ArrayList<Long> ids){
//    public Map list(UserSearchParam userSearchParam) {
//    public DataTableResult list(UserSearchParam userSearchParam) {
    public ServerResponse list(UserSearchParam userSearchParam) {
        Map<String, Object> map = new HashMap<>();
        Long recordsTotal = userService.getRecordsTotal(userSearchParam);
        List<UserVo> list = userService.getList(userSearchParam);
//        map.put("draw", userSearchParam.getDraw());
//        map.put("recordsTotal", recordsTotal);
//        map.put("recordsFiltered", recordsTotal);
//        map.put("data", list);
        DataTableResult dataTableResult = new DataTableResult(userSearchParam.getDraw(), recordsTotal, recordsTotal, list);
        return ServerResponse.success(dataTableResult);
//        return map;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    @Log("删除用户")
    public ServerResponse delete(Long id) {
        userService.delete(id);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ServerResponse.success();
    }

    /**
     * 解锁用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/unlockUser")
    @ResponseBody
    public ServerResponse unlockUser(Long id) {
        userService.updateLockInfo(0, 0, id);
        return ServerResponse.success();
    }

    /**
     * 去修改密码页面
     *
     * @return
     */
    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword() {
        return "/user/password";
    }

    /**
     * 修改密码
     *
     * @param userPasswordParam
     * @return
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    @Log("修改密码")
    public ServerResponse updatePassword(UserPasswordParam userPasswordParam) {
        return userService.updatePassword(userPasswordParam);
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/resetPassword")
    @ResponseBody
    public ServerResponse resetPassword(Long id) throws Exception {
        UserVo user = userService.findUser(id);
        if (user == null)
            return ServerResponse.error(ResponseEnum.USER_NOT_EXIST);
        String newPassword = userService.resetPassword(id);

        //发送邮件
        EmailUtil.sendEmail(user.getEmail(), "新密码为:" + newPassword);
        return ServerResponse.success();
    }

    /**
     * 忘记密码
     *
     * @param email
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/forgetPassword")
    @ResponseBody
    public ServerResponse forgetPassword(String email) throws Exception {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return ServerResponse.error(ResponseEnum.EMAIL_IS_ERROR);
        }
        //重置密码
        String newPassword = userService.resetPassword(user.getId());
        //发送邮件
        EmailUtil.sendEmail(user.getEmail(), "新密码为:" + newPassword);
        return ServerResponse.success();
    }

    /**
     * 回显
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "findUser")
    @ResponseBody
    public ServerResponse findUser(Long id) {
        "".indexOf(1);
        //Map map = new HashMap();
        UserVo user = userService.findUser(id);
        return ServerResponse.success(user);
//            map.put("code",1200);
//            map.put("msg","回显成功");
//            map.put("data",user);
    }

    /**
     * ajax修改
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "updateAjax")
    @ResponseBody
    @Log("修改用户")
    public ServerResponse updateAjax(User user) {
        userService.update(user);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));

        return ServerResponse.success(user);

    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public void batchDelete(@RequestParam(value = "ids[]") Long[] ids) {
        userService.batchDelete(ids);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
    }
}
