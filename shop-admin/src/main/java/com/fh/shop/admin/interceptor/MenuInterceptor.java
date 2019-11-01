package com.fh.shop.admin.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.util.DistributedSession;
import com.fh.shop.admin.util.KeyUtil;
import com.fh.shop.admin.util.RedisUtil;
import com.fh.shop.admin.util.SystemConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class MenuInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();//获取请求路径     uri 和 url 都不会获取到参数信息
        //if (SystemConst.WELCOME_PAGE.equals(uri))
        //return true;

        //没有权限控制的路径都是公共路径
        boolean flag = false;//是否被权限控制
//        List<Wealth> menuList = (List<Wealth>) request.getSession().getAttribute(SystemConst.MENU_ALL_INFO);

        //分布式session    cookie + redis
        String sessionId = DistributedSession.getSessionId(request, response);
        String menuListJson = RedisUtil.get(KeyUtil.buildMenuAllInfo(sessionId));
        List<Wealth> menuList = JSON.parseArray(menuListJson, Wealth.class);


        for (int i = 0; i < menuList.size(); i++) {
            Wealth menu = menuList.get(i);
            if (uri.equals(menu.getUrl())) {//如果数据库中有此路径说明不是公共路径
                flag = true;
                break;
            }
        }
        if (!flag)//公共路径直接通过
            return true;
        //判断用户是否有访问权限 防止用户通过url直接访问
//        List<String> menuUrls = (List<String>) request.getSession().getAttribute(SystemConst.CURR_USER_MENU_URLS);

        //分布式session    cookie + redis
        String menuUrlsStr = RedisUtil.get(KeyUtil.buildCurrUserMenuUrls(sessionId));
        List<String> menuUrls = Arrays.asList(menuUrlsStr.split(","));


        for (int i = 0; i < menuUrls.size(); i++) {//将request的uri与数据库查出来的路径做对比 相同则通过
            String url = menuUrls.get(i);
            if (!StringUtils.isEmpty(url) && !url.equals("#") && !uri.equals("#")) {//uri有时为#
                if (uri.equals(url)) {//uri为#时会报下标越界异常 contains 包含
                    return true;
                }
            }
        }
        System.out.println("-------被拦截了----------" + uri);
        String requestType = request.getHeader("X-Requested-With");//获取请求类型
        if (StringUtils.isNotEmpty(requestType) && requestType.equals("XMLHttpRequest")) {//ajax请求的头部信息里会有 X-Requested-With:XMLHttpRequest 这个属性
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            ServerResponse error = ServerResponse.error(ResponseEnum.HAVE_NO_RIGHT);
            JSONObject jsonObject = new JSONObject();
            String jsonString = jsonObject.toJSONString(error);
            writer.print(jsonString);//ajax请求没有权限的操作时会返回json格式的数据
            //关闭连接
            if (writer != null) {
                writer.close();
                writer = null;
            }
        } else
            response.sendRedirect(SystemConst.Error_PAGE);//访问没有权限的路径会跳转到导航页面
        return false;
    }
}
