package com.ch.ch9.repository.admin;


import com.ch.ch9.entity.BUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminUserRepository")
public interface UserRepository {
    List<BUser> findAllUsers();
    List<BUser> findByUsername(String username);
    BUser findUserById(Integer id);
    int addUser(BUser user);
    int updateUser(BUser user);
    int deleteUser(Integer id);
    void resetAutoIncrement();
}