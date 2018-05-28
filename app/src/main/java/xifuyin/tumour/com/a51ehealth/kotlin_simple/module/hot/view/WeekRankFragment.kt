package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.view

import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view.HomeFragment

/**
 * Created by Administrator on 2018/5/28.
 */
class WeekRankFragment : BaseFragment() {

    //静态方法
    companion object {
        fun getInstance(): WeekRankFragment {
            val fragment = WeekRankFragment()
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_week_layout

    override fun initListener() {

    }

    override fun getSerivceData() {

    }
}