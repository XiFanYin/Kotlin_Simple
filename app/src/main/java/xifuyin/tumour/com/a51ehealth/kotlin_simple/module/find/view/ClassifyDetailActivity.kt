package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
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


/**
 * Created by Administrator on 2018/5/25.
 */
class ClassifyDetailActivity : BaseMvpActivity<ClassifyDetailPersenter>(), ClassifyDetailContact.View {


    var data: ClassifyBean? = null
    var adapter: ClassifyDetailAdapter? = null
    var hasMore: Boolean = false
    var isShowLoading: Boolean = true
    override fun initPersenter(): ClassifyDetailPersenter = ClassifyDetailPersenter(this)


    override fun getLayout(): Int = R.layout.activity_classify_detial_layout

    override fun initListener() {
        data = intent.getSerializableExtra("data") as ClassifyBean?;
        Glide.with(this).load(data?.headerImage).into(imageView)
        tv_category_desc.setText("#${data?.description}#")
        collapsing_toolbar_layout.title = data?.name
        adapter = ClassifyDetailAdapter(null, R.layout.item_classify_detail_layout)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = adapter
        //和状态栏有关的
        mImmersionBar.titleBar(toolbar).init()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE) //设置还没收缩时状态下字体颜色
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK) //设置收缩后Toolbar上字体的颜色
        app_bar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {

            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                if (state == AppBarStateChangeListener.State.EXPANDED) {
                    //展开状态
                    changeBackColor(android.R.color.white)
                    mImmersionBar?.statusBarDarkFont(false)?.init()
                } else if (state == AppBarStateChangeListener.State.COLLAPSED) {
                    //折叠状态
                    changeBackColor(android.R.color.black)
                    mImmersionBar?.statusBarDarkFont(true)?.init()
                }

            }
        })
        //加载更多
        adapter?.setOnLoadMoreListener({
            if (hasMore) {
                isShowLoading = false
                mPersenter.getLoadMoreData()
            }
        }, mRecyclerView)
        //条目点击事件
        adapter?.setOnItemClickListener({ _, _, position ->
            var intent = Intent(this, VideoDetailActivity::class.java)
            intent.putExtra("video_url", this.adapter?.data?.get(position)?.data?.playUrl)
            intent.putExtra("video_title", this.adapter?.data?.get(position)?.data?.title)
            startActivity(intent)
        })

    }


    override fun getSerivceData() {
        mPersenter.getData(data?.id!!)
    }

    override fun setData(data: ClassifyDetailBean, hasMore: Boolean) {
        this.hasMore = hasMore
        adapter?.setNewData(data.itemList)
    }

    override fun setMoreData(data: ClassifyDetailBean, hasMore: Boolean) {
        this.hasMore = hasMore
        adapter?.addData(data.itemList)
        adapter?.loadMoreComplete()
    }


    override fun onRetry() {
        mPersenter.getData(data?.id!!)
    }


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