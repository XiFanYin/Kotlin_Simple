package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean

/**
 * Created by Administrator on 2018/5/25.
 */
class FocusItemAdapter(data: List<FocusBean.Item.Data.Item>, layout: Int) : BaseQuickAdapter<FocusBean.Item.Data.Item, BaseViewHolder>(layout, data) {
    lateinit var str: String

    override fun convert(helper: BaseViewHolder, item: FocusBean.Item.Data.Item) {
        helper.setText(R.id.tv_title, item.data.title)
        str = "#"
        for (tag in item.data.tags) {
            str = str + tag.name + "/"
        }
        helper.setText(R.id.tv_tag, str)
        var iv_cover_feed = helper.getView<ImageView>(R.id.iv_cover_feed)
        Glide.with(mContext).load(item.data.cover.detail).into(iv_cover_feed)

    }
}