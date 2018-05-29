package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_focus_layout.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseMvpFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.FocusPersenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact.FocusContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view.adapter.FocusAdapter

/**
 * Created by Administrator on 2018/5/24.
 */
class FocusFragment : BaseMvpFragment<FocusContact.Persenter>(), FocusContact.View {


    lateinit var adapter: FocusAdapter
    var hasMore: Boolean = false
    var isShowLoading: Boolean = true

    //静态方法
    companion object {
        fun getInstance(): FocusFragment {
            val fragment = FocusFragment()
            return fragment
        }
    }

    override fun initPersenter(): FocusContact.Persenter = FocusPersenter(this)

    override fun getLayoutId(): Int = R.layout.fragment_focus_layout


    override fun initListener() {

        mRecyclerView.setHasFixedSize(true)
        //设置布局是垂直显示
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        //创建adapter
        adapter = FocusAdapter(null, R.layout.item_focus_layout)
        //设置adapter
        mRecyclerView.adapter = adapter
        //加载更多
        adapter.setOnLoadMoreListener({
            isShowLoading = false
            if (hasMore) {
                mPersenter.getMoreData()
            }
        }, mRecyclerView)
    }

    override fun getSerivceData() {
        mPersenter.getData()

    }

    override fun setData(data: FocusBean, hasMore: Boolean) {
        this.hasMore = hasMore
        adapter.setNewData(data.itemList)
    }

    override fun setMoreData(data: FocusBean, hasMore: Boolean) {
        this.hasMore = hasMore
        adapter.addData(data.itemList)
        if (hasMore) {
            adapter.loadMoreComplete()
        } else {
            adapter.loadMoreEnd()

        }

    }


    override fun onRetry() {
        isShowLoading = true
        mPersenter.getData()
    }

    override fun showLoading() {
        if (isShowLoading) {
            super.showLoading()
        }
    }
}