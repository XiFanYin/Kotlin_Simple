package xifuyin.tumour.com.a51ehealth.kotlin_simple.app

import android.app.Application
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.squareup.leakcanary.LeakCanary
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R

/**
 * Created by Administrator on 2018/5/21.
 */
class App : Application() {
    //静态属性，私有化set方法
    companion object {
        lateinit var application: App
            private set
    }


    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator{ context, layout->
            layout.setPrimaryColorsId(android.R.color.white, android.R.color.background_dark);
            ClassicsHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator{ context, layout->
            ClassicsFooter(context).setDrawableSize(20F)

        }
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

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null)
        /*这些也需要去替换 **/
        PlatformConfig.setWeixin("wx6c609010c5101349", "c896637e1b9e97756f3ca156b64f7aa1")
        PlatformConfig.setQQZone("1107474610", "frEHNKoKEFjtZkZK")
        //这里配置log输出信息
        UMConfigure.setLogEnabled(false)
        //统计用的，不知道管什么用
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
    }


}