package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.me.view

import android.content.Intent
import android.graphics.PorterDuff
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_me_layout.*
import kotlinx.android.synthetic.main.layout_head.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.AboutAuthorActivity
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.me.view.adapter.MeAdapter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.utils.AppBarStateChangeListener
import xifuyin.tumour.com.a51ehealth.kotlin_simple.utils.GlideApp

/**
 * Created by Administrator on 2018/5/21.
 */
class MeFragment : BaseFragment() {

    //静态方法
    companion object {
        fun getInstance(): MeFragment {
            val fragment = MeFragment()
            return fragment
        }
    }


    override fun getLayoutId(): Int = R.layout.fragment_me_layout

    override fun initListener() {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this!!.activity)
        var item = listOf<String>("我的消息", "观看记录", "我的缓存", "我的下载", "意见反馈","新手任务","我的上传","我的收益","关于作者")
        var adapter = MeAdapter(item, android.R.layout.simple_list_item_1)
        mRecyclerView.adapter = adapter
        GlideApp.with(this!!.activity!!).load(R.drawable.title_image).circleCrop().into(image)
        app_bar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                if (state == AppBarStateChangeListener.State.EXPANDED) {
                    //展开状态
                    title.setTextColor(resources.getColor(android.R.color.white))
                    mImmersionBar?.statusBarDarkFont(false)?.init()
                } else if (state == AppBarStateChangeListener.State.COLLAPSED) {
                    //折叠状态
                    title.setTextColor(resources.getColor(android.R.color.black))
                    mImmersionBar?.statusBarDarkFont(true)?.init()
                }

            }
        })
        adapter.setOnItemClickListener({_,_,p->
            var intent = Intent(activity,AboutAuthorActivity::class.java)
            startActivity(intent)
        })
        //测试第三方登录
        image.setOnClickListener {
            var intent = Intent(activity,TestThereLoginActivity::class.java)
            startActivity(intent)

        }
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        mImmersionBar?.titleBar(toolbar)?.init()

    }

    override fun getSerivceData() {


    }


}