package xifuyin.tumour.com.a51ehealth.xvideoview;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/4/17.
 */

public class QQBrowserController extends BaseController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    //控件对象
    public ImageView cover;
    public ImageView back;
    public TextView title;
    public ImageView share;
    public ImageView tiny_window;
    public ImageView menu;
    public LinearLayout top;
    public ImageView lock;
    public ImageView restart_or_pause;
    public TextView position;
    public TextView position_full;
    public TextView line;
    public SeekBar seek;
    public TextView duration;
    public ImageView full_screen;
    public LinearLayout bottom;
    public ImageView center_start;
    private ProgressBar progress_bar;
    private TextView hintText;
    private LinearLayout change_position;
    public ImageView fast_image;
    public TextView fast_newPositionProgress;
    public TextView fast_duration;
    public ProgressBar change_brightness_progress;
    public LinearLayout change_brightness;
    public ProgressBar change_volume_progress;
    public LinearLayout change_volume;

    public QQBrowserController(@NonNull Context context) {
        super(context);
        init();
    }


    //打入布局，布局是仿照QQ浏览器布局书写的
    private void init() {
        View qq_controller = LayoutInflater.from(mContext).inflate(R.layout.qq_controller, this, true);
        this.cover = qq_controller.findViewById(R.id.cover);
        this.back = qq_controller.findViewById(R.id.back);
        this.title = qq_controller.findViewById(R.id.title);
        this.share = qq_controller.findViewById(R.id.share);
        this.tiny_window = qq_controller.findViewById(R.id.tiny_window);
        this.menu = qq_controller.findViewById(R.id.menu);
        this.top = qq_controller.findViewById(R.id.top);
        this.lock = qq_controller.findViewById(R.id.lock);
        this.restart_or_pause = qq_controller.findViewById(R.id.restart_or_pause);
        this.position = qq_controller.findViewById(R.id.position);
        this.seek = qq_controller.findViewById(R.id.seek);
        this.duration = qq_controller.findViewById(R.id.duration);
        this.full_screen = qq_controller.findViewById(R.id.full_screen);
        this.bottom = qq_controller.findViewById(R.id.bottom);
        this.center_start = qq_controller.findViewById(R.id.center_start);
        this.progress_bar = qq_controller.findViewById(R.id.progress_bar);
        this.position_full = qq_controller.findViewById(R.id.position_full);
        this.line = qq_controller.findViewById(R.id.line);
        this.change_position = findViewById(R.id.change_position);
        this.fast_image = findViewById(R.id.fast_image);
        this.fast_newPositionProgress = findViewById(R.id.fast_newPositionProgress);
        this.fast_duration = findViewById(R.id.fast_duration);
        this.change_brightness_progress = findViewById(R.id.change_brightness_progress);
        this.change_brightness = findViewById(R.id.change_brightness);
        this.change_volume_progress = findViewById(R.id.change_volume_progress);
        this.change_volume = findViewById(R.id.change_volume);

        //中心按钮的点击事件
        center_start.setOnClickListener(this);
        back.setOnClickListener(this);
        tiny_window.setOnClickListener(this);
        menu.setOnClickListener(this);
        lock.setOnClickListener(this);
        restart_or_pause.setOnClickListener(this);
        full_screen.setOnClickListener(this);

        seek.setOnSeekBarChangeListener(this);

        //整个布局的点击事件
        this.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == center_start) {//播放按钮被点击

            if (xVideoView.isIdle()) { //判断是否是播放未开始
                xVideoView.start();//调用自定义播放器的开始方法
            } else if (xVideoView.isPlaying()) {
                xVideoView.Pause();
            } else if (xVideoView.isPaused() || xVideoView.isCompleted()) {
                xVideoView.restart();
            }

        } else if (v == back) {//返回按钮被点击

            xVideoView.exitFullScreen();

        }  else if (v == tiny_window) {//小屏按钮被点击

            if (xVideoView.isFullScreen()) {
                xVideoView.exitFullScreen();
                xVideoView.enterFloatWindow();

            } else if (xVideoView.isNormal()) {
                xVideoView.enterFloatWindow();
            }

        } else if (v == menu) {//菜单被点击

            Toast.makeText(mContext, "菜单被点击", Toast.LENGTH_SHORT).show();

        } else if (v == lock) {//锁定控制器被点击

            if (isLock) {
                lock.setImageResource(R.drawable.video_unlock);
                topBottomVisible = false;
                setTopBottomVisible(topBottomVisible);
                topBottomVisible = !topBottomVisible;
                startDismissTopBottomTimer();
                cancelDismissLockTimer();
            } else {
                lock.setImageResource(R.drawable.video_lock);
                cancelDismissTopBottomTimer();
                startDismissLockTimer();
                topBottomVisible = true;
                setTopBottomVisible(topBottomVisible);
            }
            isLock = !isLock;

        } else if (v == restart_or_pause) {//开始暂停被点击

            if (xVideoView.isPlaying()) {
                xVideoView.Pause();
            } else if (xVideoView.isPaused() || xVideoView.isCompleted()) {
                xVideoView.restart();
            }

        } else if (v == full_screen) {//全屏被点击

            xVideoView.enterFullScreen(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            if (xVideoView.isPlaying()) {
                topBottomVisible = true;
            } else {
                topBottomVisible = false;
            }
            setTopBottomVisible(topBottomVisible);//设置顶部和底部显示和隐藏
            setCenterImageViesible(topBottomVisible);//设置中心按钮显示和隐藏
            setLockImageViesible(topBottomVisible);//设置锁的显示和隐藏
            topBottomVisible = !topBottomVisible;

        } else if (v == this) {//整个控制器被点击

            if (isLock) {
                startDismissLockTimer();
                setTopBottomVisible(topBottomVisible);//设置顶部和底部显示和隐藏
                setCenterImageViesible(topBottomVisible);//设置中心按钮显示和隐藏
                setLockImageViesible(topBottomVisible && LockVisible);//设置锁的显示和隐藏
                LockVisible = !LockVisible;
            } else {
                cancelDismissLockTimer();
                setTopBottomVisible(topBottomVisible);//设置顶部和底部显示和隐藏
                setCenterImageViesible(topBottomVisible);//设置中心按钮显示和隐藏
                setLockImageViesible(topBottomVisible);//设置锁的显示和隐藏
                topBottomVisible = !topBottomVisible;
            }


        }


    }


    @Override//根据标记的播放状态去更新对应的UI
    public void onPlayStateChanged(int state) {
        switch (state) {

            case Constants.STATE_IDLE://默认状态

                break;
            case Constants.STATE_PREPARING://正在准备播放状态，对应去更新UI
                cover.setVisibility(GONE);
                center_start.setVisibility(GONE);
                progress_bar.setVisibility(VISIBLE);
                break;

            case Constants.STATE_PREPARED://播放器准备就绪，准备播放
                progress_bar.setVisibility(GONE);

                break;

            case Constants.STATE_PLAYING://播放器正在播放

                center_start.setImageResource(R.drawable.video_mid_pause_fullscreen);
                restart_or_pause.setImageResource(R.drawable.video_pause_wide);
                cover.setVisibility(GONE);
                startUpdateProgressTimer();//开启定时器，会调用更新进度
                center_start.setVisibility(GONE);
                break;


            case Constants.STATE_PAUSED://用户点击了暂停按钮
                center_start.setImageResource(R.drawable.video_mid_play_fullscreen);
                center_start.setVisibility(VISIBLE);
                restart_or_pause.setImageResource(R.drawable.video_play_wide);
                cancelUpdateProgressTimer();//取消更新进度的方法，否则视频播放完成，更新进度的定时器还是会走，浪费cpu,这个是父类的方法
                break;


            case Constants.STATE_COMPLETED://播放完成后更新Ui

                reset();
                cover.setVisibility(VISIBLE);

                break;


        }

    }

    //根据播放器的显示模式去更新控制器Ui的显示
    @Override
    public void onPlayModeChanged(int Mode) {
        switch (Mode) {

            case Constants.MODE_NORMAL://播放器在默认模式下
                //获取容器布局,移除添加的提示TextView
                xVideoView.getContainer().removeView(hintText);
                //让控制器显示
                setVisibility(VISIBLE);

                position.setVisibility(VISIBLE);
                full_screen.setVisibility(VISIBLE);
                if (!xVideoView.isPlaying()) {
                    center_start.setVisibility(VISIBLE);
                }
                back.setVisibility(GONE);
                title.setVisibility(INVISIBLE);
                share.setVisibility(GONE);
                menu.setVisibility(GONE);
                lock.setVisibility(GONE);
                restart_or_pause.setVisibility(GONE);
                position_full.setVisibility(GONE);
                line.setVisibility(GONE);

                break;

            case Constants.MODE_TINY_WINDOW://播放器在Tiny模式下
                //获取容器布局,添加的提示TextView
                FrameLayout container2 = xVideoView.getContainer();
                hintText = new TextView(mContext);
                hintText.setText("当前视频正在小窗中播放");
                hintText.setTextSize(14);
                FrameLayout.LayoutParams textParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.gravity = Gravity.CENTER;
                hintText.setTextColor(mContext.getResources().getColor(R.color.colorHint));
                container2.addView(hintText, textParams);
                //让控制器消失
                setVisibility(GONE);

                break;

            case Constants.MODE_FULL_SCREEN://播放器在全屏模式下
                //获取容器布局,移除添加的提示TextView
                xVideoView.getContainer().removeView(hintText);
                //让控制器显示
                setVisibility(VISIBLE);

                back.setVisibility(VISIBLE);
                title.setVisibility(VISIBLE);
                share.setVisibility(VISIBLE);
                menu.setVisibility(VISIBLE);
                lock.setVisibility(VISIBLE);
                restart_or_pause.setVisibility(VISIBLE);
                position_full.setVisibility(VISIBLE);
                line.setVisibility(VISIBLE);
                position.setVisibility(GONE);
                full_screen.setVisibility(GONE);
                center_start.setVisibility(GONE);
                break;

        }

    }


    /**
     * 当视频准备就绪后，设置页面和视频进度有关的UI,因为进度是事实更新的，所以这里使用定时器，
     * 每隔一秒去请求一次数据，因为这个所有子类都有的功能，所以定时器写到了父类
     * <p>
     * 这个方法每个一秒会调用一次，因为是时时更新进度的方法
     */
    protected void updateProgress() {
        //获取播放器解析到的当前进度和整个视频长度，还有缓存进度
        long currentPosition = xVideoView.getCurrentPosition();
        long durationPosition = xVideoView.getDuration() + 1;
        int bufferPercentage = xVideoView.getBufferPercentage();
        //设置数据到控件
        position.setText(Utils.formatTime(currentPosition));
        position_full.setText(Utils.formatTime(currentPosition));
        duration.setText(Utils.formatTime(durationPosition));
        int progress = (int) (100 * currentPosition / durationPosition);//计算进度百分比，转换层int，因为下边接受的就是int类型的值
        seek.setProgress(progress);//设置进度条
        seek.setSecondaryProgress(bufferPercentage);//设置缓存百分比

    }

    /**
     * 顶部和底部控制器显示和隐藏
     *
     * @param visible
     */
    @Override
    protected void setTopBottomVisible(boolean visible) {
        if (xVideoView.isPlaying() || xVideoView.isCompleted() || xVideoView.isPaused()) {
            top.setVisibility(visible ? View.GONE : View.VISIBLE);
            bottom.setVisibility(visible ? View.GONE : View.VISIBLE);
        }
        if (visible) {
            cancelDismissTopBottomTimer();
        } else {
            startDismissTopBottomTimer();
        }

    }

    /**
     * 中间开始和暂停显示和隐藏
     */
    @Override
    protected void setCenterImageViesible(boolean visible) {

        if (xVideoView.isFullScreen()) {

            center_start.setVisibility(GONE);
        } else {

            if (xVideoView.isPlaying() || xVideoView.isCompleted()) {
                center_start.setVisibility(visible ? View.GONE : View.VISIBLE);
            }

        }

    }

    /**
     * 锁屏按钮显示和隐藏
     */
    @Override
    protected void setLockImageViesible(boolean visible) {
        if (xVideoView.isFullScreen()) {
            lock.setVisibility(visible ? View.GONE : View.VISIBLE);
        } else {
            lock.setVisibility(GONE);
        }

    }

    /**
     * 重置控制器，将控制器恢复到初始状态。在列表时候，这里不可能是全屏和小屏时候调用，只可能谁默认情况下才调用
     */
    @Override
    protected void reset() {
        //所有标记回复默认状态
        topBottomVisible = false;
        LockVisible = true;
        isLock = false;

        //取消所有定时器
        cancelUpdateProgressTimer();
        cancelDismissTopBottomTimer();
        cancelDismissLockTimer();

        seek.setProgress(0);//设置mSeek的进度为0
        seek.setSecondaryProgress(0);//设置mSeek的加载进度为0
        top.setVisibility(GONE);
        bottom.setVisibility(GONE);
        cover.setVisibility(VISIBLE);
        center_start.setVisibility(VISIBLE);
        center_start.setImageResource(R.drawable.video_mid_play_fullscreen);

    }


    //==============================================底部进度条拖动的回调=========================================================
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        long touchProgress = (progress * xVideoView.getDuration() / 100);
        xVideoView.seekTo(touchProgress);
    }

    //=========================================和手势有关的声音，亮度，和进度的改变调用对应的方法=====================================================


    @Override
    protected void showChangePosition(long duration, int newPositionProgress, boolean schedule) {
        change_position.setVisibility(VISIBLE);
        fast_image.setImageResource(schedule ? R.drawable.video_fast_forward : R.drawable.video_fast_back);
        long newPosition = (long) (duration * newPositionProgress / 100f);
        fast_newPositionProgress.setText(Utils.formatTime(newPosition));
        fast_duration.setText(Utils.formatTime(duration));
    }

    @Override
    protected void hideChangePosition() {
        change_position.setVisibility(GONE);
    }


    @Override
    protected void showChangeVolume(int newVolumeProgress) {
        change_volume.setVisibility(View.VISIBLE);
        change_volume_progress.setProgress(newVolumeProgress);
    }

    @Override
    protected void hideChangeVolume() {
        change_volume.setVisibility(View.GONE);
    }

    @Override
    protected void showChangeBrightness(int newBrightnessProgress) {
        change_brightness.setVisibility(View.VISIBLE);
        change_brightness_progress.setProgress(newBrightnessProgress);
    }

    @Override
    protected void hideChangeBrightness() {
        change_brightness.setVisibility(View.GONE);
    }


    //============================对外暴漏的方法，设置视频名称的方法==================================
    public void setTitle(String title) {
        this.title.setText(title);
    }


    public ImageView getCover() {

        return cover;
    }




}
