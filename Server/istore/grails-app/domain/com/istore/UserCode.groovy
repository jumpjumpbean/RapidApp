package com.istore
/**
 * 用户验证码相关domain
 * 用户注册激活验证码，找回密码验证码
 */
class UserCode {
    User user
    String code
    int type //1:注册激活码,2:密码找回验证码
    boolean isUsed = false
    static constraints = {

    }

    static mapping = {
        version(false)
        user column: 'user_id'
    }
}
