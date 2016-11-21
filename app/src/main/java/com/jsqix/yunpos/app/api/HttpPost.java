package com.jsqix.yunpos.app.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jsqix.utils.ACache;
import com.jsqix.yunpos.app.utils.UAD;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class HttpPost extends AsyncTask<String, String, String> {
    String response = "";
    int resultCode = 0;

    Context context;
    InterfaceHttpPost mListener;
    Map<String, Object> postMap;
//    private CustomeDialog customeDialog;

    /**
     * @param context
     * @param params
     * @param listener
     */
    public HttpPost(Context context, InterfaceHttpPost listener, Map<String, Object> params) {
        this.postMap = params;
        String hmac = ApiClient.getSignAfter(params, ApiClient.ANDRID_SDK_KEY);
        postMap.put("hmac", hmac);
        this.mListener = listener;
        this.context = context;
    }

    public abstract void onPreExecute(); /*{
        customeDialog = new CustomeDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.view_loading, null);
        customeDialog.setView(view);
        customeDialog.show();
    }*/

    public String doInBackground(String... urls) {
        return httpPost(urls);
    }

    public void onPostExecute(String response) {

//        if (customeDialog != null && customeDialog.isShowing()) {
//            customeDialog.dismiss();
//        }
        System.out.println(response);
        if (mListener != null) {
            mListener.postCallback(resultCode, response);
        }
    }

    @SuppressLint("NewApi")
    public String httpPost(String... urls) {
        try {
            for (String url : urls) {

                url = ApiClient.IP + url;
                XutilsPost(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Exception", e.getMessage() == null ? "" : e.getMessage());
        } catch (Throwable t) {

        }
        return response;
    }

    private void XutilsPost(String url) throws Throwable {
        RequestParams params = new RequestParams(url);
        params.setHeader(UAD.TOKEN, ACache.get(context).getAsString(UAD.TOKEN));
        params.setConnectTimeout(30*1000);
        Iterator<Entry<String, Object>> strings = postMap.entrySet().iterator();
        while (strings.hasNext()) {
            Entry<String, Object> entry = strings.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List<?>) {
                List imgs = (List) value;
                for (Object img : imgs) {
                    File file = new File(img + "");
                    params.addBodyParameter(key, file, "image/*", file.getName());
                }
            } else {
                if (key.contains("img")) {
                    File file = new File(value + "");
                    params.addBodyParameter(key, file, "image/*", file.getName());
                } else {
                    params.addBodyParameter(key, value + "");
                }
            }
        }
        response = x.http().postSync(params, String.class);
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public interface InterfaceHttpPost {
        void postCallback(int resultCode, String result);
    }
}
