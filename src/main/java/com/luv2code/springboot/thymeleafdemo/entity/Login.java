package com.luv2code.springboot.thymeleafdemo.entity;

public class Login {

    private String login_id;

    private String password;

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin_id() {
        return login_id;
    }

    public String getPassword() {
        return password;
    }

    public String toString() {
        return "\"Login { + login_id :" + login_id + ", password: " + password + "}";
    }

}
