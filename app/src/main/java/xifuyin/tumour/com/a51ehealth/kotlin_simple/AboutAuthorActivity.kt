package xifuyin.tumour.com.a51ehealth.kotlin_simple

import kotlinx.android.synthetic.main.activity_about_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseActivity

/**
 * Created by Administrator on 2018/5/29.
 */

class AboutAuthorActivity : BaseActivity() {

    override fun getLayout(): Int = R.layout.activity_about_layout

    override fun initListener() {
        mWebView.loadUrl("https://github.com/XiFanYin")
    }


}
