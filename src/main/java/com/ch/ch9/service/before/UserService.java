package com.ch.ch9.service.before;


import com.ch.ch9.entity.BUser;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


public interface UserService {

    /**
     * 用户注册
     * @param bUser 用户信息
     * @param bindingResult 验证结果
     * @param model 模型
     * @return 视图名称
     */

    String register(BUser bUser, BindingResult bindingResult, Model model);

    /**
     * 检查邮箱是否已存在
     * @param email 邮箱
     * @return 是否存在
     */
    boolean isEmailExist(String email);

    /**
     * 用户登录
     * @param bUser 用户信息
     * @param session 会话
     * @param model 模型
     * @return 视图名称
     */
    String login(BUser bUser, HttpSession session, Model model);

    /**
     * 用户退出
     * @param session 会话
     * @return 重定向地址
     */
    String logout(HttpSession session);

    /**
     * 获取当前登录用户信息
     * @param session 会话
     * @return 用户信息
     */
    BUser getCurrentUser(HttpSession session);

    /**
     * 更新用户信息
     * @param bUser 用户信息
     * @param session 会话
     * @param model 模型
     * @return 视图名称
     */
    String updateProfile(BUser bUser, HttpSession session, Model model);

    String updatePassword(String oldPassword,
                          String newPassword,
                          String confirmPassword,
                          HttpSession session,
                          Model model);
}