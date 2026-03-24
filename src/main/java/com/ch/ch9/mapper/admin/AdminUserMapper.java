package com.ch.ch9.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.ch9.entity.AUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminUserMapper extends BaseMapper<AUser> {
    
    /**
     * 登录验证
     */
    @Select("SELECT id, aname, aemail FROM ausertable WHERE aname = #{userName} AND apwd = #{password}")
    AUser login(@Param("userName") String userName, @Param("password") String password);
    
    /**
     * 根据用户名查找用户（用于注册时检查用户名是否已存在）
     */
    @Select("SELECT COUNT(*) FROM ausertable WHERE aname = #{userName}")
    Integer countByuserName(@Param("userName") String userName);
}