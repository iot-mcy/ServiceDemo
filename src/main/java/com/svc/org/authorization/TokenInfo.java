package com.svc.org.authorization;

public class TokenInfo {

    /**
     * 用户名
     */
    private String username;
    /**
     * token 创建时间
     */
    private long time;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
