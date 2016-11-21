package com.jsqix.yunpos.app.api;

import android.util.Log;

import com.jsqix.yunpos.app.utils.UAD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ApiClient {
    // 测试服务器
    public static final String IP = UAD.getRequestIp();

    public final static String UTF_8 = "UTF-8";
    public final static String ANDRID_SDK_KEY = UAD.getParaMd5Key();
    public final static String SECRET_KEY = UAD.getPswMd5Key();

    public static String makeGetMessage(String srcUrl, Map<String, Object> data)
            throws IOException {
        String url = null;
        if (data != null) {
            Log.v("XXXXX", "--------------------------");
            Log.i("签名前----", data.toString());
            String data2 = getSignAfter(data, ApiClient.ANDRID_SDK_KEY);
            Log.i("签名后----", data2);
            data.put("hmac", data2);
            data2 = makePostData2(data);

            url = srcUrl + "?" + data2;
            Log.i("发送的请求----", url);
        } else {
            url = srcUrl;
        }
        return url;
    }

    private static String makePostData2(Map<String, Object> data)
            throws UnsupportedEncodingException {

        StringBuilder postData = new StringBuilder();

        for (String name : data.keySet()) {
            postData.append(name);
            postData.append('=');
            if (data.get(name) == null) {
                postData.append("");
            } else {
                postData.append(URLEncoder.encode(
                        URLEncoder.encode(data.get(name) + "", "utf-8"),
                        "utf-8"));
            }
            postData.append('&');
        }

        return postData.deleteCharAt(postData.lastIndexOf("&")).toString();
    }

    /**
     * map进行排序
     *
     * @param argMap
     * @return
     */
    public static String getSignAfter(Map<String, Object> argMap, String sin) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        for (Entry<String, Object> tMap : argMap.entrySet()) {
            treeMap.put(tMap.getKey(), tMap.getValue() + "");
        }
        StringBuffer ret = new StringBuffer();
        for (Entry<String, String> map : treeMap.entrySet()) {
            String key = map.getKey();
            String value = map.getValue();
            System.out.print(key + "=");
            if (value == null)
                value = "";
            System.out.println(value);
            if (key.contains("img"))
                continue;
            ret.append(key);
            ret.append("=");
            ret.append(value);
            ret.append("&");
        }
        if (ret.toString().endsWith("&")) {
            ret.delete(ret.toString().length() - 1, ret.toString().length());
        }
        String sign = getSign(ret.toString(), sin);
        return sign;
    }

    /**
     * 签名
     *
     * @param signAfter
     * @return
     */
    private static String getSign(String signAfter, String key) {
        Log.v("加密前：", signAfter);
        String sign = Md5.getMD5(signAfter + key, "utf-8");
        Log.v("加密后：", sign);
        return sign;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
