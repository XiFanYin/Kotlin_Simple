package xifuyin.tumour.com.a51ehealth.xvideoview;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/4/17.
 * 这个类主要是后期处理手势触摸的基础类,同时还有规范方法名字的作用
 * <p>
 * <p>
 */

public abstract class BaseController extends FrameLayout implements View.OnTouchListener {

    public Context mContext;//上下文对象
    public IXVideoView xVideoView;//播放器对象
    private Timer mUpdateProgressTimer;
    private TimerTask mUpdateProgressTimerTask;
    private CountDownTimer mDismissTopBottomCountDownTimer;
    public boolean topBottomVisible = false;//顶部和底部默认不显示
    public boolean LockVisible = true;//锁是否显示和隐藏标记
    public String url;
    private CountDownTimer mDismissLockTimer;
    //屏幕锁是否已经上锁
    public boolean isLock = false;
    private float mDownX;
    private float mDownY;
    private boolean mNeedChangePosition;
    private boolean mNeedChangeVolume;
    private boolean mNeedChangeBrightness;
    private static final int THRESHOLD = 80;//临界值
    private long mGestureDownPosition;//手指按下时候播放的位置
    private float mGestureDownBrightness;//手指按下时候的亮度
    private int mGestureDownVolume;
    private long mNewPosition;

    public BaseController(@NonNull Context context) {
        super(context);
        this.mContext = context;
        this.setOnTouchListener(this);//注册当前控制器的触摸事件
    }


    //================================和声音播放位置亮度有关的Ui======================================================

    /**
     * @param v
     * @param event
     * @return 当返回值为true时当前点击事件被onTouch消耗掉，子类的onClick将不会执行，
     * 否则当前点击事件没有被onTouch消耗掉，子类的onClick将会执行
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //如果不是全屏，让手势失效
        if (!xVideoView.isFullScreen()) {
            return false;
        }
        //如果是锁住控制器，让手势失效
        if (isLock) {
            return false;
        }

        //获取所在组件原点的x坐标
        float x = event.getX();
        float y = event.getY();
        //解析用户手势，
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN://当按下的时候

                mDownX = x;//获取按下位置的坐标位置记录一下
                mDownY = y;
                mNeedChangePosition = false;//设置声音亮度和进度都不需要改变
                mNeedChangeVolume = false;
                mNeedChangeBrightness = false;
                break;

            case MotionEvent.ACTION_MOVE://当移动的时候
                float deltaX = x - mDownX;//计算一下用户手指滑动的距离
                float deltaY = y - mDownY;
                float absDeltaX = Math.abs(deltaX);//把滑动位置转换成绝对值
                float absDeltaY = Math.abs(deltaY);
                //断定用户手势是准备做什么的时候
                if (!mNeedChangePosition && !mNeedChangeVolume && !mNeedChangeBrightness) {
                    // 只有在播放、暂停、缓冲的时候能够拖动改变位置、亮度和声音
                    if (absDeltaX >= THRESHOLD) {//如果X方向超过了临界值
                        cancelUpdateProgressTimer();//取消更新进度计时器
                        mNeedChangePosition = true;//把用户判定为需要手势改变进度
                        mGestureDownPosition = xVideoView.getCurrentPosition();//同事获取当前播放器的播放位置记录一下
                    } else if (absDeltaY >= THRESHOLD) {//如果Y方向先超过了临界值
                        if (mDownX < getWidth() * 0.5f) {//如果用户是滑动的左半边屏幕，判定用户想改变屏幕亮度，否则认为想改变声音
                            // 左侧改变亮度
                            mNeedChangeBrightness = true;
                            mGestureDownBrightness = Utils.scanForActivity(mContext).getWindow().getAttributes().screenBrightness;//获取当前屏幕亮度记录一下
                        } else {
                            // 右侧改变声音
                            mNeedChangeVolume = true;
                            mGestureDownVolume = xVideoView.getVolume();//获取用户当前的声音值
                        }
                    }
                }
                //如果需要改变亮度
                if (mNeedChangeBrightness) {
                    deltaY = -deltaY;//转换成反方向
                    float deltaBrightness = deltaY * 4 / getHeight();
                    float newBrightness = mGestureDownBrightness + deltaBrightness;
                    newBrightness = Math.max(0, Math.min(newBrightness, 1));
                    float newBrightnessPercentage = newBrightness;
                    //设置新的亮度
                    WindowManager.LayoutParams params = Utils.scanForActivity(mContext).getWindow().getAttributes();
                    params.screenBrightness = newBrightnessPercentage;
                    Utils.scanForActivity(mContext).getWindow().setAttributes(params);

                    int newBrightnessProgress = (int) (100f * newBrightnessPercentage);
                    showChangeBrightness(newBrightnessProgress);//调用改变亮度的Ui
                }

                //如果需要改变播放进度
                if (mNeedChangePosition) {
                    long duration = xVideoView.getDuration();//获取当前视频的总时长
                    long toPosition = (long) (mGestureDownPosition + duration * deltaX / getWidth());//计算一下需要滑动到的位置
                    mNewPosition = Math.max(0, Math.min(duration, toPosition));//做一些容错处理
                    int newPositionProgress = (int) (100f * mNewPosition / duration);//换算成移动到进度，取值0-100
                    showChangePosition(duration, newPositionProgress, deltaX > 0);//调用改变进度的UI和播放器逻辑
                }

                if (mNeedChangeVolume) {//如果需要改变声音
                    deltaY = -deltaY;
                    int maxVolume = xVideoView.getMaxVolume();
                    int deltaVolume = (int) (maxVolume * deltaY * 4 / getHeight());
                    int newVolume = mGestureDownVolume + deltaVolume;
                    newVolume = Math.max(0, Math.min(maxVolume, newVolume));
                    xVideoView.setVolume(newVolume);//设置声音的变化
                    int newVolumeProgress = (int) (100f * newVolume / maxVolume);
                    showChangeVolume(newVolumeProgress);//调用改变声音的Ui
                }
                break;


            case MotionEvent.ACTION_UP://用户抬起手指或者是
            case MotionEvent.ACTION_CANCEL://用户移除到屏幕外边的时候

                if (mNeedChangePosition) {//如果是改变播放进度
                    xVideoView.seekTo(mNewPosition);
                    hideChangePosition();//抬起时候，去隐藏Ui
                    startUpdateProgressTimer();//从新去设置定期器
                    return true;
                }
                if (mNeedChangeBrightness) {
                    hideChangeBrightness();//抬起时候，去隐藏Ui
                    return true;
                }
                if (mNeedChangeVolume) {
                    hideChangeVolume();//抬起时候，去隐藏Ui
                    return true;
                }
                break;
        }


        return false;
    }

    //改变亮度显示
    protected abstract void showChangeBrightness(int newBrightnessProgress);

    //改变亮度隐藏
    protected abstract void hideChangeBrightness();

    //改变位置
    protected abstract void showChangePosition(long duration, int newPositionProgress, boolean schedule);

    //改变位置隐藏
    protected abstract void hideChangePosition();


    //改变声音显示
    protected abstract void showChangeVolume(int newVolumeProgress);

    //改变声音隐藏
    protected abstract void hideChangeVolume();

    /**
     * 对外提供设置播放器类对象的方法，让本类持有播放器类的对象，好调用播放器类的方法
     */
    public void setXVideoView(IXVideoView xVideoView) {
        this.xVideoView = xVideoView;

    }

