package com.sandman.download.utils;


import org.apache.shiro.codec.Base64;

/**
 * Created by sunpeikai on 2018/5/24.
 */
public class Test {
    public static void main(String[] args){

        String encode = Base64.encodeToString("COOKIEKEYFORSANDMAN".getBytes());

        System.out.println("encode:" + encode);
        System.out.println("decode:" + Base64.decodeToString(encode));
    }
}
