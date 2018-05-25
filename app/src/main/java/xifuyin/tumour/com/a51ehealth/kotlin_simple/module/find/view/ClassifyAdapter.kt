package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyBean

/**
 * Created by Administrator on 2018/5/25.
 */
class ClassifyAdapter(data:List<ClassifyBean>?,layout:Int) :BaseQuickAdapter<ClassifyBean,BaseViewHolder>(layout,data){


    override fun convert(helper: BaseViewHolder, item: ClassifyBean) {
        helper.setText(R.id.tv_category_name,"#${item.name}")
       var bg =  helper.getView<ImageView>(R.id.iv_category)
        Glide.with(mContext).load(item.bgPicture).into(bg)
    }
}