package com.qf.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Md5Utils {
    public static String md5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            //更新摘要
            messageDigest.update(str.getBytes("utf-8"));
            byte[] digest = messageDigest.digest();
            BigInteger secret = new BigInteger(1, digest);
            return secret.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
