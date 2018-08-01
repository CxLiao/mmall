package com.mmall.util;

import org.apache.shiro.crypto.hash.Sha384Hash;

/**
 * @Description: 加密工具类,之后可作扩展
 * @Author: liaocx
 * @Date: 2018/8/1 16:49
 */
public class CryptoUtil {
    /** 迭代次数，正常情况下2～3次即可 */
    public static final int HASH_ITERATIONS = 3;

    /**
     * 获取Salt值
     * @return
     */
    public static String getSalt() {
        return PropertiesUtil.getProperty("password.salt");
    }

    /**
     * 获取密码通过"SHA-384+盐值"加密后的字符串
     * @param password
     * @return
     */
    public static String getSHA384(String password) {
        Sha384Hash hash = new Sha384Hash(password, getSalt(), HASH_ITERATIONS);
        return hash.toBase64();
    }
}