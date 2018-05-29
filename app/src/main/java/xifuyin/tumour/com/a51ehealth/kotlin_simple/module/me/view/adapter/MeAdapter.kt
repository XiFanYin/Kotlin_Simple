package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.me.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R

/**
 * Created by Administrator on 2018/5/29.
 */
class MeAdapter(data: List<String>,layout :Int) :BaseQuickAdapter<String,BaseViewHolder>(layout,data) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(android.R.id.text1,item)
    }
}