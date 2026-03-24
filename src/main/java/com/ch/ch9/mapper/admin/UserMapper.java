package com.ch.ch9.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.ch9.entity.BUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminUsermapper")
@Mapper
public interface UserMapper extends BaseMapper<BUser> {
    List<BUser> findAllUsers();
    List<BUser> findByuserName(String userName);
    void resetAutoIncrement();
}