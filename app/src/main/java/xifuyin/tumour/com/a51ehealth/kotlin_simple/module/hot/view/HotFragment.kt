package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_find_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseMvpFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.model.TabInfoBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.persenter.HotPersenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.persenter.contact.HotContact

/**
 * Created by Administrator on 2018/5/21.
 */
class HotFragment : BaseMvpFragment<HotContact.Persenter>(), HotContact.View {
    //伴生对象
    companion object {
        fun getInstance(): HotFragment {
            val fragment = HotFragment()
            return fragment
        }
    }

    override fun initPersenter()= HotPersenter(this)


    override fun initImmersionBar() {
        super.initImmersionBar()
        mImmersionBar?.titleBar(toolbar)?.init()

    }

    override fun getLayoutId() = R.layout.fragment_hot_layout


    override fun initListener() {

    }

    override fun getSerivceData() {
        mPersenter.getTableData()

    }

    override fun setTableData(data: TabInfoBean) {
        //使用集合操作符，重构数据
        val tabList = ArrayList<String>()
        var fragments = ArrayList<Fragment>()
        data.tabInfo.tabList.mapTo(tabList) { it.name }
        data.tabInfo.tabList.mapTo(fragments) { RankFragment.getInstance(it.apiUrl) }
        //创建设置适配器
        var adapter = MyAdapter(fragments, tabList, childFragmentManager)
        mViewPager.adapter = adapter
        //关联tablelayout
        mTabLayout.setupWithViewPager(mViewPager)
        //设置预加载数量
        mViewPager.offscreenPageLimit = tabList.size - 1
        //设置table的下划线长度
        mTabLayout.post({ TableLayoutUtils.setIndicator(mTabLayout, 60, 60) })


    }


    class MyAdapter(var fragments: ArrayList<Fragment>, var titles: ArrayList<String>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = fragments.get(position)
        override fun getCount(): Int = fragments.size
        override fun getPageTitle(position: Int): CharSequence = titles[position]
    }


    override fun onRetry() {
        mPersenter.getTableData()
    }





}