    //根据播放器的状态去更新控制器Ui的显示
    public abstract void onPlayStateChanged(int state);


    //根据播放器的显示模式去更新控制器Ui的显示
    public abstract void onPlayModeChanged(int Mode);


    /**
     * 重置控制器，将控制器恢复到初始状态。在列表时候
     */
    protected abstract void reset();

//===============================================和进度有关的逻辑======================================================

    /**
     * 开启更新进度的计时器。
     */
    protected void startUpdateProgressTimer() {
        if (mUpdateProgressTimer == null) {
            mUpdateProgressTimer = new Timer();//创建Timer对象
        }
        if (mUpdateProgressTimerTask == null) {
            mUpdateProgressTimerTask = new TimerTask() {//创建TimerTask对象
                @Override
                public void run() {
                    BaseController.this.post(new Runnable() {
                        @Override
                        public void run() {
                            updateProgress();//这里每隔一秒调用一次更新进度的方法，
                        }
                    });
                }
            };
        }
        mUpdateProgressTimer.schedule(mUpdateProgressTimerTask, 0, 1000);//把任务添加到定时器中，每隔一秒执行一次Runnable中代码
    }

    /**
     * 取消更新进度的计时器。
     */
    protected void cancelUpdateProgressTimer() {
        if (mUpdateProgressTimer != null) {
            mUpdateProgressTimer.cancel();
            mUpdateProgressTimer = null;
        }
        if (mUpdateProgressTimerTask != null) {
            mUpdateProgressTimerTask.cancel();
            mUpdateProgressTimerTask = null;
        }
    }

    /**
     * 这里更新进度的抽象方法，之所以抽象1，父类中可以调用，2，规范方法名字
     */
    protected abstract void updateProgress();


//============================================和控制器的显示隐藏有关的定时器===============================================

    /**
     * 开启top、bottom自动消失的timer
     */
    protected void startDismissTopBottomTimer() {
        cancelDismissTopBottomTimer();
        if (mDismissTopBottomCountDownTimer == null) {
            mDismissTopBottomCountDownTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    setTopBottomVisible(topBottomVisible);//设置顶部和底部显示和隐藏
                    setLockImageViesible(topBottomVisible);//设置锁的显示和隐藏
                    if (xVideoView.isPlaying()) {
                        setCenterImageViesible(topBottomVisible);//设置中间播放按钮是否显示和隐藏
                    }
                    topBottomVisible = !topBottomVisible;
                }
            };
        }
        mDismissTopBottomCountDownTimer.start();
    }

    /**
     * 取消top、bottom自动消失的timer
     */
    protected void cancelDismissTopBottomTimer() {
        if (mDismissTopBottomCountDownTimer != null) {
            mDismissTopBottomCountDownTimer.cancel();
        }
    }

    /**
     * 顶部和底部控制器显示和隐藏抽象方法
     *
     * @param visible
     */
    abstract void setTopBottomVisible(boolean visible);


    /**
     * 中间开始和暂停显示和隐藏
     */
    abstract void setCenterImageViesible(boolean visible);


    //==============================================和锁显示隐藏有关的定时器=======================================
    ;

    /**
     * 开启锁自动消失的timer
     */
    protected void startDismissLockTimer() {
        cancelDismissLockTimer();
        if (mDismissLockTimer == null) {
            mDismissLockTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    setLockImageViesible(LockVisible);
                    LockVisible = !LockVisible;
                }
            };
        }
        mDismissLockTimer.start();
    }

    /**
     * 取消锁自动消失的timer
     */
    protected void cancelDismissLockTimer() {
        if (mDismissLockTimer != null) {
            mDismissLockTimer.cancel();
        }
    }

    /**
     * 锁屏按钮显示和隐藏
     */
    abstract void setLockImageViesible(boolean visible);


    public Context getmContext() {

        return mContext;
    }

    ;

    public void setmContextToNull() {

        mContext = null;
    }
}
