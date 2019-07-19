package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mvvm_simple.baseadapter.BaseAdapter
import com.example.mvvm_simple.baseadapter.CommonViewHolder
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean

/**
 * Created by Administrator on 2018/5/25.
 */
class FocusItemAdapter(context:Context,data: MutableList<FocusBean.Item.Data.Item>) : BaseAdapter<FocusBean.Item.Data.Item>( context,R.layout.item_focus_horizontal_layout, data) {

    lateinit var str: String

    override fun convert(holder: CommonViewHolder, data: FocusBean.Item.Data.Item, position: Int) {
        //     //设置子条目的Ui
        holder.setText(R.id.tv_title, data.data.title)
        str = "#"
        for (tag in data.data.tags) {
            str = str + tag.name + "/"
        }
        holder.setText(R.id.tv_tag, str)
        var iv_cover_feed = holder.getView<ImageView>(R.id.iv_cover_feed)
        Glide.with(mContext).load(data.data.cover.detail).into(iv_cover_feed)
    }




}