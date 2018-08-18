package com.svc.org.mapper;

import com.svc.org.po.Orders;
import com.svc.org.po.User;
import com.svc.org.po.UserCustom;
import com.svc.org.po.UserQueryVo;

import java.util.List;

public interface UserMapper {
    //根据id查询用户信息
    public User findUserById(int id) throws Exception;

    //登录操作，根据用户名和密码查询用户信息
    public User login(User user) throws Exception;

    //用户信息综合查询
    public List<UserCustom> findUserList(UserQueryVo userQueryVo) throws Exception;

    //根据用户名列查询用户列表
    public List<User> findUserByName(String name) throws Exception;

    //添加用户信息
    public void insertUser(User user) throws Exception;

    //删除用户信息
    public void deleteUser(int id) throws Exception;

    //更新用户
    public void updateUser(User user) throws Exception;

    //根据id查询用户信息，使用resultMap输出
    public List<User> findUserByIdResultMap(int id) throws Exception;

    public List<Orders> findOrdersAndUserResultMap(int userId) throws Exception;

    public List<Orders> findOrdersAndOrderDetailResultMap(int userId) throws Exception;

    public User findUserAndItemsResultMap(int userId) throws Exception;
}
