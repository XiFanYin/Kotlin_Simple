package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mvvm_simple.baseadapter.BaseAdapter
import com.example.mvvm_simple.baseadapter.CommonViewHolder
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyBean

/**
 * Created by Administrator on 2018/5/25.
 */
class ClassifyAdapter(context: Context,data:MutableList<ClassifyBean>?) :BaseAdapter<ClassifyBean>(context,R.layout.item_classify_layout,data){
    override fun convert(holder: CommonViewHolder, data: ClassifyBean, position: Int) {
        holder.setText(R.id.tv_category_name,"#${data.name}")
        var bg =  holder.getView<ImageView>(R.id.iv_category)
        Glide.with(mContext).load(data.bgPicture).into(bg)
    }


}