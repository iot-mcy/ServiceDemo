package com.svc.org.service.impl;

import com.svc.org.mapper.UserMapper;
import com.svc.org.po.User;
import com.svc.org.po.UserCustom;
import com.svc.org.po.UserQueryVo;
import com.svc.org.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserById(int userId) throws Exception {
        return userMapper.findUserById(userId);
    }

    public List<UserCustom> findUserList(UserQueryVo userQueryVo) throws Exception {
        return userMapper.findUserList(userQueryVo);
    }
}
