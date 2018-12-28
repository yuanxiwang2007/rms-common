package com.rms.common.util.baidu;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaiduMapApiUtils {

    /**
     * 根据坐标获取地址信息
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    public static BaiduLocationReverseModel locationRenderReverse(String longitude, String latitude) {
        try {
            Map paramsMap = new LinkedHashMap<String, String>();
            paramsMap.put("callback", "renderReverse");
            paramsMap.put("location", latitude + "," + longitude);
            paramsMap.put("output", "json");
            paramsMap.put("pois", "1");
            paramsMap.put("output", "json");
            paramsMap.put("ak", "oLsvc2Z4nCRD18oXlWhDGYSbGPOF48NQ");
            String paramsStr = BaiduMapApiUtils.toQueryString(paramsMap);
            String wholeStr = new String("/geocoder/v2/?" + paramsStr + "RXvzucvZjvWvUwil5QB7Fg2r7ffhnOdi");
            String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
            String sn = BaiduMapApiUtils.MD5(tempStr);
            // 算得sn后发送get请求
            HttpClient client = new DefaultHttpClient();
            String url = "http://api.map.baidu.com/geocoder/v2/?" + paramsStr + "&sn=" + sn;
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);
            InputStream is = response.getEntity().getContent();
            String result = BaiduMapApiUtils.inStream2String(is);
            result = result.substring("renderReverse&&renderReverse(".length(), result.length() - 1);
            BaiduLocationReverseModel baiduLocationReverseModel = JSON.parseObject(result, BaiduLocationReverseModel
                    .class);
            return baiduLocationReverseModel;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    // 将输入流转换成字符串
    private static String inStream2String(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return new String(baos.toByteArray(), "UTF-8");
    }

    // 对Map内所有value作utf8编码，拼接返回结果
    private static String toQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
