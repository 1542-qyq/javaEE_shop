package com.ch.ch9.controller.admin;

import com.ch.ch9.NoLoginException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller("adminBaseController")
public class AdminBaseController {
    /**
     * 登录权限控制，处理方法执行前执行该方法
     */
    @ModelAttribute
    public void isLogin(HttpServletRequest request, HttpSession session) throws NoLoginException {
        // 获取当前请求的路径
        String requestURI = request.getRequestURI();
        
        // 如果是登录页面或登录处理路径，跳过登录检查
        if (requestURI.contains("/admin/toLogin") || requestURI.contains("/admin/login")) {
            return;
        }
        
        // 其他路径需要检查登录状态
        if(session.getAttribute("auser") == null){
            throw new NoLoginException("没有登录");
        }
    }
}
