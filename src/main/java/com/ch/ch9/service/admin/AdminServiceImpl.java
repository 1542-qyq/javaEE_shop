package com.ch.ch9.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.ch9.common.exception.BusinessException;
import com.ch.ch9.dto.LoginDTO;
import com.ch.ch9.dto.LoginResultDTO;
import com.ch.ch9.entity.AUser;
import com.ch.ch9.mapper.admin.AdminUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminUserMapper, AUser> implements AdminService {

    // 简单内存存储token（生产环境用Redis）
    private final ConcurrentHashMap<String, Integer> tokenStore = new ConcurrentHashMap<>();

    @Autowired
    public AdminServiceImpl(AdminUserMapper adminUserMapper) {
        // 如果有需要可以自定义构造方法
    }

    @Override
    public LoginResultDTO login(LoginDTO loginDTO) {
        // 1. 参数验证
        if (loginDTO == null) {
            throw new BusinessException("登录信息不能为空");
        }

        String userName = loginDTO.getuserName();
        String password = loginDTO.getPassword();

        if (userName == null || userName.trim().isEmpty()) {
            throw new BusinessException("用户名不能为空");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new BusinessException("密码不能为空");
        }

        // 2. 查询用户
        QueryWrapper<AUser> wrapper = new QueryWrapper<>();
        wrapper.eq("aname", userName.trim())
                .eq("apwd", password.trim());

        AUser admin = baseMapper.selectOne(wrapper);

        // 3. 验证登录
        if (admin == null) {
            throw new BusinessException("用户名或密码错误");
        }

        

        // 5. 生成token
        String token = generateToken(admin.getId());

        // 6. 返回登录结果
        LoginResultDTO result = new LoginResultDTO();
        result.setId(admin.getId());
        result.setuserName(admin.getAname());
        result.setToken(token);

        return result;
    }

    @Override
    public AUser findByuserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            return null;
        }

        QueryWrapper<AUser> wrapper = new QueryWrapper<>();
        wrapper.eq("aname", userName.trim());
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public boolean logout(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        return tokenStore.remove(token) != null;
    }

    @Override
    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        // 1. 参数验证
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }

        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            throw new BusinessException("旧密码不能为空");
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new BusinessException("新密码不能为空");
        }

        // 2. 获取用户
        AUser admin = getById(userId);
        if (admin == null) {
            throw new BusinessException("用户不存在");
        }

        // 3. 验证旧密码
        if (!admin.getApwd().equals(oldPassword.trim())) {
            throw new BusinessException("旧密码错误");
        }

        // 4. 更新密码
        admin.setApwd(newPassword.trim());
        return updateById(admin);
    }

    @Override
    public boolean isuserNameExist(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            return false;
        }

        QueryWrapper<AUser> wrapper = new QueryWrapper<>();
        wrapper.eq("aname", userName.trim());
        Integer count = Math.toIntExact(baseMapper.selectCount(wrapper));
        return count != null && count > 0;
    }

    @Override
    public boolean register(AUser admin) {
        // 1. 参数验证
        if (admin == null) {
            throw new BusinessException("用户信息不能为空");
        }

        if (admin.getAname() == null || admin.getAname().trim().isEmpty()) {
            throw new BusinessException("用户名不能为空");
        }

        if (admin.getApwd() == null || admin.getApwd().trim().isEmpty()) {
            throw new BusinessException("密码不能为空");
        }

        // 2. 检查用户名是否已存在
        if (isuserNameExist(admin.getAname())) {
            throw new BusinessException("用户名已存在");
        }

       

        // 4. 保存
        return save(admin);
    }

    /**
     * 生成token
     */
    private String generateToken(Integer userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        tokenStore.put(token, userId);
        return token;
    }

    /**
     * 根据token获取用户ID
     */
    public Integer getUserIdByToken(String token) {
        if (token == null) {
            return null;
        }
        return tokenStore.get(token);
    }

    /**
     * 获取所有token数量（用于测试）
     */
    public int getTokenCount() {
        return tokenStore.size();
    }
}