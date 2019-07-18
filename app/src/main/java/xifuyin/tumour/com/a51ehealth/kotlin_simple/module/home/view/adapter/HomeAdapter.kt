package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view.adapter


import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mvvm_simple.baseadapter.BaseAdapter
import com.example.mvvm_simple.baseadapter.CommonViewHolder
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.model.HomeBean

/**
 * Created by Administrator on 2018/5/23.
 */
class HomeAdapter(context: Context, data: MutableList<HomeBean.Issue.Item>?) : BaseAdapter<HomeBean.Issue.Item>(context, R.layout.item_home_content_layout, data) {
    lateinit var str: String

    override fun convert(holder: CommonViewHolder, data: HomeBean.Issue.Item, position: Int) {
        holder.setText(R.id.tv_title, data.data.title)
        str = "#"
        for (tag in data.data.tags) {
            str = str + tag.name + "/"
        }
        holder.setText(R.id.tv_tag, str)
        holder.setText(R.id.tv_category, "#${data.data.category}")
        var iv_cover_feed = holder.getView<ImageView>(R.id.iv_cover_feed)
        Glide.with(mContext).load(data.data.cover.detail).into(iv_cover_feed)
        var iv_avatar = holder.getView<ImageView>(R.id.iv_avatar)
        Glide.with(mContext).load(data.data?.author?.icon).into(iv_avatar)

    }


}