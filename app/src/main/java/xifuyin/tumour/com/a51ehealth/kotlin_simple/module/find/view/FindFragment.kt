package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_find_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseFragment


/**
 * Created by Administrator on 2018/5/21.
 */

class FindFragment : BaseFragment() {

    //伴生对象
    companion object {
        fun getInstance(): FindFragment {
            val fragment = FindFragment()

            return fragment
        }
    }


    override fun getLayoutId() = R.layout.fragment_find_layout

    override fun initListener() {

    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        mImmersionBar?.titleBar(toolbar)?.init()
    }


    override fun getSerivceData() {
        //创建数据
        val tabList = arrayListOf("关注", "分类")
        val fragments = arrayListOf<Fragment>(FocusFragment.getInstance(), ClassifyFragment.getInstance())
        //创建adapter
        val adapter = MyAdapter(fragments, tabList, childFragmentManager)
        //设置adapter
        mViewPager.adapter = adapter
        //关联tablelayout
        mTabLayout.setupWithViewPager(mViewPager)
        //设置tablelayout的下划线长度
        mTabLayout.post({ TableLayoutUtils.setIndicator(mTabLayout, 60, 60) })
    }


    class MyAdapter(var fragments: ArrayList<Fragment>, var titles: ArrayList<String>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = fragments.get(position)
        override fun getCount(): Int = fragments.size
        override fun getPageTitle(position: Int): CharSequence = titles[position]
    }
}