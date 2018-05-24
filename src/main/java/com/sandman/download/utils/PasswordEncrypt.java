package com.sandman.download.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.security.MessageDigest;

/**
 * Created by sunpeikai on 2018/5/24.
 */
public class PasswordEncrypt {

    /**
     * shiro 加密密码
     * */
    public static String getEncryptedPwdBySalt(String password,String salt){
        String hashAlgorithmName = "MD5";
        String credentials = password;
        int hashIterations = 1024;
        Object obj = new SimpleHash(hashAlgorithmName, credentials, ByteSource.Util.bytes(salt), hashIterations);
        return obj.toString();
    }
    /**
     * MD5加密密码
     * */
    public static String getSecretPasswordMD5(String oriPassword){
        StringBuffer sb = new StringBuffer(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(oriPassword.getBytes("utf-8"));

            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
            }
        } catch (Exception e) {
            System.out.println("Can not encode the string '" + oriPassword + "' to MD5。 Exception:" + e);
            return null;
        }
        return sb.toString();
    }
}
