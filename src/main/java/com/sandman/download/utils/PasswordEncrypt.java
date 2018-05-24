package com.sandman.download.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by sunpeikai on 2018/5/24.
 */
public class PasswordEncrypt {
    public static String getEncryptedPwdBySalt(String password,String salt){
        String hashAlgorithmName = "MD5";
        String credentials = password;
        int hashIterations = 1024;
        Object obj = new SimpleHash(hashAlgorithmName, credentials, ByteSource.Util.bytes(salt), hashIterations);
        return obj.toString();
    }
}
