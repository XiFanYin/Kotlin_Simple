package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.me.view

import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseFragment

/**
 * Created by Administrator on 2018/5/21.
 */
class MeFragment : BaseFragment() {

    //静态方法
    companion object {
        fun getInstance(): MeFragment {
            val fragment = MeFragment()
            return fragment
        }
    }


    override fun getLayoutId(): Int = R.layout.fragment_home_layout

    override fun initListener() {

    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        mImmersionBar?.fitsSystemWindows(true)?.init()

    }
    override fun getSerivceData() {

    }
}