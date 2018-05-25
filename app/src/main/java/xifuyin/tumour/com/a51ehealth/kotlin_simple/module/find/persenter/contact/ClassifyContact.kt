package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact

import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseView
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyBean

/**
 * Created by Administrator on 2018/5/25.
 */
interface ClassifyContact {

    interface View : BaseView {

        fun getData(mData: ArrayList<ClassifyBean>)
    }

    interface Persenter : BasePresenter {

        fun getData()
    }
}