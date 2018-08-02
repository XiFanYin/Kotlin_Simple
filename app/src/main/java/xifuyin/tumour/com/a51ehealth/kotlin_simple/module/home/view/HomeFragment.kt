package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseMvpFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.model.HomeBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.persenter.HomePersenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.persenter.contact.HomeContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view.adapter.GlideImageLoader
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view.adapter.HomeAdapter

/**
 * Created by Administrator on 2018/5/21.
 */
class HomeFragment : BaseMvpFragment<HomeContact.Persenter>(), HomeContact.View {

    lateinit var adapter: HomeAdapter
    lateinit var banner: Banner
    var isShowLoading: Boolean = true
    var playurl = ArrayList<String>()
    var imageUrl = ArrayList<String>()
    var titles = ArrayList<String>()
    var description = ArrayList<String>()

    //伴生对象
    companion object {
        fun getInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }

    //对父类提供Persenter对象
    override fun initPersenter() = HomePersenter(this)

    //设置布局
    override fun getLayoutId() = R.layout.fragment_home_layout

    //设置监听用
    override fun initListener() {
        //设置是垂直布局
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = HomeAdapter(R.layout.item_home_content_layout, null)
        //获得头部布局
        val top = layoutInflater.inflate(R.layout.item_home_top_layout, mRecyclerView.parent as ViewGroup, false)
        banner = top.findViewById<Banner>(R.id.banner)
        //添加头部轮播图控件
        adapter.addHeaderView(top)
        //设置adapter
        mRecyclerView.adapter = adapter
        var flag1 = true
        var flag2 = true
        //滚动监听
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //找到第一个显示控件的位置
                val layoutManager = mRecyclerView.getLayoutManager() as LinearLayoutManager
                val currentVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                //如果==0，表示轮播已经显示，需要隐藏头标题
                if (currentVisibleItemPosition == 0) {
                    if (flag2) {
                        flag1 = true//开启下边if的逻辑
                        toolbar.visibility = View.GONE//让控件消失
                        mImmersionBar?.statusBarDarkFont(false)?.init()//让状态栏的字体为白色
                        flag2 = false//让if内的代码只能执行一次的标记
                        banner.startAutoPlay()//开启轮播图滚动
                    }
                } else {//否则需要显示头标题
                    if (flag1) {
                        flag2 = true//开启上边if的逻辑
                        mImmersionBar?.statusBarDarkFont(true)?.init()//让状态栏的字体为黑色
                        toolbar.visibility = View.VISIBLE//让控件显示
                        flag1 = false//让if内的代码只能执行一次的标记
                        banner.stopAutoPlay()//停止轮播图滚动
                    }
                }
            }
        })

        //加载更多
        adapter.setOnLoadMoreListener({
            isShowLoading = false
            mPersenter.requestNextHomeData()
        }, mRecyclerView)

        //设置条目点击事件
        adapter.setOnItemClickListener({ _, _, position ->
            var intent = Intent(activity, VideoDetailActivity::class.java)
            intent.putExtra("video_url", this.adapter.data.get(position).data.playUrl)
            intent.putExtra("video_title", this.adapter.data.get(position).data.title)
            intent.putExtra("image_url", this.adapter.data.get(position).data.cover.detail)
            intent.putExtra("description", this.adapter.data.get(position).data.description)
            startActivity(intent)

        })
        //轮播图的点击事件
        banner.setOnBannerListener({ position ->
            var intent = Intent(activity, VideoDetailActivity::class.java)
            intent.putExtra("video_url", playurl.get(position))
            intent.putExtra("video_title", titles.get(position))
            intent.putExtra("image_url", imageUrl.get(position))
            intent.putExtra("description", description.get(position))
            startActivity(intent)
        })
        //搜索的点击事件
        iv_search.setOnClickListener { view ->
            Toast.makeText(activity, "搜索的点击事件", Toast.LENGTH_SHORT).show()
        }
    }


    //状态栏用
    override fun initImmersionBar() {
        super.initImmersionBar()
        mImmersionBar?.titleBar(toolbar)?.statusBarDarkFont(false)?.init()

    }

    //获取数据调用p层的逻辑
    override fun getSerivceData() {
        mPersenter.requestHomeData(1)
    }


    //得到数据后回调
    override fun getData(homeBean: HomeBean) {
        //设置轮播图适配器
        banner.setImageLoader(GlideImageLoader())
        //截取一定数量的数据做为轮播图
        var bannerData = homeBean.issueList[0].itemList.subList(0, homeBean.issueList[0].count)
        //重构数据
        for (lists in bannerData) {
            titles.add(lists.data.title)
            imageUrl.add(lists.data.cover.detail)
            playurl.add(lists.data.playUrl)
            description.add(lists.data.description)
        }
        //设置轮播图的一些UI
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        banner.setImages(imageUrl)
        banner.setBannerTitles(titles)
        banner.start()
        //把截取后的数据设置为列表
        adapter.setNewData(homeBean.issueList[0].itemList.subList(homeBean.issueList[0].count, homeBean.issueList[0].itemList.size))

    }

    //得到下页数据回调
    override fun getNextData(homeBean: HomeBean) {
        adapter.addData(homeBean.issueList[0].itemList)
        adapter.loadMoreComplete()
    }

    //当显示错误布局后的点击事件回调
    override fun onRetry() {
        isShowLoading = true//控制圈圈是否显示
        mPersenter.requestHomeData(1)
    }

    override fun showLoading() {
        if (isShowLoading) super.showLoading()
    }

}