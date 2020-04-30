package com.xzsd.app.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>密码工具类</p>
 * <p>创建日期：2018-01-11</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class PasswordUtils {

    /**
     * 生成加密后的密码
     *
     * @param rawPassword 原密码
     * @return 加密后的密码
     */
    public static String generatePassword(String rawPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    /**
     * 密码比较
     *
     * @param oldPassword 原密码
     * @return 加密后的密码
     * @Author SwordKun.
     * @Date 2020-04-24
     */
    public static boolean validatePassword (String oldPassword,String rawPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean flag = true;
        if (!bCryptPasswordEncoder.matches(oldPassword,rawPassword)) {
            return flag = false;
        }
        return flag;
    }
}
