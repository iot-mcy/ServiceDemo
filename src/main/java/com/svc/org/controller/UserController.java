package com.svc.org.controller;

import com.svc.org.bean.ResponseEntity;
import com.svc.org.po.User;
import com.svc.org.po.UserCustom;
import com.svc.org.po.UserQueryVo;
import com.svc.org.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user.svc")
public class UserController {

    /**
     * Log4j日志处理(@author: rico)
     */
    private static final Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login/{UserID}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("UserID") int UserID) {

        ResponseEntity<User> userFestFulBean = new ResponseEntity<User>();
        if (UserID == 10086) {
            User user = new User();
            user.setId(10086);
            user.setUsername("mcy");
            user.setPassword("123456");
            userFestFulBean.setData(user);
            userFestFulBean.setStatus(0);
            userFestFulBean.setMsg("成功.");
        } else {
            userFestFulBean.setStatus(-1);
            userFestFulBean.setMsg("失败.");
        }

        return userFestFulBean;
    }

    @ResponseBody
    @RequestMapping(value = "/get/{username}")
    public List<UserCustom> getUser2(@PathVariable("username") String username){

        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        //由于这里使用动态sql，如果不设置某个值，条件不会拼接在sql中
        userCustom.setEmail("meng@id.com");
        userCustom.setUsername("meng");
        userQueryVo.setUserCustom(userCustom);

        List<UserCustom> userList = new ArrayList<UserCustom>();
        try {
            userList = userService.findUserList(userQueryVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    
}
