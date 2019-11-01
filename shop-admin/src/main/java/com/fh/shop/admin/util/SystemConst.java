package com.fh.shop.admin.util;

public final class SystemConst {
    public static final String CURR_USER = "user";
    public static final String CURR_USER_MENU_URLS = "menuUrls";
    public static final String CURR_USER_MENU_INFO = "menus";
    public static final String MENU_ALL_INFO = "menuList";
    public static final String WELCOME_PAGE = "/index.jhtml";
    public static final String Error_PAGE = "/error.jhtml";

    public static final int IF_ERRORS_LOCK = 3;//如果密码错误次数超过三次则锁定用户

    //
    public static final String PRODUCT_WORD_TEMPLATE_FILE = "product-template.xml";
    public static final String TEMPLATE_PATH = "/template";
    public static final String WORD_SAVE_PATH = "d:/";
    public static final String WORD_SUFFIX = ".docx";

    public static final String COMPANY_NAME = "1902";//导出pdf文件的单位
    public static final String PRODUCT_PDF_TEMPLATE_FILE = "info.html";//制定pdf模板html
    public static final String DEFAULT_PASSWORD = "123";
    public static final Integer LOG_STATUS_SUCCESS = 1;//成功状态
    public static final Integer LOG_STATUS_ERROR = 0;//失败状态

    public static final String IMG_PATH = "/images";


    public static final String SESSION_ID = "my_id";
    public static final Integer CODE_EXPIRE = 1 * 60;
    public static final Integer EXPIRE = 5 * 60;
}
