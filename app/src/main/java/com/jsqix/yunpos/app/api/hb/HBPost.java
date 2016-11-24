package com.jsqix.yunpos.app.api.hb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jsqix.yunpos.app.api.face.InterfaceHttpPost;
import com.jsqix.yunpos.app.utils.hb.HttpClientTool;

import java.util.Map;


public abstract class HBPost extends AsyncTask<String, String, String> {
    String response = "";
    int resultCode = 0;

    Context context;
    InterfaceHttpPost mListener;
    Map<String, String> postMap;

    /**
     * @param context
     * @param params
     * @param listener
     */
    public HBPost(Context context, Map<String, String> params,
                  InterfaceHttpPost listener) {
        this.postMap = params;
        this.mListener = listener;
        this.context = context;
    }

    public abstract void onPreExecute();

    public String doInBackground(String... urls) {
        return httpPost(urls);
    }

    public void onPostExecute(String response) {
        if (mListener != null) {
            mListener.postCallback(resultCode, response);
        }
    }

    @SuppressLint("NewApi")
    public String httpPost(String... urls) {
        try {
            for (String url : urls) {

                OkHttpPost(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Exception", e.getMessage() == null ? "" : e.getMessage());
        } catch (Throwable t) {

        }
        return response;
    }

    private void OkHttpPost(String url)
            throws Exception {
        HttpClientTool httpClientTool = HttpClientTool.getInstance();
        response = httpClientTool.sendPost(url, postMap);

    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }


}
