package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_classify_detial.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseMvpActivity
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyDetailBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.ClassifyDetailPersenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact.ClassifyDetailContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.adapter.ClassifyDetailAdapter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.utils.AppBarStateChangeListener


/**
 * Created by Administrator on 2018/5/25.
 */
class ClassifyDetailActivity : BaseMvpActivity<ClassifyDetailPersenter>(), ClassifyDetailContact.View {


    var data: ClassifyBean? = null
    var adapter: ClassifyDetailAdapter? = null

    override fun initPersenter(): ClassifyDetailPersenter = ClassifyDetailPersenter(this)


    override fun getLayout(): Int = R.layout.activity_classify_detial

    override fun initListener() {
        data = intent.getSerializableExtra("data") as ClassifyBean?;
        Glide.with(this).load(data?.headerImage).into(imageView)
        tv_category_desc.setText("#${data?.description}#")
        collapsing_toolbar_layout.title = data?.name
        adapter = ClassifyDetailAdapter(null, R.layout.item_classify_detail)
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
    }


    override fun getSerivceData() {
        mPersenter.getData(data?.id!!)
    }

    override fun setData(data: ClassifyDetailBean) {
        adapter?.setNewData(data.itemList)
    }



    override fun onRetry() {
        mPersenter.getData(data?.id!!)
    }


    private fun changeBackColor(color: Int) {
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }

}