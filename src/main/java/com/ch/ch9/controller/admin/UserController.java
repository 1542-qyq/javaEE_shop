package com.ch.ch9.controller.admin;

import com.ch.ch9.entity.BUser;
import com.ch.ch9.service.admin.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller("adminUserController")
@RequestMapping("/admin/user")
public class UserController extends AdminBaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public String list(@RequestParam(value = "userName", required = false) String userName, Model model, HttpSession session) {
        logger.info("进入用户列表页面，查询条件: userName = {}", userName);
        try {
            List<BUser> userList;
            if (userName != null && !userName.trim().isEmpty()) {
                userList = userService.findByuserName(userName.trim());
                logger.info("根据用户名查询完成，共 {} 条记录", userList.size());
            } else {
                userList = userService.findAllUsers();
                logger.info("用户列表加载完成，共 {} 条记录", userList.size());
                // 添加日志查看每个用户的id值
                for (int i = 0; i < userList.size(); i++) {
                    BUser user = userList.get(i);
                    logger.info("用户 {}: ID={}, 用户名={}, ID是否为null: {}", 
                               i+1, user.getId(), user.getUserName(), (user.getId() == null));
                }
            }
            model.addAttribute("userList", userList);
            model.addAttribute("userName", userName);
        } catch (Exception e) {
            logger.error("加载用户列表失败: {}", e.getMessage(), e);
            throw e;
        }
        return "admin/userList";
    }

    @RequestMapping("/toAdd")
    public String toAdd(@ModelAttribute("user") BUser user) {
        logger.info("进入添加用户页面");
        return "admin/userAdd";
    }

    @RequestMapping("/add")
    public String add(@ModelAttribute("user") BUser user) throws Exception {
        logger.info("执行添加用户操作: {}", user);
        try {
            userService.addUser(user);
            logger.info("用户添加成功: {}", user);
        } catch (Exception e) {
            logger.error("用户添加失败: {}", e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/user/list";
    }

    @RequestMapping("/toEdit/{id}")
    public String toEdit(@PathVariable("id") Integer id, Model model) {
        logger.info("进入编辑用户页面，接收到的ID: {}, ID类型: {}", id, (id != null ? id.getClass().getName() : "null"));
        try {
            if (id == null) {
                logger.error("ID参数为null");
                throw new IllegalArgumentException("ID参数不能为空");
            }
            logger.info("准备调用userService.findUserById({})", id);
            BUser user = userService.findUserById(id);
            logger.info("加载待编辑用户信息成功，用户: {}", user);
            model.addAttribute("user", user);
        } catch (Exception e) {
            logger.error("加载待编辑用户信息失败，ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
        return "admin/userEdit";
    }

    @RequestMapping("/edit")
    public String edit(@ModelAttribute("user") BUser user) throws Exception {
        logger.info("执行编辑用户操作: {}", user);
        try {
            userService.updateUser(user);
            logger.info("用户编辑成功: {}", user);
        } catch (Exception e) {
            logger.error("用户编辑失败: {}", e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/user/list";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) throws Exception {
        logger.info("执行删除用户操作，ID: {}", id);
        try {
            userService.deleteUser(id);
            logger.info("用户删除成功，ID: {}", id);
        } catch (Exception e) {
            logger.error("用户删除失败，ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/user/list";
    }
}