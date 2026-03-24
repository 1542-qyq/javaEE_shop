package com.ch.ch9.service.before;

import com.ch.ch9.entity.BUser;
import com.ch.ch9.repository.before.UserRepository;
import com.ch.ch9.service.before.UserService;
import com.ch.ch9.util.MD5Util;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public String register(BUser bUser, BindingResult bindingResult, Model model) {
        // 1. 表单验证
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        // 2. 检查密码是否一致
        if (!bUser.getBpwd().equals(bUser.getRebpwd())) {
            model.addAttribute("errorMessage", "两次输入的密码不一致");
            return "user/register";
        }

        // 3. 检查邮箱是否已存在
        if (isEmailExist(bUser.getBemail())) {
            model.addAttribute("errorMessage", "该邮箱已被注册");
            return "user/register";
        }

        // 4. 密码加密
        String encryptedPassword = MD5Util.encode(bUser.getBpwd());
        bUser.setBpwd(encryptedPassword);

        // 5. 保存用户
        int result = userRepository.save(bUser);
        if (result > 0) {
            model.addAttribute("successMessage", "注册成功，请登录");
            return "user/login";
        } else {
            model.addAttribute("errorMessage", "注册失败，请稍后重试");
            return "user/register";
        }
    }

    @Override
    public boolean isEmailExist(String email) {
        List<BUser> users = userRepository.findByEmail(email);
        return users != null && users.size() > 0;
    }

    @Override
    public String login(BUser bUser, HttpSession session, Model model) {
        // 1. 验证输入
        if (bUser.getBemail() == null || bUser.getBemail().trim().isEmpty()) {
            model.addAttribute("errorMessage", "请输入邮箱");
            return "user/login";
        }

        if (bUser.getBpwd() == null || bUser.getBpwd().trim().isEmpty()) {
            model.addAttribute("errorMessage", "请输入密码");
            return "user/login";
        }

        // 2. 密码加密
        String encryptedPassword = MD5Util.encode(bUser.getBpwd());

        // 3. 验证登录
        List<BUser> users = userRepository.login(bUser.getBemail(), encryptedPassword);

        if (users != null && users.size() > 0) {
            // 登录成功
            BUser user = users.get(0);
            session.setAttribute("buser", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getUserName() != null ? user.getUserName() : user.getBemail());

            // 登录后跳转到首页
            return "redirect:/";
        } else {
            // 登录失败
            model.addAttribute("errorMessage", "邮箱或密码错误");
            return "user/login";
        }
    }

    @Override
    public String logout(HttpSession session) {
        session.removeAttribute("buser");
        session.removeAttribute("userId");
        session.removeAttribute("userName");
        session.invalidate();
        return "redirect:/user/toLogin";
    }

    @Override
    public BUser getCurrentUser(HttpSession session) {
        return (BUser) session.getAttribute("buser");
    }

    @Override
    public String updateProfile(BUser bUser, HttpSession session, Model model) {
        BUser currentUser = getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/user/toLogin";
        }

        // 设置ID
        bUser.setId(currentUser.getId());

        // 更新用户信息
        int result = userRepository.update(bUser);
        if (result > 0) {
            // 更新session中的用户信息
            BUser updatedUser = userRepository.findById(currentUser.getId());
            session.setAttribute("buser", updatedUser);
            session.setAttribute("userName", updatedUser.getUserName());

            model.addAttribute("successMessage", "个人信息更新成功");
            model.addAttribute("bUser", updatedUser);
        } else {
            model.addAttribute("errorMessage", "更新失败，请稍后重试");
            model.addAttribute("bUser", currentUser);
        }

        return "user/userInfo";
    }
    // 在 UserServiceImpl.java 中添加以下方法
    @Override
    @Transactional
    public String updatePassword(String oldPassword,
                                 String newPassword,
                                 String confirmPassword,
                                 HttpSession session,
                                 Model model) {

        // 1. 获取当前登录用户
        BUser currentUser = (BUser) session.getAttribute("buser");
        if (currentUser == null) {
            return "redirect:/user/toLogin"; // 跳转到登录页面
        }

        // 2. 验证参数
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            model.addAttribute("errorMessage", "请输入当前密码");
            model.addAttribute("bUser", currentUser);
            return "user/userInfo";
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            model.addAttribute("errorMessage", "请输入新密码");
            model.addAttribute("bUser", currentUser);
            return "user/userInfo";
        }

        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            model.addAttribute("errorMessage", "请确认新密码");
            model.addAttribute("bUser", currentUser);
            return "user/userInfo";
        }

        // 3. 验证新密码长度
        if (newPassword.length() < 6) {
            model.addAttribute("errorMessage", "新密码长度不能少于6位");
            model.addAttribute("bUser", currentUser);
            return "user/userInfo";
        }

        // 4. 验证两次输入的新密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "两次输入的新密码不一致");
            model.addAttribute("bUser", currentUser);
            return "user/userInfo";
        }

        // 5. 验证旧密码是否正确（需要加密后比较）
        String encryptedOldPassword = MD5Util.encode(oldPassword);
        if (!currentUser.getBpwd().equals(encryptedOldPassword)) {
            model.addAttribute("errorMessage", "当前密码不正确");
            model.addAttribute("bUser", currentUser);
            return "user/userInfo";
        }

        // 6. 验证新密码不能与旧密码相同
        if (oldPassword.equals(newPassword)) {
            model.addAttribute("errorMessage", "新密码不能与当前密码相同");
            model.addAttribute("bUser", currentUser);
            return "user/userInfo";
        }

        // 7. 对新密码进行加密
        String encryptedNewPassword = MD5Util.encode(newPassword);

        // 8. 更新密码
        int rows = userRepository.updatePassword(currentUser.getId(), encryptedNewPassword);
        if (rows > 0) {
            // 更新session中的用户密码信息
            currentUser.setBpwd(encryptedNewPassword);
            session.setAttribute("buser", currentUser);

            model.addAttribute("successMessage", "密码修改成功");
            model.addAttribute("bUser", currentUser);
            return "user/userInfo";
        } else {
            model.addAttribute("errorMessage", "密码修改失败，请稍后重试");
            model.addAttribute("bUser", currentUser);
            return "user/userInfo";
        }
    }
}