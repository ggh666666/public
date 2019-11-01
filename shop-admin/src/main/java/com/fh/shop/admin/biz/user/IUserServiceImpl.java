package com.fh.shop.admin.biz.user;

import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.user.IUserMapper;
import com.fh.shop.admin.param.user.UserPasswordParam;
import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.po.user.UserRole;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.util.Md5Util;
import com.fh.shop.admin.util.PDFFileUtil;
import com.fh.shop.admin.util.SystemConst;
import com.fh.shop.admin.vo.user.UserVo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service(value = "userService")
public class IUserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public void add(User user) {
        //salt+双重md5
        String[] ss = Md5Util.salt_md5_2(user.getPassword());
        user.setSalt(ss[0]);
        user.setPassword(ss[1]);
        userMapper.add(user);
        //添加完用户后添加用户角色中间表
        addUserRole(user);
    }

    /**
     * 添加用户角色
     *
     * @param user
     */
    private void addUserRole(User user) {
        List<Integer> roleIds = user.getRoleIds();
        Long userId = user.getId();
        List<UserRole> list = new ArrayList<>();
        if (roleIds != null) {
            for (Integer roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                list.add(userRole);
            }
            //批量添加
            userMapper.addUserRole(list);
        }
    }

    @Override
    public Long getRecordsTotal(UserSearchParam userSearchParam) {
        return userMapper.getRecordsTotal(userSearchParam);
    }

    @Override
    public List<UserVo> list(UserSearchParam userSearchParam) {
        List<UserVo> list = new ArrayList<>();
        List<User> userList = userMapper.getList(userSearchParam);
        convertUserVoList(list, userList);
        return list;
    }

    /**
     * po转vo list
     *
     * @param user1
     * @return
     */
    private UserVo convertUserVo(User user1) {
        if (user1 == null)
            return null;
        UserVo userVo = new UserVo();
        userVo.setId(user1.getId());
        userVo.setUserName(user1.getUserName());
        userVo.setRealName(user1.getRealName());
        userVo.setSex(user1.getSex());
        userVo.setAge(user1.getAge());
        userVo.setPay(user1.getPay());
        userVo.setEntryTime(DateUtil.date2str(user1.getEntryTime(), DateUtil.Y_M_D));
        userVo.setPhone(user1.getPhone());
        userVo.setEmail(user1.getEmail());
        userVo.setLock(user1.getErrors() == SystemConst.IF_ERRORS_LOCK);

        //
        userVo.setArea1(user1.getArea1());
        userVo.setArea2(user1.getArea2());
        userVo.setArea3(user1.getArea3());
        userVo.setArea4(user1.getArea4());
        userVo.setAreaInfo(user1.getAreaInfo());
        return userVo;
    }

    @Override
    public void delete(Long id) {
        userMapper.delete(id);
        //删除user role中间表的数据
        userMapper.deleteUserRole(id);
    }

    @Override
    public UserVo findUser(Long id) {
        User user = userMapper.findUser(id);
        //根据用户id查询出此用户的角色
        List<Integer> roleIds = userMapper.findRoleIds(id);
        UserVo userVo = convertUserVo(user);
        if (userVo != null)
            userVo.setRoleIds(roleIds);
        return userVo;
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
        //先删除后添加
        userMapper.deleteUserRole(user.getId());
        if (user.getRoleIds() != null && user.getRoleIds().size() > 0)
            addUserRole(user);
//        throw new RuntimeException();
    }

    @Override
    public void batchDelete(Long[] ids) {
        userMapper.batchDelete(ids);
        //删除user role中间表的数据
        userMapper.batchDeleteUserRole(ids);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userMapper.findUserByUserName(userName);
    }

    @Override
    public void updateLoginInfo(Integer todayLogins, Long id) {
        userMapper.updateLoginInfo(todayLogins, id);
    }

    @Override
    public void updateLockInfo(int errors, int lockState, Long id) {
        userMapper.updateLockInfo(errors, lockState, id);
    }

    @Override
    public List<UserVo> getList(UserSearchParam userSearchParam) {
        List<UserVo> list = new ArrayList<>();
        List<User> userList = userMapper.list(userSearchParam);
        convertUserVoList(list, userList);
        return list;
    }

    @Override
    public XSSFWorkbook buildWorkbook(UserSearchParam userSearchParam) {
        //查询用户列表
        List<UserVo> list = list(userSearchParam);
        //创建工作目录
        XSSFWorkbook xssfw = new XSSFWorkbook();
        //创建样式
        XSSFCellStyle style = xssfw.createCellStyle();
        XSSFFont font = xssfw.createFont();
        font.setBold(true);//是否加粗
        font.setColor(IndexedColors.WHITE.index);//设置字体颜色
        style.setFont(font);//将字体应用到样式
        style.setFillForegroundColor(IndexedColors.TEAL.index);//设置前景色
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);//设置填充才能正常显示前景色
        //创建sheet页
        XSSFSheet sheet = xssfw.createSheet();
        //表头
        XSSFRow rowHead = sheet.createRow(0);
        XSSFCell cellHead = rowHead.createCell(0);
        cellHead.setCellValue("序号");
        cellHead.setCellStyle(style);//设置此列样式
        XSSFCell cellHead2 = rowHead.createCell(1);
        cellHead2.setCellValue("用户名");
        cellHead2.setCellStyle(style);
        XSSFCell cellHead3 = rowHead.createCell(2);
        cellHead3.setCellValue("真实姓名");
        cellHead3.setCellStyle(style);
        XSSFCell cellHead4 = rowHead.createCell(3);
        cellHead4.setCellValue("性别");
        cellHead4.setCellStyle(style);
        XSSFCell cellHead5 = rowHead.createCell(4);
        cellHead5.setCellValue("年龄");
        cellHead5.setCellStyle(style);
        XSSFCell cellHead6 = rowHead.createCell(5);
        cellHead6.setCellValue("薪资");
        cellHead6.setCellStyle(style);
        XSSFCell cellHead7 = rowHead.createCell(6);
        cellHead7.setCellValue("入职时间");
        cellHead7.setCellStyle(style);
        XSSFCell cellHead8 = rowHead.createCell(7);
        cellHead8.setCellValue("电话");
        cellHead8.setCellStyle(style);
        XSSFCell cellHead9 = rowHead.createCell(8);
        cellHead9.setCellValue("邮箱");
        cellHead9.setCellStyle(style);
        XSSFCell cellHead10 = rowHead.createCell(9);
        cellHead10.setCellValue("角色");
        cellHead10.setCellStyle(style);
        XSSFCell cellHead11 = rowHead.createCell(10);
        cellHead11.setCellValue("地区");
        cellHead11.setCellStyle(style);

        //日期处理
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //数据格式化
        XSSFCellStyle dateStyle = xssfw.createCellStyle();
        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
