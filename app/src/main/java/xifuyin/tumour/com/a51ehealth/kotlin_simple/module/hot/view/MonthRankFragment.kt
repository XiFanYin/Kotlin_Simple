package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.view

import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseFragment

/**
 * Created by Administrator on 2018/5/28.
 */
class MonthRankFragment :BaseFragment() {

    //静态方法
    companion object {
        fun getInstance(): MonthRankFragment {
            val fragment = MonthRankFragment()
            return fragment
        }
    }
    override fun getLayoutId(): Int= R.layout.fragment_month_layout

    override fun initListener() {

    }

    override fun getSerivceData() {

    }
}