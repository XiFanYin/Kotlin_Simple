package xifuyin.tumour.com.a51ehealth.xvideoview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by Administrator on 2018/4/17.
 * 具体请采参考项目：https://github.com/XiFanYin/FakeVideoView
 */

public class XVideoView extends FrameLayout implements IXVideoView, TextureView.SurfaceTextureListener {

    private Context mContext;//上下文
    private FrameLayout mContainer;//容器
    private BaseController mController;//控制器布局
    private int mCurrentState = Constants.STATE_IDLE;//记录播放器状态
    private IjkMediaPlayer mediaPlayer;
    private SurfaceTexture mSurfaceTexture;
    private XTextureView mTextureView;
    private int BufferPercentage;
    private int mCurrentMode = Constants.MODE_NORMAL;
    private Surface surface;
    private AudioManager mAudioManager;
    private boolean ActivityIsDestroy = false;
    private long historyPosition;
    private String url;

    public XVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {//小容器添加到大布局中去，这里设置的是MATCH_PARENT，父布局会给父布局最大的布局范围
        mContainer = new FrameLayout(mContext);
        mContainer.setBackgroundColor(Color.BLACK);//设置为黑色背景，
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mContainer, params);
    }

    /**
     * 对外暴漏设置控制器的方法，其实就是向容器中添加一个布局
     *
     * @param mController
     */
    public void setController(BaseController mController) {
        //把播放器恢复到默认状态
        mController.reset();
        mContainer.removeView(mController);//先移除之前添加的控制器，如果第一次添加也不会报错，因为系统已经做了判断
        this.mController = mController;//这里就持有控制器的对象
        mController.setXVideoView(this);//这里设置播放器对象
        //更改当前UI状态为正在准备中，同时调用控制器中更新Ui的方法
        mCurrentState = Constants.STATE_IDLE;
        mController.onPlayStateChanged(mCurrentState);//更新控制器为正在准备状态
        //把控制器布局添加到容器中
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer.addView(mController, params);
    }

    //用户点击控制器播放按钮时候调用的方法
    @Override
    public void start() {
        //记录当前创建的播放器，处理只能有一个视频播放
        XVideoViewManager.getInstance().setCurrentNiceVideoPlayer(this);
        //初始化音频管理对象
        initAudioManager();
        //初始化播放器
        initMediaPlayer();
        //初始化TextTureView控件
        initTextureView();
        //添加TextureView到容器中
        addTextureView();
    }


    /**
     * 初始化音频管理对象
     */
    private void initAudioManager() {
        if (mAudioManager == null) {
            //获取音频管理器
            mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);//获取音频
            //  请求音频的焦点
            mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
    }


    //初始化播放器
    private void initMediaPlayer() {

        if (mediaPlayer == null) {
            mediaPlayer = new IjkMediaPlayer();
        }
    }

    //初始化TextureView，并设置创建SurfaceTexture完成监听
    private void initTextureView() {

        if (mTextureView == null) {
            mTextureView = new XTextureView(mContext);
            mTextureView.setSurfaceTextureListener(this);
        }

    }

    /**
     * 添加TextureView到容器中
     */
    private void addTextureView() {
        mContainer.removeView(mTextureView);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        mContainer.addView(mTextureView, 0, params);
    }


    //===============================texttureView.setSurfaceTextureListener(this)回调======================================================
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        if (mSurfaceTexture == null) {//如果为null，赋值
            mSurfaceTexture = surfaceTexture;//把mSurfaceTexture变成成员变量,解决切换大小屏幕时候，视频不显示问题
            openMediaPlayer();
        } else {
            mTextureView.setSurfaceTexture(mSurfaceTexture);//如果不为null，直接设置进去
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {

        return mTextureView == null;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    //播放视频的一系列准备工作
    private void openMediaPlayer() {
        //常亮
        mediaPlayer.setScreenOnWhilePlaying(true);
        //设置监听
        mediaPlayer.setOnPreparedListener(onPrepared());//设置准备完成监听
        mediaPlayer.setOnVideoSizeChangedListener(onVideoSizeChanged());//当视频大小改变的时候
        mediaPlayer.setOnInfoListener(onInfo());//视频加载信息监听回调
        mediaPlayer.setOnCompletionListener(onCompletion());//视频完成播放时候监听
        mediaPlayer.setOnBufferingUpdateListener(onBufferingUpdate());//视频缓存信息监听,显示在底部进度条的第二图层中
        mediaPlayer.setOnSeekCompleteListener(onSeekComplete());//设置拖动完成监听
        //创建Surface对象，让mediaPlayer通过Surface 和mSurfaceTexture与TextureView关联起来
        surface = new Surface(mSurfaceTexture);
        mediaPlayer.setSurface(surface);//设置视频流
        try {
            mediaPlayer.setDataSource(mContext.getApplicationContext(), Uri.parse(url));//设置视频播放地址
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();//异步准备播放视频
        //更改当前UI状态为正在准备中，同时调用控制器中更新Ui的方法
        mCurrentState = Constants.STATE_PREPARING;
        mController.onPlayStateChanged(mCurrentState);//更新控制器为正在准备状态

    }


    //==========================MediaPlayer的监听回调====================================================
    @NonNull//视频加载进度的监听，体现在seekbar的第二进度
    private IMediaPlayer.OnBufferingUpdateListener onBufferingUpdate() {
        return new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

                BufferPercentage = i;//更新加载进度
            }
        };
    }

    @NonNull//视频准备完成之后调用
    private IMediaPlayer.OnPreparedListener onPrepared() {
        return new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                if (historyPosition > 0) {//处理当前的同一个视频时候定位问题
                    iMediaPlayer.seekTo(historyPosition);
                } else {
                    iMediaPlayer.start();//开始播放视频
                }

                mCurrentState = Constants.STATE_PREPARED;//播放准备就绪
                mController.onPlayStateChanged(mCurrentState);//更新控制器为正在准备状态
            }
        };
    }


    @NonNull//让视频不变形
    private IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChanged() {
        return new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
                mTextureView.adaptVideoSize(iMediaPlayer.getVideoWidth(), iMediaPlayer.getVideoHeight());
            }
        };
    }

    @NonNull//播放完成回调
    private IMediaPlayer.OnCompletionListener onCompletion() {
        return new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {

                mCurrentState = Constants.STATE_COMPLETED;//播放完成
                mController.onPlayStateChanged(mCurrentState);//更新控制器为正在准备状态

            }
        };
    }

    @NonNull
    private IMediaPlayer.OnInfoListener onInfo() {
        return new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
                switch (what) {
                    //The player just pushed the very first video frame for rendering,播放第一帧回调
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:

                        mCurrentState = Constants.STATE_PLAYING;//正在播放
                        mController.onPlayStateChanged(mCurrentState);//更新控制器为正在准备状态

                        break;
                }


                return true;
            }
        };
    }

    //播放器拖动完成监听
    @NonNull
    private IMediaPlayer.OnSeekCompleteListener onSeekComplete() {
        return new IMediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                iMediaPlayer.start();
            }
        };
    }


    //===========================================播放器状态判断==============================================================
    //播放器是否处于默认状态，第一次播放状态
    @Override
    public boolean isIdle() {
        return mCurrentState == Constants.STATE_IDLE;
    }

    //播放器正在做播放的准备工作
    @Override
    public boolean isPreparing() {
        return mCurrentState == Constants.STATE_PREPARING;
    }

    //播放器正在播放中
    @Override
    public boolean isPlaying() {
        return mCurrentState == Constants.STATE_PLAYING;
    }

    //播放器被用户点击了暂停
    @Override
    public boolean isPaused() {
        return mCurrentState == Constants.STATE_PAUSED;
    }

    //播放器播放视频完成
    @Override
    public boolean isCompleted() {
        return mCurrentState == Constants.STATE_COMPLETED;
    }


    //================================播放器播放显示模式================================================
    @Override
    public boolean isFullScreen() {
        return mCurrentMode == Constants.MODE_FULL_SCREEN;
    }

    @Override
    public boolean isTinyWindow() {
        return mCurrentMode == Constants.MODE_TINY_WINDOW;
    }

    @Override
    public boolean isNormal() {
        return mCurrentMode == Constants.MODE_NORMAL;
    }


    //===================================播放器声音相关的=========================================================
    @Override
    public int getVolume() {
        return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public int getMaxVolume() {
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void setVolume(int newVolume) {
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
    }

    @Override
    public void setPlayerActivityIsDestroy(boolean b) {
        this.ActivityIsDestroy = b;
    }


    //=====================================播放器控给控制器提供的逻辑方法=======================================


    //用户暂停的方法
    @Override
    public void Pause() {
        mediaPlayer.pause();
        mCurrentState = Constants.STATE_PAUSED;
        mController.onPlayStateChanged(mCurrentState);//更新控制器为正在准备状态
    }

    //用户由于暂停，从新点击播放的时候调用
    @Override
    public void restart() {
        mediaPlayer.start();
        mCurrentState = Constants.STATE_PLAYING;
        mController.onPlayStateChanged(mCurrentState);//更新控制器为正在准备状态
    }

    //=============================释放掉播放器状态================================================
    @Override
    public void release() {
        // 如果是全屏，就退出全屏
        if (isFullScreen()) {
            exitFullScreen();
        }
        //如果是小屏幕就退出小屏幕
        if (isTinyWindow()) {
            exitTinyWindow();
        }
        //把控制器Ui更新到默认模式，取消所有定时器,一定要先取消然后再去释放mediaPlayer否则可能出现空指针异常
        mController.reset();
        // 释放播放器
        releasePlayer();

    }

    private void releasePlayer() {
        //释放掉surface数据通道
        if (surface != null) {
            surface.release();
            surface = null;
        }
        //释放掉mSurfaceTexture数据通道
        if (mSurfaceTexture != null) {
            mSurfaceTexture.release();
            mSurfaceTexture = null;
        }
        //释放掉mediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        //把播放器状态也恢复到默认状态
        mCurrentState = Constants.STATE_IDLE;
        mCurrentMode = Constants.MODE_NORMAL;
        //移除掉mTextureView
        mContainer.removeView(mTextureView);
        mTextureView = null;
        Runtime.getRuntime().gc();
    }

//==============================和底部进度有关的=====================================

    //返回视频的总长度
    @Override
    public long getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    //返回视频播放的当前进度
    @Override
    public long getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }

        return 0;


    }

    //视频移动到指定位置进行播放
    @Override
    public void seekTo(long touchProgress) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(touchProgress);
        }

    }

    //获取视频缓存进度
    @Override
    public int getBufferPercentage() {
        return BufferPercentage;
    }


    //==============================================视频进入悬浮窗退出悬浮窗模式=========================================================
    @Override
    public void enterFloatWindow() {
        permission();
    }

    /**
     * 需要去根据手机系统版本去申请权限，这里分6.0之后和6.0之前，6.0之前适配慢慢来
     */
    public void permission() {
        if (Build.VERSION.SDK_INT >= 23) {//如果手机版本大于6.0

            if (Utils.hasPermission(mContext)) {//如果已经授予这个权限

                createTinyWindow();

            } else {//没有授予这个权限，就去申请这个权限

                Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                mContext.startActivity(intent);

            }

        } else {
            createTinyWindow();
        }
    }

    /**
     * 显示小窗口
     */
    private void createTinyWindow() {
        //改变模式，更新Ui
        mCurrentMode = Constants.MODE_TINY_WINDOW;
        mController.onPlayModeChanged(mCurrentMode);

        FloatWindow
                .getInstance(mContext.getApplicationContext())
                .setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)  // FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
                .setFormat(PixelFormat.RGBA_8888)
                .addView(mTextureView)
                .show();


        TinyWindowListener();

    }

    /**
     * 设置小窗口点击事件的监听
     */
    private void TinyWindowListener() {
        FloatWindow
                .getInstance(mContext.getApplicationContext())
                .setOnClickListener(new FloatWindow.OnClickListener() {
                    @Override
                    public void onTinyClick() {

                        FloatWindow.getInstance(mContext.getApplicationContext()).dismass();

                        if (ActivityIsDestroy) {//表示页面已经关闭，小屏幕也点了关闭，播放器你需要释放掉
                            XVideoViewManager.getInstance().releaseXVideoPlayer();
                        } else {
                            addTextureView();
                            enterFullScreen(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        }

                    }

                    @Override
                    public void onCloseClick() {

                        if (ActivityIsDestroy) {//表示页面已经关闭，小屏幕也点了关闭，播放器你需要释放掉
                            XVideoViewManager.getInstance().releaseXVideoPlayer();
                        } else {
                            exitTinyWindow();
                        }
                    }
                });

    }


    //退出小屏幕，回到默认模式
    public void exitTinyWindow() {
        //改变模式，更新Ui
        mCurrentMode = Constants.MODE_NORMAL;
        mController.onPlayModeChanged(mCurrentMode);
        //移除窗口模式
        FloatWindow.getInstance(mContext.getApplicationContext()).dismass();
        addTextureView();
    }
//====================================进入全屏退出全屏============================================================

    /**
     * 进入全屏
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void enterFullScreen(int ORIENTATION) {
        //隐藏状态栏
        Utils.scanForActivity(mContext)
                .getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏ActionBar
        ActionBar ab = Utils.getAppCompActivity(mContext).getSupportActionBar();
        if (ab != null) {
            ab.setShowHideAnimationEnabled(false);//取消隐藏动画
            ab.hide();
        }

        //设置屏幕为横屏显示
        Utils.scanForActivity(mContext).setRequestedOrientation(ORIENTATION);
        //  每个Activity里面都有一个android.R.content，它是一个FrameLayout，
        // 里面包含了我们setContentView的所有控件。既然它是一个FrameLayout，我们就可以将它作为全屏的目标视图。
        //获取目标视图
        ViewGroup contentView = Utils.scanForActivity(mContext).findViewById(android.R.id.content);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //移除mContainer 已经有的父类控件
        this.removeView(mContainer);
        //把当前播放器添加到目标视图中去
        contentView.addView(mContainer, params);
        //改变模式，更新Ui
        mCurrentMode = Constants.MODE_FULL_SCREEN;
        mController.onPlayModeChanged(mCurrentMode);


    }

    /**
     * 退出全屏
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void exitFullScreen() {
        //显示状态栏
        Utils.scanForActivity(mContext)
                .getWindow()
                .clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //显示ActionBar
        ActionBar ab = Utils.getAppCompActivity(mContext).getSupportActionBar();
        if (ab != null) {
            ab.setShowHideAnimationEnabled(false);//取消隐藏动画
            ab.show();
        }
        //设置屏幕为竖屏显示
        Utils.scanForActivity(mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //获取目标视图
        ViewGroup contentView = Utils.scanForActivity(mContext).findViewById(android.R.id.content);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //移除跟布局中已有的mContainer
        contentView.removeView(mContainer);
        //把当前播放器添加到自定义控件中
        addView(mContainer, params);
        //改变模式，更新Ui
        mCurrentMode = Constants.MODE_NORMAL;
        mController.onPlayModeChanged(mCurrentMode);

    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public void setUrl(String url) {
        //这里如果之前的播放器不是null，直接释放掉播放器
        XVideoViewManager.getInstance().releaseXVideoPlayer();
        this.url = url;
    }

    //================================其他========================================

    /**
     * 把容器返回去，这里需要处理小屏幕时候动态添加提示文字
     *
     * @return
     */
    @Override
    public FrameLayout getContainer() {

        return mContainer;
    }


    @Override
    public void setHistoryPosition(long historyPosition) {
        this.historyPosition = historyPosition;
    }

    @Override
    public boolean isLock() {
        return mController.isLock;
    }

    @Override
    public Context getContexts() {
        return mController.getmContext();
    }


}
