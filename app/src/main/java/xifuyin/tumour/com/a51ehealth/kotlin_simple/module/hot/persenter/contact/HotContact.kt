package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.persenter.contact

import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseView
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.model.TabInfoBean

/**
 * Created by Administrator on 2018/5/28.
 */
interface HotContact {


    interface View : BaseView {
        fun setTableData(data: TabInfoBean)
    }


    interface Persenter : BasePresenter {

        fun getTableData()

    }
}