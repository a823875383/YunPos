package com.jsqix.yunpos.app.utils;

public class UAD {
    final public static String UID = "user_id";
    final public static String TOKEN = "token";
    final public static String ANDROID = "1";

    public static boolean NOT_NEW = false;

    final public static String TYPE_DZQ = "1";
    final public static String TYPE_WX = "2";

    final public static String TITLE = "title";
    final public static String LEVEL = "level";
    final public static String CID = "city_id";
    final public static String RESULT = "result";

    final public static String LOCATE_LONGITUDE = "longitude";
    final public static String LOCATE_LATITUDE = "latitude";
    final public static String LOCATE__ADDRESS = "address";

    public static native String getParaMd5Key();

    public static native String getPswMd5Key();

    public static native String getRequestIp();

    public static String getTestIp() {
        return "http://120.55.191.214:8080/api/";
    }

    static {
        System.loadLibrary("yunpos");
    }
}
