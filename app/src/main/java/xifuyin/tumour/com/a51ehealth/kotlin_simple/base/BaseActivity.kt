package xifuyin.tumour.com.a51ehealth.kotlin_simple.base

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar

/**
 * Created by Administrator on 2018/5/21.
 *
 * Activity基类
 *
 */
abstract class BaseActivity : AppCompatActivity() {
    //延迟初始化
    lateinit var act: Activity
    lateinit var mImmersionBar: ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act = this
        //设制竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //设置布局
        setContentView(getLayout2())

        //初始化状态栏
        initBar()
        //初始化监听
        initListener2()
        //获取服务器数据
        getSerivceData()
    }


    fun initBar() {
        //沉浸式状态栏
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar.init()
    }

    //============================和布局有关的方法==================================

    fun getLayout2(): Int {
        return getLayout()
    }

    abstract fun getLayout(): Int


    //========================和设置监听有关的方法==============================
    protected fun initListener2() {
        initListener()
    }

    protected abstract fun initListener()

    //========================和获取数据有关的方法==============================

    fun getSerivceData() {}


    override fun onDestroy() {
        super.onDestroy()
        mImmersionBar.destroy()
    }
}