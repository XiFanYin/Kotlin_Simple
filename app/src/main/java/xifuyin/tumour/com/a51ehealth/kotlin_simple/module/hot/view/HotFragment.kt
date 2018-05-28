package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_find_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.ClassifyFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.FocusFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.utils.TableLayoutUtils

/**
 * Created by Administrator on 2018/5/21.
 */
class HotFragment : BaseFragment() {

    //静态方法
    companion object {
        fun getInstance(): HotFragment {
            val fragment = HotFragment()
            return fragment
        }
    }


    override fun getLayoutId(): Int = R.layout.fragment_hot_layout

    override fun initListener() {

    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        mImmersionBar?.titleBar(toolbar)?.init()

    }

    override fun getSerivceData() {
        val tabList = ArrayList<String>()
        var fragments = ArrayList<Fragment>()
        fragments.add(WeekRankFragment.getInstance())
        fragments.add(MonthRankFragment.getInstance())
        fragments.add(TotalRankFragment.getInstance())
        tabList.add("周排行")
        tabList.add("月排行")
        tabList.add("总排行")
        var adapter = MyAdapter(fragments, tabList, childFragmentManager)
        mViewPager.adapter = adapter
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.post({ TableLayoutUtils.setIndicator(mTabLayout, 60, 60) })
    }


    class MyAdapter(var fragments: ArrayList<Fragment>, var titles: ArrayList<String>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = fragments.get(position)
        override fun getCount(): Int = fragments.size
        override fun getPageTitle(position: Int): CharSequence = titles[position]
    }
}