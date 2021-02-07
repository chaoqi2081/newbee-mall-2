package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.entity.AdminUser;
import ltd.newbee.mall.service.AdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminUserService adminUserService;

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (int)request.getSession().getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }

        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getLoginUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request,
                                 @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        System.out.println("passwordUpdate...");
        if (ObjectUtils.isEmpty(originalPassword) || ObjectUtils.isEmpty(newPassword)) {
            return "参数不能为空";
        }

        Integer loginUserId = (int)request.getSession().getAttribute("loginUserId");
        if (!adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            return "修改失败";
        }

        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");

        return "success";
    }

    @PostMapping("/profile/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request,
                             @RequestParam("loginUserName") String loginUserName,
                             @RequestParam("nickName") String nickName) {
        System.out.println("nameUpdate...");
        if (ObjectUtils.isEmpty(loginUserName) || ObjectUtils.isEmpty(nickName)) {
            return "参数不能为空";
        }

        Integer loginUserId = (int)request.getSession().getAttribute("loginUserId");
        return adminUserService.updateName(loginUserId, loginUserName, nickName) ? "success" : "修改失败";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        System.out.println("logout...");
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }

    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {
        if (ObjectUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }
        if (ObjectUtils.isEmpty(userName) || ObjectUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode").toString();
        if (ObjectUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "admin/login";
        }
        System.out.println("userName:" + userName);
        System.out.println("password:" + password);
        AdminUser adminUser = adminUserService.login(userName, password);
        if (adminUser != null) {
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            //session过期时间设置为7200秒 即两小时
            //session.setMaxInactiveInterval(60 * 60 * 2);
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "登录信息错误");
            return "admin/login";
        }
    }

    @GetMapping({"/index"})
    public String index() {
        return "admin/index";
    }

}
