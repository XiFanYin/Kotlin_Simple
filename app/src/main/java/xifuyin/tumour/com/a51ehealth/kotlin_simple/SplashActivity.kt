package xifuyin.tumour.com.a51ehealth.kotlin_simple

import android.Manifest
import android.content.Intent
import android.graphics.Typeface
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_splash.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.app.App
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseActivity

/**
 * Created by Administrator on 2018/5/21.
 */
class SplashActivity : BaseActivity() {
    //声明成员属性
    private var alphaAnimation: AlphaAnimation
    var textTypeface: Typeface
    var descTypeFace: Typeface

    //构造方法给成员变量赋值
    init {
        textTypeface = Typeface.createFromAsset(App.application.assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(App.application.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
    }
    //重写设置布局的方法
    override fun getLayout(): Int = R.layout.activity_splash

    //设置监听的方法
    override fun initListener() {
        //设置字体显示样式
        tv_app_name.typeface = textTypeface
        author.typeface = textTypeface
        tv_splash_desc.typeface = descTypeFace

        //渐变展示启动屏
        alphaAnimation.duration = 2000
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                jumpMainActivity()//动画结束回调
            }

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationStart(animation: Animation) {}

        })

        //请求权限
        RxPermissions(act)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe({ granted ->
                    if (granted) {
                        //让根布局去开始这个动画
                        layout_splash.startAnimation(alphaAnimation)
                    } else {
                        //让根布局去开始这个动画
                        layout_splash.startAnimation(alphaAnimation)
                    }
                })

    }

    //跳转到主界面
    private fun jumpMainActivity() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}

