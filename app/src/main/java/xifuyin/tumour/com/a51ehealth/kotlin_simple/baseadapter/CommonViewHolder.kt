package com.example.mvvm_simple.baseadapter

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /*缓存View，防止View每次都find*/
    private val mSparseArray = SparseArray<View>()

    fun <T : View> getView(viewId: Int): T {
        var view = mSparseArray[viewId]
        if (view == null) {
            view = itemView.findViewById<T>(viewId)
            mSparseArray.put(viewId, view)
        }
        return view as T
    }

    /**
     * 设置文本内容
     */
    fun setText(viewId: Int, text: String): CommonViewHolder {
        getView<TextView>(viewId).setText(text)
        return this
    }

    /**
     * 设置本地图片
     */
    fun setImageResource(viewId: Int, ImageResource:Int): CommonViewHolder {
        val imageView = getView<ImageView>(viewId)
        imageView.setImageResource(ImageResource)
        return this
    }

    /**
     * 设置网络图片
     */
    fun setImageUrl(viewId: Int, url:String,value:(imageView:ImageView,url:String)->Unit): CommonViewHolder{
        value(getView(viewId),url)
        return  this
    }







}
