package com.sandman.download.utils;

import java.util.UUID;

/**
 * Created by sunpeikai on 2018/4/27.
 */
public class RandomUtils {
    private static final int CODEMAXLENGTH = 6;
    public static String getValidateCode(){
        String random = "";
        for(int i=0;i<CODEMAXLENGTH;i++){
            random += String.valueOf((int)Math.floor(Math.random() * 9 + 1));
        }
        return random;
    }
    public static String getUuidStr(){
        String uuid = UUID.randomUUID().toString().toUpperCase();
        return uuid.replace("-", "");
    }
}
