package com.jsqix.yunpos.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.Result;
import com.jsqix.utils.StringUtils;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.view.TitleBar;
import com.zxing.camera.CameraManager;
import com.zxing.decode.DecodeThread;
import com.zxing.utils.BeepManager;
import com.zxing.utils.CaptureActivityHandler;
import com.zxing.utils.InactivityTimer;
import com.zxing.utils.ScanningImageTools;
import com.zxing.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ContentView(R.layout.activity_capture)
public class CaptureActivity extends BaseAty implements SurfaceHolder.Callback {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    private static final String TAG = CaptureActivity.class.getSimpleName();
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private Rect mCropRect = null;

    @ViewInject(R.id.capture_preview)
    private SurfaceView scanPreview;
    @ViewInject(R.id.capture_container)
    private RelativeLayout scanContainer;
    @ViewInject(R.id.capture_crop_view)
    private RelativeLayout scanCropView;
    @ViewInject(R.id.capture_scan_line)
    private ImageView scanLine;
    @ViewInject(R.id.flash_box)
    private CheckBox flashBox;
    @ViewInject(R.id.photo_btn)
    private Button photoBtn;

    private boolean hasSurface;
    boolean flashOn; // 是否开启闪光灯
    private String photoPath;//二维码图片路径

    private static final int REQUEST_CODE_OPEN_ALBUM = 0x100;
    final static int QUERY = 0, PAY = 1;
    private String result;//二维码内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initCaptureView();
    }

    private void initView() {
        titleBar.setLeftBackground(R.mipmap.ic_back);
        titleBar.setTitle("扫一扫");
        titleBar.setViewBackColor(Color.parseColor("#29282e"));

    }


    private void initCaptureView() {
        flashOn = false;
        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                1.0f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);
    }

    /**
     * 闪光灯开关
     *
     * @param buttonView
     * @param isChecked
     */
    @Event(value = R.id.flash_box, type = CompoundButton.OnCheckedChangeListener.class)
    private void onFlashChange(CompoundButton buttonView, boolean isChecked) {
        cameraManager.setTorch(isChecked);
        if (isChecked) {
            flashBox.setText("关灯");
        } else {
            flashBox.setText("开灯");
        }
    }

    @Event(value = R.id.photo_btn)
    private void onPhotoClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        CaptureActivity.this.startActivityForResult(
                Intent.createChooser(intent, "选择图片"),
                REQUEST_CODE_OPEN_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_OPEN_ALBUM:
                    loading.show();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    // 获取选中图片的路径
                    Cursor cursor = getContentResolver().query(data.getData(),
                            proj, null, null, null);
                    if (cursor.moveToFirst()) {

                        int column_index = cursor
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        photoPath = cursor.getString(column_index);
                        if (photoPath == null) {
                            photoPath = Utils.getPath(getApplicationContext(),
                                    data.getData());
                            Log.i("123path  Utils", photoPath);
                        }
                        Log.i("123path", photoPath);

                    }

                    cursor.close();

                    ScanningImageTools.scanningImage(photoPath,
                            new ScanningImageTools.IZCodeCallBack() {

                                @Override
                                public void ZCodeCallBackUi(Result result) {
                                    // TODO Auto-generated method stub
                                    Message msg = new Message();
                                    msg.obj = result;
                                    mHandle.sendMessage(msg);
                                }
                            });

                    break;

            }

        }

    }

    private Handler mHandle = new Handler() {
        public void handleMessage(Message msg) {
            Result result = (Result) msg.obj;
            if (result == null) {
                loading.dismiss();
                com.jsqix.utils.Utils.makeToast(getApplicationContext(),
                        "图片格式有误");
            } else {
                Log.i("123result", result.toString());
                // 数据返回进行仿乱码处理
                CaptureActivity.this.result = ScanningImageTools.recode(result.toString());
                Matcher matcher = Pattern.compile("[A-Za-z0-9]+$").matcher(CaptureActivity.this.result);
                if (matcher.matches()) {
                } else {
                    loading.dismiss();
                    com.jsqix.utils.Utils.makeToast(CaptureActivity.this, "格式不正确");
                }
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());

        handler = null;

        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        result = rawResult.getText();

    }


    @Override
    public void postCallback(int resultCode, String result) {
        super.postCallback(resultCode, result);
        loading.dismiss();
        try {

        } catch (Exception e) {
            if (StringUtils.isNull(result)) {
                com.jsqix.utils.Utils.makeToast(this, "链接服务器异常");
            } else {
                com.jsqix.utils.Utils.makeToast(this, "json数据异常");
            }
            restartPreviewAfterDelay(1500);
        }
    }


    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder, flashOn);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        // camera error
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("相机打开出错，请稍后重试");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    /**
     * 连续扫描
     *
     * @param delayMS
     */
    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - titleBar.getMeasuredHeight() - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }
}
