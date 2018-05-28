package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_classify_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseMvpFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.ClassifyPersenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact.ClassifyContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.adapter.ClassifyAdapter

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

    override fun getLayoutId(): Int = R.layout.fragment_classify_layout

    override fun initPersenter(): ClassifyContact.Persenter = ClassifyPersenter(this)


    override fun initListener() {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        adapter = ClassifyAdapter(null, R.layout.item_classify_layout)
        mRecyclerView.adapter = adapter
        //跳转到分类详情中去
        adapter.setOnItemClickListener({ _, _, position ->
            var intent = Intent(activity, ClassifyDetailActivity::class.java)
            intent.putExtra("data", this.adapter.data.get(position))
            startActivity(intent)

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