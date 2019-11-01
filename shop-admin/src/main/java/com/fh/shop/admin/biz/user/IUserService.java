package com.fh.shop.admin.biz.user;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.user.UserPasswordParam;
import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.vo.user.UserVo;
import com.itextpdf.text.DocumentException;
import freemarker.template.TemplateException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IUserService {
    void add(User user);

    Long getRecordsTotal(UserSearchParam userSearchParam);

    List<UserVo> list(UserSearchParam userSearchParam);

    void delete(Long id);

    UserVo findUser(Long id);

    void update(User user);

    void batchDelete(Long[] ids);

    User findUserByUserName(String userName);

    void updateLoginInfo(Integer todayLogins, Long id);

    void updateLockInfo(int errors, int lockState, Long id);

    List<UserVo> getList(UserSearchParam userSearchParam);

    XSSFWorkbook buildWorkbook(UserSearchParam userSearchParam);

    File buildWordFile(UserSearchParam userSearchParam) throws IOException, TemplateException;

    ByteArrayOutputStream buildPdfOS(UserSearchParam userSearchParam) throws IOException, DocumentException;

    List<Wealth> getMenu(Long id);

    List<String> getMenuUrls(Long id);

    String resetPassword(Long id);

    ServerResponse updatePassword(UserPasswordParam userPasswordParam);

    User findUserByEmail(String email);
}
