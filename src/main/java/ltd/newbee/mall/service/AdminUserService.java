package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.AdminUser;

public interface AdminUserService {
    //用户登录
    AdminUser login(String userName, String password);
    //通过 id 获取用户名
    AdminUser getUserDetailById(Integer loginUserId);
    //更新用户密码
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);
    //更新用户昵称、登录名
    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);
}
