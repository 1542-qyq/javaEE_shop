package com.ch.ch9.dto;

public class LoginResultDTO {
    private Integer id;
    private String userName;
    private String token;

    // 构造方法
    public LoginResultDTO() {
    }

    public LoginResultDTO(Integer id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public LoginResultDTO(Integer id, String userName, String token) {
        this.id = id;
        this.userName = userName;
        this.token = token;
    }

    // Getter和Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResultDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}