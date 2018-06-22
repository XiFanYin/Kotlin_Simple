package xifuyin.tumour.com.a51ehealth.kotlin_simple


import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseActivity
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.FindFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view.HomeFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.view.HotFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.me.view.MeFragment


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var fragments: ArrayList<android.support.v4.app.Fragment>

    override fun getLayout(): Int = R.layout.activity_main_layout

    override fun initListener() {
        //进制底部位移动画
        BottomNavigationViewUtils.disableShiftMode(bottomView)
        //设置底部点击监听
        bottomView.setOnNavigationItemSelectedListener(this)
        //初始化数据
        fragments = arrayListOf(HomeFragment.getInstance(), FindFragment.getInstance(), HotFragment.getInstance(), MeFragment.getInstance())
        //创建adapter
        var adapter = MyAdapter(fragments, supportFragmentManager)
        //设置adapter
        container.adapter = adapter
        //设置缓存数量,房子每次都去创建一个心得
        container.offscreenPageLimit = fragments.size - 1

    }

    /**
     * 设置底部按钮选择监听
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> container.setCurrentItem(0, false)
            R.id.find -> container.setCurrentItem(1, false)
            R.id.hot -> container.setCurrentItem(2, false)
            R.id.me -> container.setCurrentItem(3, false)
        }
        return true
    }
    //这是嵌套类，而不是内部类，所以需要传递数据过来
    class MyAdapter(var fragments: ArrayList<android.support.v4.app.Fragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): android.support.v4.app.Fragment = fragments.get(position)
        override fun getCount(): Int = fragments.size
    }
}