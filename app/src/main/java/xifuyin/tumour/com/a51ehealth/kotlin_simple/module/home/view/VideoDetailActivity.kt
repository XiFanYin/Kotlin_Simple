package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMVideo
import kotlinx.android.synthetic.main.activity_vidio_detail_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseActivity
import xifuyin.tumour.com.a51ehealth.xvideoview.QQBrowserController
import xifuyin.tumour.com.a51ehealth.xvideoview.XVideoViewManager
import com.umeng.socialize.shareboard.ShareBoardConfig


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
        var image_url = intent.getStringExtra("image_url")
        var description = intent.getStringExtra("description")

        val controller = QQBrowserController(this)//初始化了一个布局，设置了点击事件和拖动事件
        //分享逻辑
        controller.share.setOnClickListener {
            //视频播放地址
            val video = UMVideo(playurl)
            //视频的标题
            video.title = playtitle
            //视频的缩略图
            val image = UMImage(this, image_url)
            video.setThumb(image)
            //视频的描述
            video.description = description
            //新建ShareBoardConfig
            val config = ShareBoardConfig()
            config.setIndicatorVisibility(false)
            config.setTitleVisibility(false)
            config.setShareboardBackgroundColor(Color.parseColor("#00000000"))
            ShareAction(this)
                    .withText("hello")
                    .withMedia(video)
                    .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                    .open(config)
        }

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

    override fun onPause() {
        super.onPause()
        XVideoViewManager.getInstance().onPause()
    }


    override fun onDestroy() {
        super.onDestroy()
        XVideoViewManager.getInstance().onDestroy()
    }

    //需要在使用QQ分享或者授权的Activity中添加：
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

}