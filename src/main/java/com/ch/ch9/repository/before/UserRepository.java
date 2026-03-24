package com.ch.ch9.repository.before;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.ch9.entity.BUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserRepository extends BaseMapper<BUser>{

    // 根据邮箱查询用户（用于登录和检查邮箱是否已存在）
    List<BUser> findByEmail(String bemail);

    // 根据ID查询用户
    BUser findById(Integer id);

    // 用户注册
    int save(BUser bUser);

    // 用户登录验证
    List<BUser> login(@Param("bemail") String bemail, @Param("bpwd") String bpwd);

    // 更新用户信息
    int update(BUser bUser);

    // 修改密码
    int updatePassword(@Param("id") Integer id, @Param("newPassword") String newPassword);
}