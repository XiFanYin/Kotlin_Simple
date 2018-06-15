package xifuyin.tumour.com.a51ehealth.xvideoview;

import android.content.Context;

/**
 * Created by Administrator on 2018/4/24.
 * <p>
 * 如果视频在列表中去播放，会导致多个视频同事播放，这里记录一下创建的播放器的多少，这里只允许一个播放器工作，其他播放器释放掉
 */

public class XVideoViewManager {

    private static XVideoViewManager mInstance;
    //播放器成员变量
    private IXVideoView mVideoPlayer;
    private OrientationUtils orientationUtils;


    //构造方法
    private XVideoViewManager() {
    }

    /**
     * 单例模式，生成该类对象
     *
     * @return
     */
    public static XVideoViewManager getInstance() {

        if (mInstance == null) {
            synchronized (FloatWindow.class) {
                mInstance = new XVideoViewManager();
            }
        }
        return mInstance;
    }

    //创建好的播放器设置到当前管理类中，记录下来，
    public void setCurrentNiceVideoPlayer(IXVideoView videoPlayer) {
        if (mVideoPlayer != videoPlayer) {
            if (mVideoPlayer != null && videoPlayer != null) {
                //表示是和上一次播放的url是通一个url
                if (mVideoPlayer.getUrl().equals(videoPlayer.getUrl())) {
                    videoPlayer.setHistoryPosition(mVideoPlayer.getCurrentPosition());
                }
            }
            // 如果播放器不等于之前的播放器，直接释放掉之前的播放器
            releaseXVideoPlayer();
            //同时，让新传入的播放器，记录下来
            mVideoPlayer = videoPlayer;
        }
        //开启重力感应工具类
        if (orientationUtils != null) {
            orientationUtils.disable();
        }
        orientationUtils = new OrientationUtils(videoPlayer.getContexts(), videoPlayer);
        orientationUtils.enable();
    }


    /**
     * 释放掉上一个播放器对象
     */
    public void releaseXVideoPlayer() {
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
            mVideoPlayer = null;
        }
    }

    /**
     * 当用户点击了Home键
     */
    public void onBackground() {
        //如果App不等于null，并且正在播放中，让播放器暂停
        if (mVideoPlayer != null && mVideoPlayer.isPlaying()) {
            mVideoPlayer.Pause();
        }
        //如果是小屏，退出小屏
        if (mVideoPlayer != null && mVideoPlayer.isTinyWindow()) {
            mVideoPlayer.exitTinyWindow();
        }

    }

    /**
     * 当前界面从新获取焦点
     */
    public void onResume() {
        //如果播放器处于暂停状态，，让播放器从新播放视频
        if (mVideoPlayer != null && mVideoPlayer.isPaused()) {
            mVideoPlayer.restart();
        }

    }

    /**
     * 如果只是退出播放器，而不是退出app的时候，只有再不是小屏的时候才释放掉播放器
     */
    public void onDestroy() {
        //如果当前播放页面关闭，并且播放器不处于Tiny模式，直接释放掉
        if (mVideoPlayer != null && !mVideoPlayer.isTinyWindow()) {
            releaseXVideoPlayer();
        } else {
            if (mVideoPlayer != null) {
                mVideoPlayer.setPlayerActivityIsDestroy(true);//否则告诉当前自定义播放器，对应的Activity已经关闭
            }
        }
//        关闭重力感应工具类
        orientationUtils.disable();
        orientationUtils = null;
    }

    /**
     * App退出之后，直接释放掉播放器，不管是什么状态
     */
    public void onExitApp() {
        //如果播放器不等于null，直接让播放器释放掉 ， 如果播放器处于Tiny模式，退出这个模式
        if (mVideoPlayer != null) {
            if (mVideoPlayer.isTinyWindow()) {
                mVideoPlayer.exitTinyWindow();
            }
            releaseXVideoPlayer();
        }

    }


}
