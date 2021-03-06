package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_classify_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseMvpFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.baseadapter.Divider
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.ClassifyPersenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact.ClassifyContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.adapter.ClassifyAdapter

/**
 * Created by Administrator on 2018/5/24.
 */
class ClassifyFragment : BaseMvpFragment<ClassifyContact.Persenter>(), ClassifyContact.View {

    lateinit var adapter: ClassifyAdapter

    //伴生对象
    companion object {
        fun getInstance(): ClassifyFragment {
            val fragment = ClassifyFragment()
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_classify_layout

    override fun initPersenter()= ClassifyPersenter(this)


    override fun initListener() {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        adapter = ClassifyAdapter(activity!!,null )
        mRecyclerView.adapter = adapter

        /*设置分割线*/
        mRecyclerView.addItemDecoration(Divider.builder().color(android.R.color.white)
                        .height(10)
                        .width(10)
                        .build())

        //跳转到分类详情中去
        adapter.setItemClickListener { view, position, data ->
            var intent = Intent(activity, ClassifyDetailActivity::class.java)
            intent.putExtra("data", data)
            startActivity(intent)
        }

    }

    override fun getSerivceData() {
        mPersenter.getData()
    }


    override fun getData(mData: ArrayList<ClassifyBean>) {
        adapter.setNewData(mData)

    }

    override fun onRetry() {
        mPersenter.getData()
    }


}