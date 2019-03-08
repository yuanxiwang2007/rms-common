package com.rms.common.util;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by wk on 2017/7/11.
 */
public class Md5Util {
    /**
     * 利用MD5进行加密
     * 　　* @param str  待加密的字符串
     * 　　* @return  加密后的字符串
     * 　　* @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * 　　 * @throws UnsupportedEncodingException
     */
    public static String Md5Base64(String str) throws Exception {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    /**
     * 使用md5的算法进行加密
     *
     * @param plainText 加密明文
     * @return 加密密文
     */
    public static String encoderByMd5(String plainText) {
        char hexDigits[] = {'0', '1', '2', '3',
                '4', '5', '6', '7',
                '8', '9', 'a', 'b',
                'c', 'd', 'e', 'f'
        };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");

            byte[] strTemp = plainText.getBytes();
            mdTemp.update(strTemp);

            byte[] md = mdTemp.digest();

            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断用户密码是否正确
     * 　　　　* @param newVal  用户输入的密码
     * 　　　　 * @param oldVal  数据库中存储的密码－－用户密码的摘要
     * 　　　　* @return
     * 　　　　* @throws NoSuchAlgorithmException
     * 　　　　* @throws UnsupportedEncodingException
     */
    public static boolean validate(String newVal, String oldVal) throws Exception {
        if (encoderByMd5(newVal).equals(oldVal))
            return true;
        else
            return false;
    }

    public static String createMD5Sign(SortedMap<String, String> signParams, String scret) throws Exception {
        StringBuffer sb = new StringBuffer();
        Set es = signParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + v);
        }
        return encoderByMd5(scret + sb.toString() + scret);
    }
}

