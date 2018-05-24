package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.annotation.SuppressLint
import kotlinx.android.synthetic.main.fragment_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseFragment

/**
 * Created by Administrator on 2018/5/21.
 */

class FindFragment : BaseFragment() {

    //静态方法
    companion object {
        fun getInstance(): FindFragment {
            val fragment = FindFragment()
            return fragment
        }
    }


    override fun getLayoutId(): Int = R.layout.fragment_layout

    override fun initListener() {

    }
    override fun initImmersionBar() {
        super.initImmersionBar()
        mImmersionBar?.fitsSystemWindows(true)?.init()

    }
    override fun getSerivceData() {

    }
}