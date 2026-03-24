// AdminPageController.java
package com.ch.ch9.controller.admin;

import com.ch.ch9.common.exception.BusinessException;
import com.ch.ch9.dto.LoginDTO;
import com.ch.ch9.dto.LoginResultDTO;
import com.ch.ch9.entity.AUser;
import com.ch.ch9.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController extends AdminBaseController {

    @Autowired
    private AdminService adminService;

    /**
     * 跳转到管理员登录页面
     */
    @GetMapping("/toLogin")
    public String toLogin() {
        return "admin/login";  // 对应 templates/admin/login.html
    }

    /**
     * 处理管理员登录请求
     */
    @PostMapping("/login")
    public String login(@RequestParam("aname") String userName, 
                       @RequestParam("apwd") String password, 
                       RedirectAttributes redirectAttributes, 
                       HttpSession session) {
        try {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setuserName(userName);
            loginDTO.setPassword(password);
            
            LoginResultDTO result = adminService.login(loginDTO);
            
            // 获取用户完整信息
            AUser adminUser = adminService.findByuserName(userName);
            
            // 将用户信息存入session
            session.setAttribute("auser", adminUser);
            
            // 登录成功，跳转到后台首页
            return "redirect:/admin/index";
        } catch (BusinessException e) {
            // 登录失败，重定向回登录页面并显示错误信息
            redirectAttributes.addAttribute("error", true);
            return "redirect:/admin/toLogin";
        }
    }

    /**
     * 管理员首页
     */
    @GetMapping({"/index","","/"})
    public String index() {
        return "admin/adminIndex";  // 对应 templates/admin/index.html
    }


}