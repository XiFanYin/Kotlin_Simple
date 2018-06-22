package xifuyin.tumour.com.a51ehealth.kotlin_simple.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.base_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.dialog.LoadingDialog
import xifuyin.tumour.com.a51ehealth.kotlin_simple.utils.NetWorkUtils

/**
 * Created by Administrator on 2018/5/21.
 * 实现BaseView，规定统一的方法名字
 * 这里泛型到父类是为了解绑View,当Fragment被销毁的时候调用p中的方法-也叫通知p层，页面关闭了，你要释放view了
 *
 */
abstract class BaseMvpFragment<P : BasePresenter> : BaseFragment(), BaseView {

    lateinit var dialog: LoadingDialog
    //使用委托属性，每个子类都会去创建一个常量
    val mPersenter: P by lazy { initPersenter() }


    protected abstract fun initPersenter(): P


    override fun onDestroyView() {
        //在presenter中解绑释放view
        mPersenter.detach()
        isViewInitiated = false
        isDataRequested = false
        super.onDestroyView()
    }

    //=============================上边是和mvp有关的逻辑========================================


    override fun getLayoutId2(): Int {
        return R.layout.base_layout
    }

    override fun initListener2() {
        //添加传递过来的布局到跟布局
        LayoutInflater.from(activity).inflate(getLayoutId(), container_layout, true)
        //设置错误文字的点击事件
        error_text.setOnClickListener({ view -> onRetry() })
        initListener()
    }

    //显示错误时候，用户的点击事件
    abstract fun onRetry()


    override fun showError() {
        //如果是没有网引起的错误
        if (!NetWorkUtils.isNetworkReachable()) {
            error_image.setImageResource(R.drawable.ic_no_network)
            error_text.setText("请打开网络点击重试")
        } else {
            error_image.setImageResource(R.drawable.ic_error)
            error_text.setText("连接不上服务器，请重启app重试")
        }
        error_layout.visibility = View.VISIBLE
        container_layout.visibility = View.GONE
    }

    override fun dissmassErrorView() {
        error_layout.visibility = View.GONE
        container_layout.visibility = View.VISIBLE
    }


    open override fun dissmassLoading() {
        dialog.dismiss()

    }

    open override fun showLoading() {

        activity?.let {
            dialog = LoadingDialog(activity as Context)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        }

    }


}