//        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));//double类型格式化

        //表内容
        for (int i = 0; i < list.size(); i++) {
            UserVo user = list.get(i);

            XSSFRow row = sheet.createRow(i + 1);
            XSSFCell cel0 = row.createCell(0);//id
            cel0.setCellValue(user.getId());
            XSSFCell cell = row.createCell(1);//用户名
            cell.setCellValue(user.getUserName());
            XSSFCell cell2 = row.createCell(2);//真实姓名
            cell2.setCellValue(user.getRealName());
            XSSFCell cell3 = row.createCell(3);//性别
            cell3.setCellValue(user.getSex() == 1 ? "男" : "女");
            XSSFCell cell4 = row.createCell(4);//年龄
            cell4.setCellValue(user.getAge());
            XSSFCell cell5 = row.createCell(5);//薪资
            cell5.setCellValue(user.getPay());
            XSSFCell cell6 = row.createCell(6);//入职时间
            try {
                cell6.setCellValue(sdf.parse(user.getEntryTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cell6.setCellStyle(dateStyle);
            XSSFCell cell7 = row.createCell(7);//电话
            cell7.setCellValue(user.getPhone());
            XSSFCell cell8 = row.createCell(8);//邮箱
            cell8.setCellValue(user.getEmail());
            XSSFCell cell9 = row.createCell(9);//角色
            cell9.setCellValue(user.getRoleNames());
            XSSFCell cell10 = row.createCell(10);//地区
            cell10.setCellValue(user.getAreaInfo());
        }

        sheet.setDefaultColumnWidth(20);
        //自动列宽
//				sheet.autoSizeColumn(0);
//				sheet.autoSizeColumn(1);
//				sheet.autoSizeColumn(2);
//				sheet.autoSizeColumn(3);
//				sheet.autoSizeColumn(4);
        return xssfw;
    }

    @Override
    public File buildWordFile(UserSearchParam userSearchParam) throws IOException, TemplateException {
        //查询用户列表
        List<UserVo> list = list(userSearchParam);
        //
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("title", "标题");
        dataMap.put("date", DateUtil.date2str(new Date(), DateUtil.Y_M_D));
        dataMap.put("end", "ggh");

        dataMap.put("list", list);
        // 新建配置对象
        Configuration configuration = new Configuration();
        // 设置默认编码
        configuration.setDefaultEncoding("UTF-8");
        // 设置模板所在路径
        configuration.setClassForTemplateLoading(this.getClass(), "/template");
        // 获取模板对象
        Template t = configuration.getTemplate("v.xml", "UTF-8");
        //Template t = configuration.getTemplate("test-2.xml", "UTF-8");//微软模板
        // 创建文件对象
        File file = new File("D:/" + UUID.randomUUID().toString() + ".docx");
        //File file = new File("D:/" + UUID.randomUUID().toString() + ".doc");//微软后缀
        // 新建文件输出流
        FileOutputStream fos = new FileOutputStream(file);
        // 新建写入器
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        // 把数据写入文件里
        t.process(dataMap, osw);
        //刷新缓冲区
        osw.flush();
        //关流
        osw.close();

        return file;
    }

    @Override
    public ByteArrayOutputStream buildPdfOS(UserSearchParam userSearchParam) throws IOException, DocumentException {
        //定义一个字节流数组
        ByteArrayOutputStream byo = new ByteArrayOutputStream();
        //查询用户列表
        List<UserVo> list = list(userSearchParam);
        //创建一个doucment对象 文本对象
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        //创建字体 设置为中文
        BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //创建列的字体样式
        Font keyFont = new Font(font, 10, Font.BOLD);
        //创建pdf文件
        PdfWriter.getInstance(document, byo);
        //设置一个表头数组
        String[] str = {"序号", "用户名", "真实姓名", "性别", "年龄", "薪资", "入职时间", "电话", "电话", "角色"};
        //设置书写器
        PdfPTable table = PDFFileUtil.createTable(str.length);
        //打开文本对象
        document.open();
        //设置标题
        Font headFont = new Font(font, 25, Font.BOLD);
        //设置标题的颜色
        headFont.setColor(BaseColor.PINK);
        //创建标题
        PdfPCell headCell = PDFFileUtil.createHeadline("1902-ggh", headFont);
        headCell.setExtraParagraphSpace(30);
        //放入书写器
        table.addCell(headCell);
        for (int i = 0; i < str.length; i++) {
            table.addCell(PDFFileUtil.createCell(str[i], keyFont, Element.ALIGN_CENTER));
        }
        //把查询到的数据集合遍历到书写器里面
        for (int i = 0; i < list.size(); i++) {
            table.addCell(PDFFileUtil.createCell(list.get(i).getId().toString(), keyFont, Element.ALIGN_CENTER));
            table.addCell(PDFFileUtil.createCell(list.get(i).getUserName(), keyFont, Element.ALIGN_CENTER));
            table.addCell(PDFFileUtil.createCell(list.get(i).getRealName(), keyFont, Element.ALIGN_CENTER));
            table.addCell(PDFFileUtil.createCell(list.get(i).getSex() == 1 ? "男" : "女", keyFont, Element.ALIGN_CENTER));
            table.addCell(PDFFileUtil.createCell(list.get(i).getAge().toString(), keyFont, Element.ALIGN_CENTER));
            table.addCell(PDFFileUtil.createCell(list.get(i).getPay().toString(), keyFont, Element.ALIGN_CENTER));
            table.addCell(PDFFileUtil.createCell(list.get(i).getEntryTime(), keyFont, Element.ALIGN_CENTER));
            table.addCell(PDFFileUtil.createCell(list.get(i).getPhone(), keyFont, Element.ALIGN_CENTER));
            table.addCell(PDFFileUtil.createCell(list.get(i).getEmail(), keyFont, Element.ALIGN_CENTER));
            table.addCell(PDFFileUtil.createCell(list.get(i).getRoleNames(), keyFont, Element.ALIGN_CENTER));
        }
        //放入文本对象
        document.add(table);
        document.close();
        return byo;
    }

    @Override
    public List<Wealth> getMenu(Long id) {
        return userMapper.getMenu(id);
    }

    @Override
    public List<String> getMenuUrls(Long id) {
        return userMapper.getMenuUrls(id);
    }

    @Override
    public String resetPassword(Long id) {

        //随机密码
        String newPassword = RandomStringUtils.randomAlphanumeric(6);

        //salt+双重md5
        String[] ss = Md5Util.salt_md5_2(newPassword);
        String salt = ss[0];
        String md5 = ss[1];
        userMapper.updatePassword(id, salt, md5);
        return newPassword;
    }

    @Override
    public ServerResponse updatePassword(UserPasswordParam userPasswordParam) {
        //判断旧密码 新密码 确认密码 是否为空 有任意一个为空 返回提示
        if (StringUtils.isEmpty(userPasswordParam.getOldPassword())
                || StringUtils.isEmpty(userPasswordParam.getConPassword())
                || StringUtils.isEmpty(userPasswordParam.getNewPassword())) {
            return ServerResponse.error(ResponseEnum.USER_PASSWORD_IS_NULL);
        }
        //判断确认密码和新密码是否一致  如果不一致 返回提示
        if (!userPasswordParam.getNewPassword().equals(userPasswordParam.getConPassword())) {
            return ServerResponse.error(ResponseEnum.NEWPASSWORD_CONPASSWORD_IS_ERROR);
        }
        //通过id 查询 数据库中的 当前用户的 密码 和 盐
        User user = userMapper.findUser(userPasswordParam.getUserId());
        //获取盐
        String salt = user.getSalt();
        //给前台传过来的原密码加密
        String oldPassword = Md5Util.salt_md5_2(userPasswordParam.getOldPassword(), salt);
        //判断原密码是否和数据库中的一致  如果不一致 返回提示
        if (!user.getPassword().equals(oldPassword)) {
            return ServerResponse.error(ResponseEnum.OLDPASSWORD_IS_ERROR);
        }
        String newPassword = Md5Util.salt_md5_2(userPasswordParam.getNewPassword(), salt);
        userMapper.updatePassword(user.getId(), null, newPassword);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    private void convertUserVoList(List<UserVo> list, List<User> userList) {
        for (User user1 : userList) {
            UserVo userVo = convertUserVo(user1);
            List<String> roleNameList = userMapper.findRoleNames(user1.getId());
            String roleNames = StringUtils.join(roleNameList, ",");
            userVo.setRoleNames(roleNames);
            list.add(userVo);
        }
    }
}
