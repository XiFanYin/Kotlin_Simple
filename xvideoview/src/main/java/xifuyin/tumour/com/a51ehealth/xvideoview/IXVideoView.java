package xifuyin.tumour.com.a51ehealth.xvideoview;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2018/4/17.
 */

public interface IXVideoView {

    /**
     * 开始播放的方法
     */
    void start();

    /**
     * 暂停播放的方法
     */

    void Pause();


    /**
     * 重新播放
     */
    void restart();


    /**
     * 释放播放器，
     */
    void release();

    /**
     * 获取办法给总时长，毫秒
     *
     * @return 视频总时长ms
     */
    long getDuration();

    /**
     * 获取当前播放的位置，毫秒
     *
     * @return 当前播放位置，ms
     */
    long getCurrentPosition();

    /**
     * 拖动进度掉到指定位置
     */
    void seekTo(long touchProgress);

    /**
     * 获取视频缓存进度，体现在进度条的第二个颜色
     */

    int getBufferPercentage();


    /**
     * 进入悬浮窗
     */

    void enterFloatWindow();

    /**
     * 退出悬浮窗
     */

    void exitTinyWindow();

    /**
     * 进入全屏
     */
    void enterFullScreen(int ORIENTATION);

    /**
     * 退出全屏
     */

    void exitFullScreen();


    //获取url，这里是为了处理上一个视频和下一个视频是同一个视频时候，让用户不用快进播放，通过对比url
    String getUrl();

    void setUrl(String url);
    //记录上一个视频播放位置，这是在小屏幕时候又点击了同一个视频记录当前小屏幕播放位置，告诉新的播放器用的

    //===============================播放器状态=================================

    boolean isIdle();

    boolean isPreparing();

    boolean isPlaying();

    boolean isPaused();

    boolean isCompleted();


//=====================================播放器显示的模式===========================================


    boolean isFullScreen();

    boolean isTinyWindow();

    boolean isNormal();


    //=============================================对外提供返回容器布局的方法=============================================

    FrameLayout getContainer();

    //===============================================和声音有关的============================================================================
    //获取播放设置当前的声音
    int getVolume();

    //获取声音的最大值
    int getMaxVolume();

    //设置新的声音
    void setVolume(int newVolume);


    //====================================================================================
    // 当视频播放器页面已经关闭时候，告诉播放器，播放页面Activity已经关闭，点击小屏幕的关闭按钮，需要直接释放掉播放器
    void setPlayerActivityIsDestroy(boolean b);

    void setHistoryPosition(long currentPosition);

    //获取当前视频图层是否已经被锁，如果被锁，重力感应失效
    boolean isLock();

    Context getContexts();
}
