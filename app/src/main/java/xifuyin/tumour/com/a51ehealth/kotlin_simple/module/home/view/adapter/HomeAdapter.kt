package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view.adapter


import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.model.HomeBean

/**
 * Created by Administrator on 2018/5/23.
 */
class HomeAdapter(layoutResId: Int, data: List<HomeBean.Issue.Item>?) : BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(layoutResId, data) {
    lateinit var str: String

    override fun convert(helper: BaseViewHolder, item: HomeBean.Issue.Item) {
        helper.setText(R.id.tv_title, item.data.title)
        str = "#"
        for (tag in item.data.tags) {
            str = str + tag.name + "/"
        }
        helper.setText(R.id.tv_tag, str)
        helper.setText(R.id.tv_category, "#${item.data.category}")
        var iv_cover_feed = helper.getView<ImageView>(R.id.iv_cover_feed)
        Glide.with(mContext).load(item.data.cover.detail).into(iv_cover_feed)
        var iv_avatar = helper.getView<ImageView>(R.id.iv_avatar)
        Glide.with(mContext).load(item.data?.author?.icon).into(iv_avatar)
    }


}