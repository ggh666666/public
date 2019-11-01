package com.fh.shop.admin.controller.user;

import com.fh.shop.admin.biz.user.IUserService;
import com.fh.shop.admin.biz.wealth.IWealthService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-common.xml"})
public class TestUserController extends AbstractJUnit4SpringContextTests {
    @Resource(name = "userService")
    private IUserService userService;

    @Resource(name = "wealthService")
    private IWealthService wealthService;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @Test
    public void testAdd() {
        User user = new User();
        user.setUserName("junit_test");
        user.setPassword("junit_test");
        user.setEntryTime(new Date());
        userService.add(user);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
    }
}
