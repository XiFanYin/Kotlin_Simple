package xifuyin.tumour.com.a51ehealth.kotlin_simple.app

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI

/**
 * Created by Administrator on 2018/5/21.
 */
class App : Application() {
    //静态属性，私有化set方法
    companion object {
        lateinit var application: App
            private set
    }


    override fun onCreate() {
        super.onCreate()
        initUmeng()
        application = this
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);


    }

    //初始化友盟
    private fun initUmeng() {

        UMConfigure.init(this, "5b61764ef43e483fda000131", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "")
        /*这些也需要去替换 **/
        PlatformConfig.setWeixin("wx6c609010c5101349", "c896637e1b9e97756f3ca156b64f7aa1")
        PlatformConfig.setQQZone("1107474610", "frEHNKoKEFjtZkZK")
        UMConfigure.setLogEnabled(true)

        //统计用的，不知道管什么用
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
    }


}