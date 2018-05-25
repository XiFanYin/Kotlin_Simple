package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.focus_fragment_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseMvpFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.FocusPersenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact.FocusContact

/**
 * Created by Administrator on 2018/5/24.
 */
class FocusFragment : BaseMvpFragment<FocusContact.Persenter>(), FocusContact.View {

    lateinit var adapter: FocusAdapter

    //静态方法
    companion object {
        fun getInstance(): FocusFragment {
            val fragment = FocusFragment()
            return fragment
        }
    }

    override fun initPersenter(): FocusContact.Persenter = FocusPersenter(this)

    override fun getLayoutId(): Int = R.layout.focus_fragment_layout


    override fun initListener() {

        mRecyclerView.setHasFixedSize(true)
        //设置布局是垂直显示
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        //创建adapter
        adapter = FocusAdapter(null, R.layout.item_focus_fragment)
        //设置adapter
        mRecyclerView.adapter = adapter

    }

    override fun getSerivceData() {
        mPersenter.getData()

    }

    override fun setData(data: FocusBean) {
        adapter.setNewData(data.itemList)
    }


    override fun onRetry() {

    }
}