package com.svc.org.po;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {
    private int id;
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    @Length(max = 20, min = 6, message = "密码应在6-20位")
    private String password;
    private String email;
    private int sex;
    private String address;
    private String create_time;
    private String token;

//    @Null   被注释的元素必须为 null
//    @NotNull    被注释的元素必须不为 null
//    @AssertTrue     被注释的元素必须为 true
//    @AssertFalse    被注释的元素必须为 false
//    @Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值
//    @Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值
//    @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
//    @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
//    @Size(max=, min=)   被注释的元素的大小必须在指定的范围内
//    @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内
//    @Past   被注释的元素必须是一个过去的日期
//    @Future     被注释的元素必须是一个将来的日期
//    @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式

//    private List<Orders> ordersList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    //    public List<Orders> getOrdersList() {
//        return ordersList;
//    }

//    public void setOrdersList(List<Orders> ordersList) {
//        this.ordersList = ordersList;
//    }

    public User() {
    }
}
