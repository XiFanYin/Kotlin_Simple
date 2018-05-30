package xifuyin.tumour.com.a51ehealth.xvideoview;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.provider.Settings;
import android.view.OrientationEventListener;

/**
 * Created by Administrator on 2018/4/26.
 * <p>
 * <p>
 * 重力感应工具类
 */

public class OrientationUtils {

    private Context context;
    private IXVideoView mVideoPlayer;
    private MyOrientationDetector orientationDetector;

    //构造方法
    public OrientationUtils(Context context, IXVideoView mVideoPlayer) {
        OrientationUtils.this.mVideoPlayer = mVideoPlayer;
        OrientationUtils.this.context = context;
        //创建对象
        orientationDetector = new MyOrientationDetector();

    }

    /**
     * 开启旋转监听
     */
    public void enable() {
        orientationDetector.enable();

    }

    /**
     * 关闭旋转监听
     */
    public void disable() {

        if (orientationDetector != null) {
            orientationDetector.disable();
            orientationDetector = null;
            context = null;
            mVideoPlayer = null;
        }


    }

    /**
     * 集成屏幕方向监听
     */
    public class MyOrientationDetector extends OrientationEventListener {

        public MyOrientationDetector() {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {

            //获取当前手机是否开启了屏幕旋转
            boolean autoRotateOn = (Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
            if (mVideoPlayer != null) {

                //当前播放器是否锁住控制界面
                boolean isLock = mVideoPlayer.isLock();
                //表示开启旋转，并且没有锁
                if (autoRotateOn && !isLock) {
                    if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {//手机水平放，如果是全屏，退出全屏

                        if (mVideoPlayer.isFullScreen()) {
                            mVideoPlayer.exitFullScreen();
                        }
                    }
                    //只检测是否有四个角度的改变
                    if (orientation > 350 || orientation < 10) {//手机正放了

                        if (mVideoPlayer.isFullScreen()) {
                            mVideoPlayer.exitFullScreen();
                        }

                    } else if (orientation > 80 && orientation < 100) {//手机向右旋转了

                        if (mVideoPlayer.isNormal()) {
                            mVideoPlayer.enterFullScreen(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                        }
                    } else if (orientation > 170 && orientation < 190) {//手机向倒放了


                        if (mVideoPlayer.isFullScreen()) {
                            mVideoPlayer.exitFullScreen();
                        }

                    } else if (orientation > 260 && orientation < 280) {//手机向左旋转了

                        if (mVideoPlayer.isNormal()) {
                            mVideoPlayer.enterFullScreen(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        }

                    } else {
                        return;
                    }

                }

            }
        }
    }

}
