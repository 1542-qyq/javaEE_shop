package com.ch.ch9.controller.before;

import com.ch.ch9.entity.BUser;
import com.ch.ch9.service.before.UserService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller("beforeUserController")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到注册页面
     */
    @GetMapping("/toRegister")
    public String toRegister(Model model) {
        model.addAttribute("bUser", new BUser());
        return "user/register";
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("bUser") BUser bUser,
                           BindingResult bindingResult,
                           Model model) {
        return userService.register(bUser, bindingResult, model);
    }

    /**
     * 检查邮箱是否可用（AJAX接口）
     */
    @PostMapping("/checkEmail")
    @ResponseBody
    public String checkEmail(@RequestParam("email") String email) {
        boolean exists = userService.isEmailExist(email);
        return exists ? "no" : "yes";
    }

    /**
     * 跳转到登录页面
     */
    @GetMapping("/toLogin")
    public String toLogin(Model model) {
        model.addAttribute("bUser", new BUser());
        return "user/login";
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public String login(@ModelAttribute("bUser") BUser bUser,
                        HttpSession session,
                        Model model) {
        return userService.login(bUser, session, model);
    }

    /**
     * 用户退出
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        return userService.logout(session);
    }

    /**
     * 跳转到个人信息页面
     */
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        BUser currentUser = userService.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/user/toLogin";
        }
        model.addAttribute("bUser", currentUser);
        return "user/userInfo";
    }

    /**
     * 更新个人信息
     */
    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("bUser") BUser bUser,
                                HttpSession session,
                                Model model) {
        return userService.updateProfile(bUser, session, model);
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 HttpSession session,
                                 Model model) {

        // 调用Service修改密码
        return userService.updatePassword(currentPassword, newPassword, confirmPassword, session, model);
    }

    /**
     * 首页（测试用）
     */
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        BUser user = userService.getCurrentUser(session);
        model.addAttribute("user", user);
        return "user/home";
    }

}