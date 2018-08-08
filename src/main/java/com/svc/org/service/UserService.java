package com.svc.org.service;

import com.svc.org.po.User;
import com.svc.org.po.UserCustom;
import com.svc.org.po.UserQueryVo;

import java.util.List;

public interface UserService {
    User findUserById(int userId) throws Exception;

    List<UserCustom> findUserList(UserQueryVo userQueryVo) throws Exception;
}
