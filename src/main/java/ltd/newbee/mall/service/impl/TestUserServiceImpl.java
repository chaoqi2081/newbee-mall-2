package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.dao.TestUserDao;
import ltd.newbee.mall.entity.TestUser;
import ltd.newbee.mall.service.TestUserService;
import ltd.newbee.mall.utils.PageResult;
import ltd.newbee.mall.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TestUserService")
public class TestUserServiceImpl implements TestUserService {
    @Autowired
    private TestUserDao testUserDao;

    @Override
    public PageResult getAdminUserPage(PageUtil pageUtil) {
        //当前页码中的数据列表
        List<TestUser> users = testUserDao.findUsers(pageUtil);
        //数据总条数 用于计算分页数据
        int total = testUserDao.getTotalUser(pageUtil);
        PageResult pageResult = new PageResult(users, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
