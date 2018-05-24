package xifuyin.tumour.com.a51ehealth.xvideoview;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/4/19.
 * 悬浮窗管理类
 */

public class FloatWindow {

    private Context applicationContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private static FloatWindow mInstance;
    private FrameLayout newContainer;
    private OnClickListener onClickListener;

    private View TextureView;


    /**
     * 单例模式，生成该类对象
     *
     * @return
     */
    public static FloatWindow getInstance(Context applicationContext) {

        if (mInstance == null) {
            synchronized (FloatWindow.class) {
                mInstance = new FloatWindow(applicationContext);
            }
        }
        return mInstance;
    }

    //构造方法
    private FloatWindow(Context applicationContext) {

        this.applicationContext = applicationContext;
        // 获取系统窗体管理类
        mWindowManager = (WindowManager) applicationContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕的宽和高
        DisplayMetrics dm = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        // 获取描述窗口的管理策略类
        mLayoutParams = new WindowManager.LayoutParams();
        //为了统一远点坐标，我们这里设置为左边，上边
        mLayoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;

        mLayoutParams.width = width / 2;
        mLayoutParams.height = width * 9 / 32;

        // 设置窗体显示类型——TYPE_SYSTEM_ALERT(系统提示)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0+
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

    }


    /**
     * 设置显示标记
     *
     * @param layoutParamsFlags
     * @return
     */
    public FloatWindow setFlags(int layoutParamsFlags) {
        mLayoutParams.flags = layoutParamsFlags;
        return mInstance;
    }

    /**
     * 设置显示模式
     *
     * @param layoutParamsFormat
     * @return
     */

    public FloatWindow setFormat(int layoutParamsFormat) {

        mLayoutParams.format = layoutParamsFormat;

        return mInstance;
    }


    public FloatWindow addView(View TextureView) {
        //获取传入的播放控件
        this.TextureView = TextureView;
        //移除绑定的父类控件
        FrameLayout mContainer = (FrameLayout) TextureView.getParent();
        mContainer.removeView(TextureView);


        newContainer = new FrameLayout(applicationContext);
        newContainer.setBackgroundColor(Color.BLACK);

        //添加右下角标致
        ImageView zoom_hint = new ImageView(applicationContext);
        zoom_hint.setImageResource(R.drawable.video_zoom_hint);
        FrameLayout.LayoutParams zoom_Hint_Params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        zoom_Hint_Params.gravity = Gravity.BOTTOM | Gravity.END;
        newContainer.addView(zoom_hint, zoom_Hint_Params);

        //添加关闭按钮
        ImageView close = new ImageView(applicationContext);
        close.setImageResource(R.drawable.video_small_window_close);
        FrameLayout.LayoutParams close_Params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        close_Params.setMargins(0, 0, 30, 30);
        newContainer.addView(close, close_Params);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    //调用外部传递过来的方法，表示关闭窗口被点击
                    onClickListener.onCloseClick();
                }
            }
        });

        //添加播放器到小屏幕中去
        newContainer.addView(TextureView, 0);


        addOnTouch();

        return mInstance;
    }

    /**
     * 拖动事件，注意原点坐标的变换
     */

    private void addOnTouch() {

        newContainer.setOnTouchListener(new View.OnTouchListener() {
            float downX = 0;
            float downY = 0;
            int oddOffsetX = 0;
            int oddOffsetY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = -event.getX();
                        downY = -event.getY();
                        oddOffsetX = mLayoutParams.x;
                        oddOffsetY = mLayoutParams.y;

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveX = -event.getX();
                        float moveY = -event.getY();
                        mLayoutParams.x += (moveX - downX) / 6;
                        mLayoutParams.y += (moveY - downY) / 6;
                        if (newContainer != null) {
                            mWindowManager.updateViewLayout(newContainer, mLayoutParams);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        int newOffsetX = mLayoutParams.x;
                        int newOffsetY = mLayoutParams.y;
                        if (Math.abs(newOffsetX - oddOffsetX) <= 20 && Math.abs(newOffsetY - oddOffsetY) <= 20) {//如果抬起来的时候位置小于20，认为用户是点击事件
                            if (onClickListener != null) {
                                //调用外部传递过来的方法，表示整个窗口被点击调用
                                onClickListener.onTinyClick();
                            }
                        }
                        break;
                }
                return true;
            }

        });
    }

    /**
     * 显示悬浮窗
     */
    public void show() {

        mWindowManager.addView(newContainer, mLayoutParams);

    }

    /**
     * 移除悬浮窗
     */
    public void dismass() {
        if (newContainer != null) {
            mWindowManager.removeView(newContainer);
            if (TextureView != null) {
                newContainer.removeView(TextureView);
            }

        }
    }


    /**
     * 对外提供设置点击事件的方法
     *
     * @return
     */
    public void setOnClickListener(OnClickListener onClickListener) {

        this.onClickListener = onClickListener;

    }

    /**
     * 内部接口，点击时间的回调
     */

    interface OnClickListener {
        //整个小窗被点击
        void onTinyClick();

        //关闭小窗口按钮被点击
        void onCloseClick();

    }


}
