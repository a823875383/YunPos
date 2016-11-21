package com.jsqix.yunpos.app.utils;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.jsqix.utils.ACache;
import com.jsqix.utils.LogWriter;

public class BaiDuLocationHelper {

    private Context context;

    private LocationClient mLocationClient;

    private MyLocationListener mMyLocationListener;

    private String address = null;

    private String latitude;
    private String longitude;
    static BaiDuLocationHelper instance;

    public BaiDuLocationHelper(Context context) {
        this.context = context;
        initLocation();
    }

    public static BaiDuLocationHelper getInstance(Context context) {
        if (instance == null) {
            instance = new BaiDuLocationHelper(context);
        }
        return instance;
    }

    public void initLocation() {
        mLocationClient = new LocationClient(context);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);
    }

    public void startLocation() {
        LogWriter.i("TAG", "startLocation");
        if (mLocationClient != null) {
            mLocationClient.start();
            mLocationClient.requestLocation();
        }
    }

    public void stopLocation() {
        LogWriter.i("TAG", "stopLocation");
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    /**
     * 实现实位回调监听
     */
    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                LogWriter.i("TAG", "onReceiveLocation");
                longitude = location.getLongitude() + "";
                latitude = location.getLatitude() + "";
                address = location.getAddrStr();
                if (!CommUtils.isEmpty(longitude) && !longitude.equalsIgnoreCase("4.9e-324") && CommUtils.toDouble(longitude) != 0) {
                    ACache.get(context).put(UAD.LOCATE_LONGITUDE, longitude);
                } else {
                    ACache.get(context).put(UAD.LOCATE_LONGITUDE, "");
                }
                if (!CommUtils.isEmpty(latitude) && !latitude.equalsIgnoreCase("4.9e-324") && CommUtils.toDouble(latitude) != 0) {
                    ACache.get(context).put(UAD.LOCATE_LATITUDE, latitude);
                } else {
                    ACache.get(context).put(UAD.LOCATE_LATITUDE, "");
                }
                if (!CommUtils.isEmpty(address)) {
                    ACache.get(context).put(UAD.LOCATE__ADDRESS, address);
                } else {
                    ACache.get(context).put(UAD.LOCATE__ADDRESS, "");
                }
            } else {
                ACache.get(context).put(UAD.LOCATE_LONGITUDE, "");
                ACache.get(context).put(UAD.LOCATE_LATITUDE, "");
                ACache.get(context).put(UAD.LOCATE__ADDRESS, "");
            }
        }
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

}
