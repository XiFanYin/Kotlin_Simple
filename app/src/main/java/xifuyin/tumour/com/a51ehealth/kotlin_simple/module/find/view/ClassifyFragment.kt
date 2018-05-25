package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.classify_fragment_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseMvpFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.ClassifyPersenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact.ClassifyContact

/**
 * Created by Administrator on 2018/5/24.
 */
class ClassifyFragment : BaseMvpFragment<ClassifyContact.Persenter>(), ClassifyContact.View {

    lateinit var adapter: ClassifyAdapter

    //静态方法
    companion object {
        fun getInstance(): ClassifyFragment {
            val fragment = ClassifyFragment()
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.classify_fragment_layout

    override fun initPersenter(): ClassifyContact.Persenter = ClassifyPersenter(this)


    override fun initListener() {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        adapter = ClassifyAdapter(null, R.layout.item_classify)
        mRecyclerView.adapter = adapter

        adapter.setOnItemClickListener({ _, _, position ->


        })
    }

    override fun getSerivceData() {
        mPersenter.getData()
    }


    override fun getData(mData: ArrayList<ClassifyBean>) {
        adapter.addData(mData)


    }

    override fun onRetry() {
        mPersenter.getData()
    }


}