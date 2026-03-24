package com.ch.ch9.service.admin;

import com.ch.ch9.entity.BUser;

import java.util.List;

public interface UserService {
    List<BUser> findAllUsers();
    List<BUser> findByuserName(String userName);
    BUser findUserById(Integer id);
    void addUser(BUser user) throws Exception;
    void updateUser(BUser user) throws Exception;
    void deleteUser(Integer id) throws Exception;
}