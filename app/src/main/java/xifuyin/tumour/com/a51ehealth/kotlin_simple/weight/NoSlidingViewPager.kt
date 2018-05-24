package xifuyin.weight

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Administrator on 2018/5/21.
 */
class NoSlidingViewPaper : ViewPager {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    /*
     * 表示把滑动事件传递给下一个view
     */
    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return false
    }

    /*
     * 可以啥都不做
     */
    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return false
    }
}