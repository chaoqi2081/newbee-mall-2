package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.TestUser;

import java.util.List;
import java.util.Map;

public interface TestUserDao {
    /**
     * 根据参数查询用户列表
     *
     * @param param
     * @return
     */
    List<TestUser> findUsers(Map param);

    /**
     * 查询用户总数
     *
     * @param param
     * @return
     */
    int getTotalUser(Map param);
}
