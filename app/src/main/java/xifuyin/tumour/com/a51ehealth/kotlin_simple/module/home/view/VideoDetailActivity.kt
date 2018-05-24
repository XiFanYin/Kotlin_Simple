package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view

import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseActivity
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.model.HomeBean


/**
 * Created by Administrator on 2018/5/24.
 *
 * 视频详情页面
 */
class VideoDetailActivity : BaseActivity() {

    private lateinit var itemData: HomeBean.Issue.Item.Data

    override fun getLayout(): Int = R.layout.activity_vidio_detail


    override fun initListener() {
        itemData = intent.getSerializableExtra("videodata") as HomeBean.Issue.Item.Data


    }



}