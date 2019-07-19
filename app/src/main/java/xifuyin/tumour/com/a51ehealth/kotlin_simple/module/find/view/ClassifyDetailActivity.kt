package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_classify_detial_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseMvpActivity
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyDetailBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.ClassifyDetailPersenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact.ClassifyDetailContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.adapter.ClassifyDetailAdapter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view.VideoDetailActivity
import xifuyin.tumour.com.a51ehealth.kotlin_simple.utils.AppBarStateChangeListener
import xifuyin.tumour.com.a51ehealth.kotlin_simple.utils.AppBarStateChangeListener.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.utils.AppBarStateChangeListener.State.*


/**
 * Created by Administrator on 2018/5/25.
 */
class ClassifyDetailActivity : BaseMvpActivity<ClassifyDetailPersenter>(), ClassifyDetailContact.View {


    lateinit var data: ClassifyBean
    lateinit var adapter: ClassifyDetailAdapter
    var hasMore: Boolean = false
    var isShowLoading: Boolean = true

    override fun initPersenter() = ClassifyDetailPersenter(this)


    override fun getLayout() = R.layout.activity_classify_detial_layout

    override fun initListener() {
        //获取传递过来的数据
        data = intent.getSerializableExtra("data") as ClassifyBean
        Log.e("rrrrrrrrrr",data.toString())
        //加载头部图片
        Glide.with(this).load(data.headerImage).into(imageView)
        //加载头部描述信息
        tv_category_desc.setText("#${data.description}#")
        //设置title名字
        collapsing_toolbar_layout.title = data.name
        //创建适配器，设置适配器
        adapter = ClassifyDetailAdapter(null, R.layout.item_classify_detail_layout)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = adapter
        //和状态栏有关的
        mImmersionBar.titleBar(toolbar).init()
        setSupportActionBar(toolbar)
        //设置有返回键
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //返回键点击事件
        toolbar.setNavigationOnClickListener { finish() }
        //设置title的颜色
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE) //设置还没收缩时状态下字体颜色
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK) //设置收缩后Toolbar上字体的颜色
        //添加滚动监听
        app_bar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                if (state == EXPANDED) {
                    //展开状态
                    changeBackColor(android.R.color.white)//改变返回和字体颜色
                    mImmersionBar.statusBarDarkFont(false).init()//改变状态栏的字体颜色
                } else if (state == COLLAPSED) {
                    //折叠状态
                    changeBackColor(android.R.color.black)//改变返回和字体颜色
                    mImmersionBar.statusBarDarkFont(true).init()//改变状态栏的字体颜色
                }

            }
        })

        //加载更多
        adapter.setOnLoadMoreListener({
            if (hasMore) {
                isShowLoading = false
                mPersenter.getLoadMoreData()
            }
        }, mRecyclerView)
        //条目点击事件
        adapter.setOnItemClickListener({ _, _, position ->
            var intent = Intent(this, VideoDetailActivity::class.java)
            intent.putExtra("video_url", this.adapter.data.get(position).data.playUrl)
            intent.putExtra("video_title", this.adapter.data.get(position).data.title)
            intent.putExtra("image_url", this.adapter.data.get(position).data.cover.detail)
            intent.putExtra("description", this.adapter.data.get(position).data.description)
            startActivity(intent)
        })

    }


    override fun getSerivceData() {
        mPersenter.getData(data.id)
    }

    override fun setData(data: ClassifyDetailBean, hasMore: Boolean) {
        this.hasMore = hasMore
        adapter.setNewData(data.itemList)
    }

    override fun setMoreData(data: ClassifyDetailBean, hasMore: Boolean) {
        this.hasMore = hasMore
        adapter.addData(data.itemList)
        adapter.loadMoreComplete()
    }


    override fun onRetry() {
        mPersenter.getData(data.id)
    }

    //改变颜色的方法
    private fun changeBackColor(color: Int) {
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }

    override fun showLoading() {
        if (isShowLoading) {
            super.showLoading()
        }

    }

}