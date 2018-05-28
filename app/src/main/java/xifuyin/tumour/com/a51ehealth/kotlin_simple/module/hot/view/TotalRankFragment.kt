package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.view

import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseFragment

/**
 * Created by Administrator on 2018/5/28.
 */
class TotalRankFragment : BaseFragment() {

    //静态方法
    companion object {
        fun getInstance(): TotalRankFragment {
            val fragment = TotalRankFragment()
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_total_layout

    override fun initListener() {

    }

    override fun getSerivceData() {

    }
}