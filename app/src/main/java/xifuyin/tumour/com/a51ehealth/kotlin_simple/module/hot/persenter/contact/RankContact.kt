package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.persenter.contact

import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseView
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.model.RankBean

/**
 * Created by Administrator on 2018/5/28.
 */
interface RankContact {

    interface View : BaseView {


        fun setRankData(data: RankBean)
    }


    interface Persenter : BasePresenter {

        fun getRankData(url: String)

    }
}