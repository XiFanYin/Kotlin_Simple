package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseMvpFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.model.HomeBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.persenter.HomePersenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.persenter.contact.HomeContact

/**
 * Created by Administrator on 2018/5/21.
 */
class HomeFragment : BaseMvpFragment<HomeContact.Persenter>(), HomeContact.View {


    lateinit var adapter: HomeAdapter
    lateinit var banner: Banner

    //静态方法
    companion object {
        fun getInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }

    //对父类提供Persenter对象
    override fun initPersenter(): HomeContact.Persenter = HomePersenter(this)

    //设置布局
    override fun getLayoutId(): Int = R.layout.fragment_layout

    //设置监听用
    override fun initListener() {
        //设置是垂直布局
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = HomeAdapter(this!!.activity!!, R.layout.item_home_content, null)
        //获得头部布局
        val top = layoutInflater.inflate(R.layout.item_home_top, mRecyclerView.parent as ViewGroup, false)
        banner = top.findViewById<Banner>(R.id.banner)
        //添加头部
        adapter.addHeaderView(top)
        mRecyclerView.adapter = adapter
        var flag1 = true
        var flag2 = true
        //滚动监听
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = mRecyclerView.getLayoutManager() as LinearLayoutManager
                val currentVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    if (flag2) {
                        flag1 = true
                        toolbar.visibility = View.GONE
                        mImmersionBar?.statusBarDarkFont(false)?.init()
                        flag2 = false
                    }
                } else {
                    if (flag1) {
                        flag2 = true
                        mImmersionBar?.statusBarDarkFont(true)?.init()
                        toolbar.visibility = View.VISIBLE
                        flag1 = false
                    }
                }
            }
        })
        //加载更多
        adapter.setOnLoadMoreListener({
            mPersenter.requestNextHomeData()
        }, mRecyclerView)

    }


    //状态栏用
    override fun initImmersionBar() {
        super.initImmersionBar()
        mImmersionBar?.titleBar(toolbar)?.init()

    }

    //获取数据用
    override fun getSerivceData() {
        mPersenter.requestHomeData(1)
    }


    //得到数据后回调
    override fun getData(homeBean: HomeBean) {
        banner.setImageLoader(GlideImageLoader())
        var imageUrl = ArrayList<String>()
        var titles = ArrayList<String>()
        for (lists in homeBean.issueList[0].itemList.subList(0, homeBean.issueList[0].count)) {
            titles.add(lists.data.title)
            imageUrl.add(lists.data.cover.detail)
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        banner.setImages(imageUrl)
        banner.setBannerTitles(titles)
        banner.start()
        adapter.setNewData(homeBean.issueList[0].itemList.subList(homeBean.issueList[0].count, homeBean.issueList[0].itemList.size))

    }

    //得到下页数据回调
    override fun getNextData(homeBean: HomeBean) {
        adapter.addData(homeBean.issueList[0].itemList)
        adapter.loadMoreComplete()
    }

    //当显示错误布局后的点击事件回调
    override fun onRetry() {
        mPersenter.requestHomeData(1)
    }
}