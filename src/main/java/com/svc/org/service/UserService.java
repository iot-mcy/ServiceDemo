package com.svc.org.service;

import com.svc.org.po.UserCustom;
import com.svc.org.po.UserQueryVo;

import java.util.List;

public interface UserService {
    List<UserCustom> findUserList(UserQueryVo userQueryVo) throws Exception;
}
