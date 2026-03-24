package com.ch.ch9.service.admin;

import com.ch.ch9.entity.BUser;
import com.ch.ch9.mapper.admin.UserMapper;
import com.ch.ch9.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    @Qualifier("adminUsermapper")
    private UserMapper usermapper;

    @Override
    public List<BUser> findAllUsers() {
        logger.info("开始执行findAllUsers()方法");
        try {
            List<BUser> users = usermapper.findAllUsers();
            logger.info("数据库查询完成，返回用户数量: {}", users.size());

            if (users.isEmpty()) {
                logger.warn("数据库中没有用户数据，可能原因：表为空或查询条件错误");
            } else {
                for (int i = 0; i < users.size(); i++) {
                    BUser user = users.get(i);
                    logger.info("用户 {}: ID={}, 用户名={}, 邮箱={}",
                            i+1, user.getId(), user.getUserName(), user.getBemail());
                }
            }
            return users;
        } catch (Exception e) {
            logger.error("查询所有用户时发生异常: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public BUser findUserById(Integer id) {
        BUser user = usermapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("未找到ID为" + id + "的用户");
        }
        return user;
    }
    
    @Override
    public List<BUser> findByuserName(String userName) {
        return usermapper.findByuserName(userName);
    }

    @Override
    public void addUser(BUser user) throws Exception {
        // 对密码进行MD5加密
        if (user.getBpwd() != null && !user.getBpwd().isEmpty()) {
            user.setBpwd(MD5Util.encode(user.getBpwd()));
        }
        int result = usermapper.insert(user);
        if (result <= 0) {
            throw new Exception("添加用户失败");
        }
    }

    @Override
    public void updateUser(BUser user) throws Exception {
        // 检查密码是否为空，如果不为空才进行加密
        if (user.getBpwd() != null && !user.getBpwd().isEmpty()) {
            user.setBpwd(MD5Util.encode(user.getBpwd()));
        } else {
            // 如果密码为空，则不更新密码字段
            BUser existingUser = usermapper.selectById(user.getId());
            if (existingUser != null) {
                user.setBpwd(existingUser.getBpwd());
            }
        }
        int result = usermapper.updateById(user);
        if (result <= 0) {
            throw new Exception("更新用户失败");
        }
    }

    @Override
    public void deleteUser(Integer id) throws Exception {
        int result = usermapper.deleteById(id);
        if (result <= 0) {
            throw new Exception("删除用户失败");
        }
        // 重置自增ID，使其从1开始
        usermapper.resetAutoIncrement();
    }
}