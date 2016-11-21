package com.jsqix.yunpos.app.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jsqix.utils.ACache;
import com.jsqix.yunpos.app.utils.UAD;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

public abstract class HttpGet extends AsyncTask<String, String, String> {
    String response = "";
    int resultCode = 0;
    InterfaceHttpGet mListener;
    Context context;
    Map<String, Object> postMap;

    // Constructor
    public HttpGet(Context context, Map<String, Object> params,
                   InterfaceHttpGet listener) {
        this.mListener = listener;
        this.postMap = params;
        this.context = context;
    }

    public abstract void onPreExecute();

    public void onPostExecute(String response) {
        if (mListener != null)
            mListener.getCallback(resultCode, response);
    }

    public String doInBackground(String... urls) {
        return getJSON(urls);
    }

    public String getJSON(String... urls) {
        try {
            for (String url : urls) {
                String getUrl = ApiClient.makeGetMessage(ApiClient.IP + url, postMap);
                XutilsRequst(getUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Exception", e.getMessage() == null ? "" : e.getMessage());
        } catch (Throwable t) {

        }
        return response;
    }

    private void XutilsRequst(String url) throws Throwable {
        RequestParams params = new RequestParams(url);
        params.setHeader(UAD.TOKEN, ACache.get(context).getAsString(UAD.TOKEN));
        params.setConnectTimeout(30*1000);
        response = x.http().getSync(params, String.class);
    }

    public void setResultCode(int code) {
        this.resultCode = code;
    }

    public interface InterfaceHttpGet {
        void getCallback(int resultCode, String result);
    }
}
