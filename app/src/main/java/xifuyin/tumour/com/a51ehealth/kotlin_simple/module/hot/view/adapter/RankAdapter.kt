package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.view.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.model.RankBean

/**
 * Created by Administrator on 2018/5/28.
 */
class RankAdapter(data: List<RankBean.Item>?, layout: Int) : BaseQuickAdapter<RankBean.Item, BaseViewHolder>(layout, data) {

    override fun convert(helper: BaseViewHolder, item: RankBean.Item) {
        helper.setText(R.id.tv_title, item.data.title)
        var iv_image = helper.getView<ImageView>(R.id.iv_image)
        Glide.with(mContext).load(item.data.cover.detail).into(iv_image)
        val timeFormat = durationFormat(item.data.duration)
        helper.setText(R.id.tv_tag, "#${item.data.category}/$timeFormat")
    }

    /**
     * 格式化时间
     */
    fun durationFormat(duration: Int?): String {
        val minute = duration!! / 60
        val second = duration % 60
        return if (minute <= 9) {
            if (second <= 9) {
                "0$minute' 0$second''"
            } else {
                "0$minute' $second''"
            }
        } else {
            if (second <= 9) {
                "$minute' 0$second''"
            } else {
                "$minute' $second''"
            }
        }
    }
}