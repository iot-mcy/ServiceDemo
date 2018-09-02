package com.svc.org.controller;

import com.svc.org.authorization.IgnoreSecurity;
import com.svc.org.bean.ResponseEntity;
import com.svc.org.po.User;
import com.svc.org.po.UserCustom;
import com.svc.org.po.UserQueryVo;
import com.svc.org.service.TokenManager;
import com.svc.org.service.UserService;
import com.svc.org.utils.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.svc.org.utils.Constants.*;


@RestController
@RequestMapping("/user.svc")
public class UserController {

    /**
     * Log4j日志处理(@author: rico)
     */
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private TokenManager tokenManager;

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
    public List<UserCustom> getUser2(@PathVariable("username") String username) {

        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        //由于这里使用动态sql，如果不设置某个值，条件不会拼接在sql中
//        userCustom.setEmail("meng@id.com");
//        userCustom.setUsername(username);
        userCustom.setId(1000);
        userQueryVo.setUserCustom(userCustom);

        List<UserCustom> userList = new ArrayList<UserCustom>();
        try {
            userList = userService.findUserList(userQueryVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @ResponseBody
    @RequestMapping(value = "/getUserByID/{userID}")
    public ResponseEntity<User> getUserById(@PathVariable("userID") int userId) throws Exception {
        User user = null;
        user = userService.findUserById(userId);
        ResponseEntity<User> responseEntity = new ResponseEntity<User>();
        if (user == null) {
            responseEntity.setStatus(-1);
            responseEntity.setMsg("失败");
//            throw new CustomException("找不到相关数据");
        } else {
            responseEntity.setStatus(0);
            responseEntity.setMsg("成功");
            responseEntity.setData(user);
        }
        return responseEntity;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @IgnoreSecurity
    public ResponseEntity<Object> login(@RequestBody @Valid User u, BindingResult br, HttpServletResponse response) throws Exception {

        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>();
        User user = null;

        if (br.hasErrors()) {
            List<FieldError> list = br.getFieldErrors();
            for (FieldError fieldError : list) {
                System.out.println("属性名" + fieldError.getField() + ">>>" + fieldError.getDefaultMessage());
                if (fieldError.getField().equals("username")) {
                    responseEntity.setStatus(ERROR_CODE);
                    responseEntity.setMsg(fieldError.getDefaultMessage());
                    responseEntity.setData(user);

                } else if (fieldError.getField().equals("password")) {
                    responseEntity.setStatus(ERROR_CODE);
                    responseEntity.setMsg(fieldError.getDefaultMessage());
                    responseEntity.setData(user);
                }
                return responseEntity;
            }

        }

        user = userService.login(u);

        if (user == null) {
            responseEntity.setStatus(ERROR_CODE);
            responseEntity.setMsg("用户名或者密码错误");
            responseEntity.setData(user);
//            throw new Exception("找不到相关数据");
        } else {
            String token = tokenManager.createToken(user.getUsername());
            Cookie cookie = new Cookie(Constants.DEFAULT_TOKEN_NAME, token);
            response.addCookie(cookie);

            responseEntity.setStatus(SUCCESS_CODE);
            responseEntity.setMsg(SUCCESS_STR);
            user.setToken(token);
            responseEntity.setData(user);
        }
        return responseEntity;
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpServletRequest request) throws Exception {
        String token = request.getHeader(Constants.DEFAULT_TOKEN_NAME);
        tokenManager.deleteToken(token);

        ResponseEntity entity = new ResponseEntity();
        entity.setStatus(0);
        entity.setMsg("deleteToken");
        return entity;
    }

    @ResponseBody
    @RequestMapping("/image/uploadImg")
    public ResponseEntity editItemsSubmit(
            Model model,
            HttpServletRequest request,
            Integer id,
            @ModelAttribute("imgfile")
                    BindingResult bindingResult,
            MultipartFile items_pic
    ) throws Exception {
        //原始名称
        String originalFilename = items_pic.getOriginalFilename();
        //上传图片
        if (items_pic != null && originalFilename != null && originalFilename.length() > 0) {

            //存储图片的物理路径
            String pic_path = "D:\\tmp\\";


            //新的图片名称
            String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
            //新图片
            File newFile = new File(pic_path + newFileName);

            //将内存中的数据写入磁盘
            items_pic.transferTo(newFile);


        }

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setStatus(0);
        responseEntity.setMsg("");
        responseEntity.setData("");
        return responseEntity;
    }

    /**
     * 上传单个附件
     *
     * @param request
     * @param file
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/uploadAttachment", method = RequestMethod.POST)
    public ResponseEntity<String> upload(HttpServletRequest request,
                                         @RequestParam("file") MultipartFile file) throws Exception {

        ResponseEntity<String> responseEntity = new ResponseEntity<String>();

        //如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            //上传文件路径
            String path = request.getServletContext().getRealPath("/images/");
            //上传文件名
            String filename = file.getOriginalFilename();
            File filepath = new File(path, filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
            responseEntity.setData("success");
            responseEntity.setStatus(0);
            responseEntity.setMsg("成功");
            return responseEntity;
        } else {
            responseEntity.setData("error");
            responseEntity.setStatus(-1);
            responseEntity.setMsg("失败");
            return responseEntity;
        }

    }


    /**
     * 上传多个附件
     *
     * @param request
     * @param multipartFiles
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/uploadAttachments", method = RequestMethod.POST)
    public ResponseEntity<String> uploadList(HttpServletRequest request,
                                             @RequestParam("file") MultipartFile[] multipartFiles) throws Exception {
        //上传文件会自动绑定到MultipartFile中
        ResponseEntity<String> responseEntity = new ResponseEntity<String>();
        StringBuilder json = new StringBuilder();
        for (MultipartFile multipartFile : multipartFiles) {
            //如果文件不为空，写入上传路径
            if (!multipartFile.isEmpty()) {
                //上传文件路径
                String path = request.getServletContext().getRealPath("/images/");
                //上传文件名
                String filename = multipartFile.getOriginalFilename();
                json.append(path + filename + ";");
                File filepath = new File(path, filename);
                //判断路径是否存在，如果不存在就创建一个
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                //将上传文件保存到一个目标文件当中
                multipartFile.transferTo(new File(path + File.separator + filename));
            }
        }

        //这里需要修改
        responseEntity.setData(json.toString());
        responseEntity.setStatus(0);
        responseEntity.setMsg("成功");
        return responseEntity;
    }

    @RequestMapping(value = "/downloadAttachment/{path}")
    public byte[] download(HttpServletRequest request,
                           @PathVariable("path") String path,
                           Model model) throws Exception {
        //下载文件路径
        String path1 = request.getServletContext().getRealPath("/images/");
        File file = new File(path1 + File.separator + path);

//        HttpHeaders headers = new HttpHeaders();
//        //下载显示的文件名，解决中文名称乱码问题
//        String downloadFileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
//        //通知浏览器以attachment（下载方式）打开图片
//        headers.setContentDispositionFormData("attachment", downloadFileName);
//        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

//        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>();
//        responseEntity.setData(FileUtils.readFileToByteArray(file));
//        responseEntity.setMsg("Ok");
//        responseEntity.setStatus(0);
        return FileUtils.readFileToByteArray(file);
//        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
//                headers, HttpStatus.CREATED);
    }
}
