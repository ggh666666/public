package com.fh.shop.admin.aspect;

import com.alibaba.fastjson.JSON;
import com.fh.shop.admin.biz.log.ILogService;
import com.fh.shop.admin.common.Log;
import com.fh.shop.admin.common.WebContext;
import com.fh.shop.admin.po.log.LogInfo;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.util.DistributedSession;
import com.fh.shop.admin.util.KeyUtil;
import com.fh.shop.admin.util.RedisUtil;
import com.fh.shop.admin.util.SystemConst;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class loggerAcpect {

    private static final Logger LOGGER = LoggerFactory.getLogger(loggerAcpect.class);

    @Resource(name = "logService")
    private ILogService logService;

    public Object doLog(ProceedingJoinPoint pot) {
        //就相当于当前执行的方法
        HttpServletRequest request = WebContext.getRequest();
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute(SystemConst.CURR_USER);

        HttpServletResponse response = WebContext.getResponse();

        //分布式session    cookie + redis
        String sessionId = DistributedSession.getSessionId(request, response);
        String userJson = RedisUtil.get(KeyUtil.buildCurrUserKey(sessionId));
        User user = JSON.parseObject(userJson, User.class);


        //从user里面拿到用户名
        String userName = user.getUserName();
        //真实姓名
        String realName = user.getRealName();
        //拿到当前执行的方法
        String name = pot.getSignature().getName();
        //拿到当前执行的方法 属于哪个类
        String className = pot.getTarget().getClass().getName();
        //获取详细信息  也就是当前执行的这个方法的 参数类型
        Map<String, String[]> par = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = par.entrySet().iterator();
        StringBuffer str = new StringBuffer();
        while (iterator.hasNext()) {//判断有没有下一个值
            Map.Entry<String, String[]> next = iterator.next();
            str.append("|").append(next.getKey() + ":" + next.getValue());
        }
        //获取自定义注解
        MethodSignature methodSignature = (MethodSignature) pot.getSignature();
        //用来判断当前方法 有没有使用这个自定义注解
        Method method = methodSignature.getMethod();
        String value = "";
        if (method.isAnnotationPresent(Log.class)) {
            //通过method 获取自定义注解
            Log annotation = method.getAnnotation(Log.class);
            //这个就是我自己定义的日志信息
            value = annotation.value();
        }
        Object proceed = null;
        try {
            //前置通知
            proceed = pot.proceed();//走到这里 进入controller 执行核心业务  当我们的业务处理完毕 返回值之后 才会回到这里 执行下面的代码
            //方法执行完毕之后 回到这里
            //方法执行成功 记录正确的日志信息
            String info = "用户:" + userName + "执行了" + className + "的" + name + "成功";
            LOGGER.info(info);

            //操作成功保存数据到数据库
            addLogInfo(userName, realName, info, "", SystemConst.LOG_STATUS_SUCCESS, str.toString(), value);
        } catch (Throwable throwable) {
            //在catch 进行异常捕获 同时记录错误的日志信息
            String info = "用户:" + userName + "执行了" + className + "的" + name + "失败," + "失败原因:" + throwable.getMessage();
            LOGGER.error(info);
            //如果 在controller之外的类报错了 异常会被捕获到这里
            throwable.printStackTrace();
            //执行失败 保存数据到数据库
            addLogInfo(userName, realName, info, throwable.getMessage(), SystemConst.LOG_STATUS_ERROR, str.toString(), value);
            //把这个异常 往上一级 抛
            throw new RuntimeException(throwable);
        }
        return proceed;
    }

    private StringBuffer getStringBuffer(Map<String, String[]> parameterMap) {
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        StringBuffer str = new StringBuffer();
        //判断是否还有下一个值
        while (iterator.hasNext()) {
            //获取一个entry对象
            Map.Entry<String, String[]> next = iterator.next();
            str.append("|" + next.getKey() + ":" + StringUtils.join(next.getValue(), ",") + ";");
        }
        return str;
    }

    private void addLogInfo(String userName, String realName, String info, String errorMsg, Integer status, String detail, String value) {
        LogInfo logInfo = new LogInfo();
        logInfo.setUserName(userName);
        logInfo.setRealName(realName);
        logInfo.setInfo(info);
        logInfo.setCreateDate(new Date());
        logInfo.setErrorMsg(errorMsg);
        logInfo.setStatus(status);
        logInfo.setDetail(detail);
        logInfo.setContent(value);
        logService.addLog(logInfo);
    }
}
