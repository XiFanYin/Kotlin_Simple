package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view.VideoDetailActivity

/**
 * Created by Administrator on 2018/5/25.
 */
class FocusAdapter(data: List<FocusBean.Item>?, layout: Int) : BaseQuickAdapter<FocusBean.Item, BaseViewHolder>(layout, data) {

    override fun convert(helper: BaseViewHolder, item: FocusBean.Item) {
        //设置名称和描述
        helper.setText(R.id.tv_title, item.data.header.title)
        helper.setText(R.id.tv_desc, item.data.header.description)
        //设置头像
        val iv_avatar = helper.getView<ImageView>(R.id.iv_avatar)
        Glide.with(mContext).load(item.data.header.icon).into(iv_avatar)
        //找到每个条目的横向爱那个列表
        val fl_recyclerView = helper.getView<RecyclerView>(R.id.fl_recyclerView)
        fl_recyclerView.setHasFixedSize(true)
        //设置布局模式
        fl_recyclerView.layoutManager = LinearLayoutManager(mContext as Activity, LinearLayoutManager.HORIZONTAL, false)
        //创建设置适配器
        val adapter = FocusItemAdapter(item.data.itemList, R.layout.item_focus_horizontal_layout)
        fl_recyclerView.adapter = adapter
        //设置点击事件
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(mContext, VideoDetailActivity::class.java)
            intent.putExtra("video_url", item.data.itemList.get(position).data.playUrl)
            intent.putExtra("video_title", item.data.itemList.get(position).data.title)
            (mContext as Activity).startActivity(intent)
        }
    }
}