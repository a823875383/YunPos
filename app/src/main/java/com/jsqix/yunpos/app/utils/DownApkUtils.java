package com.jsqix.yunpos.app.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.R;
import com.jsqix.yunpos.app.base.MyApplication;
import com.jsqix.yunpos.app.bean.UpdateBean;
import com.jsqix.yunpos.app.view.CustomeDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by dq on 2016/4/21.
 */
public class DownApkUtils {
    private Context mContext;
    //下载对话框
    private CustomeDialog updateDialog;
    // 进度条
    private ProgressBar mProgress;
    // 显示下载数值
    private TextView mProgressText;
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
    private String curVersionName = "";
    private int curVersionCode;
    private UpdateBean mUpdate;
    private String apkName = "";

    public DownApkUtils(Context context) {
        this.mContext = context;
    }

    /**
     * 获取当前客户端版本信息
     */
    public String getCurrentVersion() {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            curVersionName = info.versionName;
            curVersionCode = info.versionCode;
        } catch (Exception e) {

        }
        return curVersionName;
    }

    public void showUpgrade(UpdateBean updateBean) {
        mUpdate = updateBean;
        Intent intent = new Intent(mContext, AppUpgradeService.class);
        intent.putExtra("bean", mUpdate);
        mContext.startService(intent);
    }

    /**
     * 显示下载对话框
     */
    public void showDownloadDialog(UpdateBean updateBean) {
        mUpdate = updateBean;
        updateDialog = new CustomeDialog(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.update_progress, null);
        updateDialog.setView(view);
        updateDialog.setCancelable(false);
        updateDialog.setCanceledOnTouchOutside(false);
        updateDialog.setParas(0.6f, 0);
        mProgress = (ProgressBar) view.findViewById(R.id.update_progress);
        mProgressText = (TextView) view.findViewById(R.id.update_progress_text);
        updateDialog.show();
        downloadApk();
    }

    //下载
    private void downloadApk() {
        apkName = mContext.getPackageName() + "_" + mUpdate.getObj().getApp_version() + ".apk";
        String tmpApk = "yunpos" + ".tmp";
        // 判断是否挂载了SD卡
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory() + File.separator +
                    mContext.getPackageName() + File.separator + "update" + File.separator;

            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            apkFilePath = savePath + apkName;
            tmpFilePath = savePath + tmpApk;
        }
        // 没有挂载SD卡，无法下载文件
        if (CommUtils.isEmpty(apkFilePath)) {
            Utils.makeToast(mContext, "无法下载安装文件，请检查SD卡是否挂载");
            return;
        }
        final File ApkFile = new File(apkFilePath);
        // 是否已下载更新文件
        if (ApkFile.exists()) {
            PackageInfo packageArchiveInfo = mContext
                    .getPackageManager().getPackageArchiveInfo(
                            apkFilePath, PackageManager.GET_ACTIVITIES);
            if (packageArchiveInfo == null
                    || !mUpdate.getObj().getApp_version().equalsIgnoreCase(
                    "v" + packageArchiveInfo.versionName)) {
                ApkFile.delete();
            } else {
                updateDialog.dismiss();
                install();
                return;
            }
        }
        // 输出临时下载文件
        File tmpFile = new File(tmpFilePath);
        if (tmpFile.exists())
            tmpFile.delete();
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
                mProgress.setProgress(progress);
                mProgressText.setText(tmpFileSize + "/" + apkFileSize);
            }

            @Override
            public void onSuccess(File result) {
                updateDialog.dismiss();
                result.renameTo(ApkFile);
                install();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                updateDialog.dismiss();
                Utils.makeToast(mContext, "下载失败,请稍候重试");
                if (mUpdate.getObj().getForced() == 1) {
                    MyApplication.exitApp();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
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
                mContext.startActivity(intent);
            }
        }).start();
    }
}
