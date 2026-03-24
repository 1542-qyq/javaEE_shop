package com.ch.ch9.repository.admin;


import com.ch.ch9.entity.AUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AdminRepository")
@Mapper
public interface AdminRepository {
    List<AUser> login(AUser aUser);
}
