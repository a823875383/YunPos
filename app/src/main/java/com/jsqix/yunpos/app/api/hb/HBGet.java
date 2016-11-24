package com.jsqix.yunpos.app.api.hb;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jsqix.yunpos.app.api.face.InterfaceHttpGet;
import com.jsqix.yunpos.app.utils.hb.HttpClientTool;

import java.io.IOException;
import java.util.Map;


public abstract class HBGet extends AsyncTask<String, String, String> {
    String response = "";
    int resultCode = 0;
    InterfaceHttpGet mListener;
    Context context;
    Map<String, String> postMap;

    public HBGet(Context context, Map<String, String> params,
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
                OkHttpRequest(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Exception", e.getMessage() == null ? "" : e.getMessage());
        } catch (Throwable t) {

        }
        return response;
    }

    private void OkHttpRequest(String url) throws IOException {
        HttpClientTool httpClientTool = HttpClientTool.getInstance();
        if (postMap == null || postMap.size() == 0) {
            response = httpClientTool.sendGet(url);
        } else {
            response = httpClientTool.sendGet(url, postMap);
        }
    }


    public void setResultCode(int code) {
        this.resultCode = code;
    }


}
