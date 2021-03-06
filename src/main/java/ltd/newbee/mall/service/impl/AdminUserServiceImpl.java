package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.dao.AdminUserDao;
import ltd.newbee.mall.entity.AdminUser;
import ltd.newbee.mall.service.AdminUserService;
import ltd.newbee.mall.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserDao adminUserDao;

    @Override
    public AdminUser getUserDetailById(Integer loginUserId) {
        return adminUserDao.selectByPrimaryKey(loginUserId);
    }

    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserDao.selectByPrimaryKey(loginUserId);
        if (null == adminUser) {
            return false;
        }

        String originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
        String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
        if (originalPasswordMd5.equals(adminUser.getLoginPassword())) {
            adminUser.setLoginPassword(newPasswordMd5);
            if (adminUserDao.updateByPrimaryKeySelective(adminUser) > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        AdminUser adminUser = adminUserDao.selectByPrimaryKey(loginUserId);
        if (null == adminUser) {
            return false;
        }

        adminUser.setLoginUserName(loginUserName);
        adminUser.setNickName(nickName);
        if (adminUserDao.updateByPrimaryKeySelective(adminUser) > 0) {
            return true;
        }

        return false;
    }

    @Override
    public AdminUser login(String userName, String password) {
        String passwordMd5 = MD5Util.MD5Encode(password, "UTF-8");
        System.out.println("password:" + passwordMd5);
        return adminUserDao.login(userName, passwordMd5);
    }
}
