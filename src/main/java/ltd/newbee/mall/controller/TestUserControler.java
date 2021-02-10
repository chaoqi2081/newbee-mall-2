package ltd.newbee.mall.controller;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ResultGenerator;
import ltd.newbee.mall.service.TestUserService;
import ltd.newbee.mall.utils.PageUtil;
import ltd.newbee.mall.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/users")
public class TestUserControler {

    @Autowired
    private TestUserService testUserService;

    /**
     * 分页功能测试
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "参数异常！");
        }
        //查询列表数据
        PageUtil pageUtil = new PageUtil(params);
        return ResultGenerator.genSuccessResult(testUserService.getAdminUserPage(pageUtil));
    }

}