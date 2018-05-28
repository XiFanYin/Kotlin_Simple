package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view

import android.content.pm.ActivityInfo
import kotlinx.android.synthetic.main.activity_vidio_detail_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseActivity
import xifuyin.tumour.com.a51ehealth.xvideoview.QQBrowserController
import xifuyin.tumour.com.a51ehealth.xvideoview.XVideoViewManager


/**
 * Created by Administrator on 2018/5/24.
 *
 * 视频详情页面
 */
class VideoDetailActivity : BaseActivity() {


    override fun getLayout(): Int = R.layout.activity_vidio_detail_layout


    override fun initListener() {
        var playurl = intent.getStringExtra("video_url")
        var playtitle = intent.getStringExtra("video_title")

        val controller = QQBrowserController(this)//初始化了一个布局，设置了点击事件和拖动事件
        controller.setTitle(playtitle)//设置视频名字，全屏的时候显示
        xVideoView.setController(controller)//让自定义播放器持有控制器对象，同时让控制器持有播放器对象，同时把控制器添加到自定义控件中
        xVideoView.setUrl(playurl)
        xVideoView.start()
        xVideoView.enterFullScreen(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    }

    override fun onResume() {
        super.onResume()
        XVideoViewManager.getInstance().onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        XVideoViewManager.getInstance().onDestroy()
    }

}