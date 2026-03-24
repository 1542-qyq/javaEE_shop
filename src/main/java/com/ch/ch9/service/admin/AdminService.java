package com.ch.ch9.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.ch9.dto.LoginDTO;
import com.ch.ch9.dto.LoginResultDTO;
import com.ch.ch9.entity.AUser;

public interface AdminService extends IService<AUser> {

    /**
     * 管理员登录
     */
    LoginResultDTO login(LoginDTO loginDTO);

    /**
     * 根据用户名查找管理员
     */
    AUser findByuserName(String userName);

    /**
     * 登出
     */
    boolean logout(String token);

    /**
     * 修改密码
     */
    boolean changePassword(Integer userId, String oldPassword, String newPassword);

    /**
     * 检查用户名是否已存在
     */
    boolean isuserNameExist(String userName);

    /**
     * 注册管理员
     */
    boolean register(AUser admin);
}