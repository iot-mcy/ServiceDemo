package com.svc.org.service.impl;

import com.svc.org.authorization.CodecUtil;
import com.svc.org.service.TokenManager;
import com.svc.org.utils.StringUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Title: TokenManager的默认实现
 * Description: 管理 Token
 *
 * @author rico
 * @created 2017年7月4日 下午4:41:32
 */

@Transactional
public class DefaultTokenManager implements TokenManager {

    /**
     * 将token存储到JVM内存(ConcurrentHashMap)中   (@author: rico)
     */
    private static Map<String, String> tokenMap = new ConcurrentHashMap<String, String>();

    /**
     * @param username
     * @return
     * @description 利用UUID创建Token(用户登录时 ， 创建Token)
     * @author rico
     * @created 2017年7月4日 下午4:46:46
     */
    public String createToken(String username) {
        String token = CodecUtil.createUUID();
        tokenMap.put(token, username);
        return token;
    }


    /**
     * @param token
     * @return
     * @description Token验证(用户登录验证)
     * @author rico
     * @created 2017年7月4日 下午4:46:50
     */
    public boolean checkToken(String token) {
        return !StringUtil.isEmpty(token) && tokenMap.containsKey(token);
    }


    /**
     * @param token
     * @description Token删除(用户登出时 ， 删除Token)
     * @author rico
     * @created 2017年7月4日 下午4:46:54
     */
    public void deleteToken(String token) {
        // TODO Auto-generated method stub
        tokenMap.remove(token);
    }
}
