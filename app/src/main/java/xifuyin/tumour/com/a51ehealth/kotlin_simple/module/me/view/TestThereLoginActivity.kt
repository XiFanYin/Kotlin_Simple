package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.me.view

import android.content.Intent
import android.util.Log
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.activity_test_login.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseActivity

/**
 * Created by Administrator on 2018/8/3.
 */

class TestThereLoginActivity : BaseActivity() {


    override fun getLayout() = R.layout.activity_test_login


    override fun initListener() {
        //测试三方登录
        test_login.setOnClickListener {
            UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, object : UMAuthListener {
                override fun onComplete(p0: SHARE_MEDIA?, p1: Int, p2: MutableMap<String, String>) {
                    val uid = p2.get("uid")
                    val name = p2.get("name")
                    val gender = p2.get("gender")
                    val iconurl = p2.get("iconurl")
                    Log.e("Rrrrrrr", uid)
                    Log.e("Rrrrrrr", name)
                    Log.e("Rrrrrrr", gender)
                    Log.e("Rrrrrrr", iconurl)

                }

                override fun onCancel(p0: SHARE_MEDIA?, p1: Int) {
                    Log.e("Rrrrrrr", "onCancel")
                }

                override fun onError(p0: SHARE_MEDIA?, p1: Int, p2: Throwable?) {
                    Log.e("Rrrrrrr", "onError")
                }

                override fun onStart(p0: SHARE_MEDIA?) {
                    Log.e("Rrrrrrr", "onStart")
                }
            })

        }

    }

    //需要在使用QQ分享或者授权的Activity中添加：
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}
