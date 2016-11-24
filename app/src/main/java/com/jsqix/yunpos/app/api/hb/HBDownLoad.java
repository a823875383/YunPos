package com.jsqix.yunpos.app.api.hb;

import android.content.Context;
import android.os.AsyncTask;

import com.jsqix.yunpos.app.api.face.InterfaceHttpGet;
import com.jsqix.yunpos.app.utils.hb.HttpClientTool;

import java.io.IOException;


/**
 * Created by dongqing on 2016/11/18.
 */

public abstract class HBDownLoad extends AsyncTask<String, String, String> {
    String response = "";
    int resultCode = 0;
    InterfaceHttpGet mListener;
    Context context;

    public HBDownLoad(Context context,
                      InterfaceHttpGet listener) {
        this.mListener = listener;
        this.context = context;
    }

    public abstract void onPreExecute();

    public void onPostExecute(String response) {
        if (mListener != null)
            mListener.getCallback(resultCode, response);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            OkHttpRequest(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void OkHttpRequest(String url) throws IOException {
        HttpClientTool httpClientTool = HttpClientTool.getInstance();
        response = httpClientTool.downLoadImg(url);
    }

    public void setResultCode(int code) {
        this.resultCode = code;
    }
}
