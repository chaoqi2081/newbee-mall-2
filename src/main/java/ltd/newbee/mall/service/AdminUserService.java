package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.AdminUser;

public interface AdminUserService {
    AdminUser login(String userName, String password);
}
