package com.jsqix.yunpos.app.utils.hb;

import android.os.Environment;

import com.android.internal.http.multipart.FilePart;
import com.android.internal.http.multipart.MultipartEntity;
import com.android.internal.http.multipart.Part;
import com.android.internal.http.multipart.StringPart;
import com.jsqix.yunpos.app.base.MyApplication;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

/**
 * httpclient工具类
 *
 * @author CPF
 */
public class HttpClientTool {
    static HttpClientTool httpClientTool;

    public static HttpClientTool getInstance() {
        if (httpClientTool == null) {
            httpClientTool = new HttpClientTool();
        }
        return httpClientTool;
    }

    /**
     * cookie池
     */
    private CookieStore COOKIE_STORE = new BasicCookieStore();
    private String charSet = "UTF-8";
    /**
     * http客户端
     */
    private HttpClient httpClient;
    /**
     * 默认证书校验器
     */
    private static SSLConnectionSocketFactory SSLSF;

    static {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                    null, new TrustStrategy() {
                        // 信任所有
                        public boolean isTrusted(X509Certificate[] chain,
                                                 String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            SSLSF = new SSLConnectionSocketFactory(sslContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建http客户端
     *
     * @param reqUrl
     * @return
     * @author caopf
     * create on 2016-11-12  下午1:22:49
     */
    private HttpClient buildHttpClient(String reqUrl) {
        HttpClientBuilder clientBuilder = HttpClientBuilder.create().setDefaultCookieStore(COOKIE_STORE);
        if (reqUrl.toUpperCase().startsWith("HTTPS")) {
            clientBuilder.setSSLSocketFactory(SSLSF);
        }
        return clientBuilder.build();
    }

    /**
     * 创建httppost对象
     *
     * @param reqUrl
     * @param param
     * @return
     * @author caopf
     * create on 2016-11-12  下午1:28:11
     */
    private HttpPost buildHttpPost(String reqUrl, Map<String, String> param) throws Exception {
        HttpPost post = new HttpPost(reqUrl);
        HttpEntity he = null;
        if (param != null) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (String key : param.keySet()) {
                formparams.add(new BasicNameValuePair(key, param.get(key)));
            }
            he = new UrlEncodedFormEntity(formparams, charSet);
            post.setEntity(he);
        }
        return post;
    }

    /**
     * 设置通用header
     *
     * @param httpMethod
     * @author caopf
     * create on 2016-11-12  下午1:54:39
     */
    public void setCommonHeader(HttpRequestBase httpMethod) {
        httpMethod.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
        httpMethod.setHeader("Accept", "application/json");
        httpMethod.setHeader("X-Requested-With", "XMLHttpRequest");
        httpMethod.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpMethod.setHeader("Accept-Encoding", "zh-CN,zh;q=0.8");
        httpMethod.setHeader("Accept-Language", "gzip, deflate");
        httpMethod.setHeader("Connection", "keep-alive");
    }

    /**
     * 发送标准post请求
     *
     * @param reqUrl
     * @param param
     * @param charSet
     * @return
     * @author caopf
     * create on 2016-11-12  下午1:55:03
     */
    public String sendPost(String reqUrl, Map<String, String> param, String charSet) {
        try {

            httpClient = buildHttpClient(reqUrl);

            HttpPost post = buildHttpPost(reqUrl, param);

            setCommonHeader(post);

            HttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == 302) {
                return sendPost(response.getLastHeader("Location").getValue(), null, charSet);
            }

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String returnStr = EntityUtils.toString(entity, charSet);
                return returnStr;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * 发送通用post请求
     *
     * @param reqUrl
     * @param param
     * @return
     * @author caopf
     * create on 2016-11-12  下午2:07:33
     */
    public String sendPost(String reqUrl, Map<String, String> param) {
        return sendPost(reqUrl, param, charSet);
    }

    /**
     * 生成get请求地址
     *
     * @param url
     * @param params
     * @return
     * @author caopf
     * create on 2016-11-12  下午1:57:07
     */
    private String buildGetUrl(String url, Map<String, String> params) {
        StringBuffer uriStr = new StringBuffer(url);
        if (params != null) {
            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                ps.add(new BasicNameValuePair(key, params.get(key)));
            }
            uriStr.append("?");
            uriStr.append(URLEncodedUtils.format(ps, charSet));
        }
        return uriStr.toString();
    }

    /**
     * 创建httpget对象
     *
     * @param url
     * @param params
     * @return
     * @author caopf
     * create on 2016-11-12  下午1:59:00
     */
    public HttpGet buildHttpGet(String url, Map<String, String> params) {
        return new HttpGet(buildGetUrl(url, params));
    }

    /**
     * 发送通用get请求
     *
     * @param url
     * @param params
     * @param charset
     * @return
     * @author caopf
     * create on 2016-11-12  下午2:01:31
     */
    public String sendGet(String url, Map<String, String> params, String charset) {
        try {

            HttpClient client = buildHttpClient(url);

            HttpGet get = buildHttpGet(url, params);

            setCommonHeader(get);

            HttpResponse response = client.execute(get);

            HttpEntity entity = response.getEntity();

            if (entity != null) {

                return EntityUtils.toString(entity, charset);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * 发送标准get请求
     *
     * @param url
     * @param param
     * @return
     * @author caopf
     * create on 2016-11-12  下午2:05:18
     */
    public String sendGet(String url, Map<String, String> param) {
        return sendGet(url, param, charSet);
    }

    /**
     * 发送标准get请求
     *
     * @param url
     * @return
     * @author caopf
     * create on 2016-11-12  下午2:06:22
     */
    public String sendGet(String url) {
        return sendGet(url, null, charSet);
    }

    /**
     * 标准获取httpentity方法
     *
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpEntity simpleGetHttpEntity(String url, Map<String, String> params) throws Exception {

        HttpClient client = buildHttpClient(url);

        HttpGet get = buildHttpGet(url, params);

        HttpResponse response = client.execute(get);

        setCommonHeader(get);

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            return entity;
        }
        return null;
    }

    /**
     * 通用下载文件方法
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String downLoadImg(String url) {
        String picName = null;
        try {
            String savePath = Environment.getExternalStorageDirectory()
                    + "/" + MyApplication.getInstance().getPackageName() + "/yzm";
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            picName = System.currentTimeMillis() + ".jpg";
            File picFile = new File(savePath, picName);
            picFile.createNewFile();

            HttpEntity entity = simpleGetHttpEntity(url, new HashMap<String, String>());
            InputStream inputStream = entity.getContent();
            OutputStream outStream = new FileOutputStream(picFile);
            int row = IOUtils.copy(inputStream, outStream);
            if (row > 0) {
                outStream.close();
                inputStream.close();
                entity.consumeContent();
                picName = picFile.getPath();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return picName;
    }

    /**
     * 上传图片
     */
    public String uploadImg(String reqUrl, Map<String, String> params) {
        String ret = "";
        try {
            HttpClient client = buildHttpClient(reqUrl);
            HttpPost post = new HttpPost(reqUrl);
            Iterator<Map.Entry<String, String>> strings = params.entrySet().iterator();
            Part[] parts = new Part[params.size()];
            int index = 0;
            while (strings.hasNext()) {
                Map.Entry<String, String> entry = strings.next();
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.contains("upload")) {
                    File file = new File(value);
                    parts[index++] = new FilePart(key, file);
                } else {
                    parts[index++] = new StringPart(key, value);
                }
            }
            MultipartEntity multipartEntity = new MultipartEntity(parts);
            post.setEntity(multipartEntity);
            HttpResponse response = client.execute(post);
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                ret = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {

        }
        return ret;
    }
}
