package com.ch.ch9.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@TableName("busertable")
public class BUser {
    @TableId
    private Integer id;

    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String bemail;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不能少于6位")
    private String bpwd;

    @NotEmpty(message = "确认密码不能为空")
    @Size(min = 6, message = "确认密码长度不能少于6位")
    @TableField(exist = false)
    private String rebpwd;

    private String userName;



    // 构造方法
    public BUser() {
    }

    public BUser(String bemail, String bpwd) {
        this.bemail = bemail;
        this.bpwd = bpwd;
    }

    // Getter和Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBemail() {
        return bemail;
    }

    public void setBemail(String bemail) {
        this.bemail = bemail;
    }

    public String getBpwd() {
        return bpwd;
    }

    public void setBpwd(String bpwd) {
        this.bpwd = bpwd;
    }

    public String getRebpwd() {
        return rebpwd;
    }

    public void setRebpwd(String rebpwd) {
        this.rebpwd = rebpwd;
    }

    public String getUserName() {  // ✅ 正确的：getUserName()
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "BUser{" +
                "id=" + id +
                ", bemail='" + bemail + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}