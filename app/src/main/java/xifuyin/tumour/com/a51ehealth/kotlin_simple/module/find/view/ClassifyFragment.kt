package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseFragment

/**
 * Created by Administrator on 2018/5/24.
 */
class ClassifyFragment :BaseFragment(){

    //静态方法
    companion object {
        fun getInstance(): ClassifyFragment {
            val fragment = ClassifyFragment()
            return fragment
        }
    }
    override fun initListener() {

    }

    override fun getSerivceData() {

    }

    override fun getLayoutId(): Int  = R.layout.classify_fragment_layout
}