package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mvvm_simple.baseadapter.BaseAdapter
import com.example.mvvm_simple.baseadapter.CommonViewHolder
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view.VideoDetailActivity

/**
 * Created by Administrator on 2018/5/25.
 */
class FocusAdapter(context: Context, data: MutableList<FocusBean.Item>?) : BaseAdapter<FocusBean.Item>(context,R.layout.item_focus_layout, data) {
    override fun convert(holder: CommonViewHolder, data: FocusBean.Item, position: Int) {
        //设置名称和描述
        holder.setText(R.id.tv_title, data.data.header.title)
        holder.setText(R.id.tv_desc, data.data.header.description)
        //设置头像
        val iv_avatar = holder.getView<ImageView>(R.id.iv_avatar)
        Glide.with(mContext).load(data.data.header.icon).into(iv_avatar)
        //找到每个条目的横向爱那个列表
        val fl_recyclerView = holder.getView<RecyclerView>(R.id.fl_recyclerView)
        fl_recyclerView.setHasFixedSize(true)
        //设置布局模式
        fl_recyclerView.layoutManager = LinearLayoutManager(mContext as Activity, LinearLayoutManager.HORIZONTAL, false)
        //创建设置适配器
        val adapter = FocusItemAdapter(mContext,data.data.itemList)
        fl_recyclerView.adapter = adapter
        //设置点击事件
        adapter.setItemClickListener { view, position, data ->
            val intent = Intent(mContext, VideoDetailActivity::class.java)
            intent.putExtra("video_url", data.data.playUrl)
            intent.putExtra("video_title", data.data.title)
            intent.putExtra("image_url", data.data.cover.detail)
            intent.putExtra("description", data.data.description)
            (mContext as Activity).startActivity(intent)

        }
    }


}