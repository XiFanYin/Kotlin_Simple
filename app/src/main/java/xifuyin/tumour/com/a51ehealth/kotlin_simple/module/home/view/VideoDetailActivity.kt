package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view

import android.content.pm.ActivityInfo
import kotlinx.android.synthetic.main.activity_vidio_detail.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseActivity
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.model.HomeBean
import xifuyin.tumour.com.a51ehealth.xvideoview.QQBrowserController


/**
 * Created by Administrator on 2018/5/24.
 *
 * 视频详情页面
 */
class VideoDetailActivity : BaseActivity() {

    private lateinit var itemData: HomeBean.Issue.Item.Data

    override fun getLayout(): Int = R.layout.activity_vidio_detail


    override fun initListener() {
        itemData = intent.getSerializableExtra("videodata") as HomeBean.Issue.Item.Data

        val controller = QQBrowserController(this)//初始化了一个布局，设置了点击事件和拖动事件
        controller.setTitle(itemData.title)//设置视频名字，全屏的时候显示
        xVideoView.setController(controller)//让自定义播放器持有控制器对象，同时让控制器持有播放器对象，同时把控制器添加到自定义控件中
        xVideoView.setUrl(itemData.playUrl)
        xVideoView.start()
        xVideoView.enterFullScreen(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    }


}