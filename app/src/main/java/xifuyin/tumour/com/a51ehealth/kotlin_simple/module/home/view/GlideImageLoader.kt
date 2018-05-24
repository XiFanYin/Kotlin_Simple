package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created by Administrator on 2018/5/23.
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(mContext: Context?, url: Any?, mImaggView: ImageView?) {
        if (mContext != null && mImaggView != null) {
            Glide.with(mContext).load(url).into(mImaggView)
        }

    }

}