package com.fh.shop.admin.mapper.user;

import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.po.user.UserRole;
import com.fh.shop.admin.po.wealth.Wealth;

import java.util.List;

public interface IUserMapper {
    void add(User user);

    Long getRecordsTotal(UserSearchParam userSearchParam);

    List<User> list(UserSearchParam userSearchParam);

    void delete(Long id);

    User findUser(Long id);

    void update(User user);

    void addUserRole(List<UserRole> userRole);

    void deleteUserRole(Long userId);

    List<String> findRoleNames(Long userId);

    List<Integer> findRoleIds(Long id);

    void batchDelete(Long[] ids);

    void batchDeleteUserRole(Long[] ids);

    User findUserByUserName(String userName);

    void updateLoginInfo(Integer todayLogins, Long id);

    void updateLockInfo(int errors, int lockState, Long id);

    List<User> getList(UserSearchParam userSearchParam);

    List<Wealth> getMenu(Long id);

    List<String> getMenuUrls(Long id);

    void updatePassword(Long id, String salt, String md5);

    User findUserByEmail(String email);
}
