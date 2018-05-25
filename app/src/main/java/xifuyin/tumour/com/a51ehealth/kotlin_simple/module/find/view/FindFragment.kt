package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.find_fragment_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.utils.TableLayoutUtils


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


    override fun getLayoutId(): Int = R.layout.find_fragment_layout

    override fun initListener() {

    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        mImmersionBar?.titleBar(toolbar)?.init()
    }


    override fun getSerivceData() {
        val tabList = ArrayList<String>()
        var fragments = ArrayList<Fragment>()
        fragments.add(FocusFragment.getInstance())
        fragments.add(ClassifyFragment.getInstance())
        tabList.add("关注")
        tabList.add("分类")
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