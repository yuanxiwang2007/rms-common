package com.rms.common.util.baidu;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;

public class BaiduMobileApiUtils {

    private static final String mobileUrl = "http://mobsec-dianhua.baidu.com/dianhua_api/open/location?tel=";

    public static MobileAreaModel getMobileAreaInfo(String mobileNumber) throws MalformedURLException {
        String url = mobileUrl + mobileNumber;
        MobileAreaModel mobileAreaModel;
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);
            InputStream is = response.getEntity().getContent();
            String result = inStream2String(is);
            mobileAreaModel = JSONObject.parseObject(result, MobileAreaModel.class);
            return mobileAreaModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String inStream2String(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return new String(baos.toByteArray(), "UTF-8");
    }
}
