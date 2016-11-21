package com.jsqix.yunpos.app.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RemoteViews;

import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.R;
import com.jsqix.yunpos.app.bean.UpdateBean;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by dq on 2016/4/21.
 */
public class AppUpgradeService extends Service {
    private NotificationManager mNotificationManager = null;
    private Notification mNotification = null;
    private PendingIntent mPendingIntent = null;
    public static final int mNotificationId = 0x12345678;

    // 下载包保存路径
    private String savePath = "";
    // apk保存完整路径
    private String apkFilePath = "";
    // 临时下载文件路径
    private String tmpFilePath = "";
    // 下载文件大小
    private String apkFileSize;
    // 已下载文件大小
    private String tmpFileSize;
    // 安装包下载地址
    private String apkUrl = "";
    //下载的文件
    private File ApkFile;
    private UpdateBean mUpdate;
    private String apkName = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mUpdate = (UpdateBean) intent.getExtras().getSerializable("bean");
        apkName = getPackageName() + "_" + mUpdate.getObj().getApp_version() + ".apk";
        String tmpApk = "yunpos";
        // 判断是否挂载了SD卡
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory() + File.separator +
                    getPackageName() + File.separator + "update" + File.separator;
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            apkFilePath = savePath + apkName;
            tmpFilePath = savePath + tmpApk;
        }
        // 没有挂载SD卡，无法下载文件
        if (CommUtils.isEmpty(apkFilePath)) {
            Utils.makeToast(getBaseContext(), "无法下载安装文件，请检查SD卡是否挂载");
            return super.onStartCommand(intent, flags, startId);
        }
        tmpFilePath = savePath + tmpApk;
        ApkFile = new File(apkFilePath);
        if (checkApkFile(ApkFile)) {
            install();
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotification = new Notification();

        mNotification.contentView = new RemoteViews(getApplication().getPackageName(), R.layout.notification_item);
        mPendingIntent = getInstallIntent(false);
        mNotification.icon = R.mipmap.ic_launcher;
        mNotification.tickerText = "开始下载";
        mNotification.contentIntent = mPendingIntent;
        mNotification.contentView.setProgressBar(R.id.pb_progress, 100, 0, false);
        mNotification.contentView.setTextViewText(R.id.tv_state, "0%");
        mNotificationManager.cancel(mNotificationId);
        mNotificationManager.notify(mNotificationId, mNotification);
        // 输出临时下载文件
        File tmpFile = new File(tmpFilePath);
        if (tmpFile.exists())
            tmpFile.delete();
        downApp(ApkFile);
        return super.onStartCommand(intent, flags, startId);
    }

    private void downApp(final File ApkFile) {
        apkUrl = mUpdate.getUrl() + mUpdate.getObj().getDown_url();
        RequestParams params = new RequestParams(apkUrl);
        params.setAutoResume(true);
        params.setSaveFilePath(tmpFilePath);
        params.setLoadingUpdateMaxTimeSpan(100);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                int progress = (int) (current * 1.0 / total * 100);
                tmpFileSize = CommUtils.toFormat(current * 1.0 / 1024 / 1024) + "MB";
                apkFileSize = CommUtils.toFormat(total * 1.0 / 1024 / 1024) + "MB";
                mNotification.contentView.setProgressBar(R.id.pb_progress, 100, progress, false);
                mNotification.contentView.setTextViewText(R.id.tv_state, progress + "%" + "/t" + tmpFileSize + "/" + apkFileSize);
                mNotificationManager.notify(mNotificationId, mNotification);
            }

            @Override
            public void onSuccess(File result) {
                mNotification.contentView.setViewVisibility(R.id.pb_progress, View.GONE);
                mNotification.defaults = Notification.DEFAULT_SOUND;
                mNotification.contentIntent = getInstallIntent();
                mNotification.contentView.setTextViewText(R.id.tv_state, "下载完成。");
                mNotificationManager.notify(mNotificationId, mNotification);
                result.renameTo(ApkFile);
                install();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Utils.makeToast(getBaseContext(), "下载失败,请稍候重试");
                mNotificationManager.cancel(mNotificationId);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 通知栏点击回调事件
     *
     * @param isFailure 重新下载
     * @return
     */
    private PendingIntent getInstallIntent(boolean isFailure) {
        Intent intent = new Intent();
        if (isFailure) {// 下载失败点击重新下载

        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setClass(getApplication().getApplicationContext(), AppUpgradeService.class);
        }
        return PendingIntent.getActivity(AppUpgradeService.this, 0, intent, 0);

    }

    // 下载完成打开安装界面
    private PendingIntent getInstallIntent() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File file = new File(savePath, apkName);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    public boolean checkApkFile(File ApkFile) {
        boolean result = false;
        try {
            if (ApkFile.exists() && ApkFile.isFile()) {
                PackageManager pManager = getPackageManager();
                PackageInfo pInfo = pManager.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
                if (pInfo == null
                        || !mUpdate.getObj().getApp_version().equalsIgnoreCase(
                        "v" + pInfo.versionName)) {
                    ApkFile.delete();
                    result = false;
                } else {
                    result = true;
                }
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    //安装
    private void install() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                final File file = new File(savePath, apkName);
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("android.intent.action.VIEW");
                String type = "application/vnd.android.package-archive";
                intent.setDataAndType(Uri.fromFile(file), type);
                startActivity(intent);
            }
        }).start();
    }
}
