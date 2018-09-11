package com.svc.org.service.impl;

import com.svc.org.authorization.CodecUtil;
import com.svc.org.authorization.TokenInfo;
import com.svc.org.service.TokenManager;
import com.svc.org.utils.StringUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.svc.org.utils.Constants.TOKEN_EXPIRES_HOUR;


/**
 * 作者 mcy
 * 日期 2018/9/8 17:49
 * token 管理
 */
@Transactional
public class DefaultTokenManager implements TokenManager {

    /**
     * 将token存储到JVM内存(ConcurrentHashMap)中
     */
    private static Map<String, TokenInfo> tokenMap = new ConcurrentHashMap<String, TokenInfo>();

    /**
     * 利用UUID创建Token(用户登录时 ， 创建Token)
     *
     * @param username
     * @return
     */
    public String createToken(String username) {
        String token = CodecUtil.createUUID();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUsername(username);
        tokenInfo.setTime(System.currentTimeMillis());
        tokenMap.put(token, tokenInfo);
        return token;
    }

    /**
     * Token验证(用户登录验证)
     *
     * @param token
     * @return
     */
    public boolean checkToken(String token) {
        if (StringUtil.isNotEmpty(token)) {
            if (tokenMap.containsKey(token)) {
                TokenInfo tokenInfo = tokenMap.get(token);
                long currentTime = System.currentTimeMillis();
                long l = currentTime - tokenInfo.getTime();
                int hour = (int) (l / (60 * 60 * 1000));
                if (hour > TOKEN_EXPIRES_HOUR) {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
     * Token删除(用户登出时 ， 删除Token)
     *
     * @param token
     */
    public void deleteToken(String token) {
        // TODO Auto-generated method stub
        tokenMap.remove(token);
    }
}
