package com.jsqix.yunpos.app.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.widget.TextView;

import com.jsqix.utils.StringUtils;

import java.util.List;

/**
 * Created by dq on 2016/5/26.
 */
public class CommUtils extends StringUtils {

    public static boolean isTopActivity(Context context) {
        String packageName = context.getPackageName();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            System.out.println("----包名-----" + tasksInfo.get(0).topActivity.getPackageName());
            // 应用程序位于堆栈的顶层
            if (packageName.equals(tasksInfo.get(0).topActivity
                    .getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static String textToString(TextView tv) {
        return tv.getText().toString().trim();
    }
}
