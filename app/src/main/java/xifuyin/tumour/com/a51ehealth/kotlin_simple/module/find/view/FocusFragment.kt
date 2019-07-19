package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_focus_layout.*
import kotlinx.android.synthetic.main.fragment_focus_layout.mRecyclerView
import kotlinx.android.synthetic.main.fragment_focus_layout.mSwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_home_layout.*
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

    //伴生对象
    companion object {
        fun getInstance(): FocusFragment {
            val fragment = FocusFragment()
            return fragment
        }
    }

    override fun initPersenter() = FocusPersenter(this)

    override fun getLayoutId() = R.layout.fragment_focus_layout


    override fun initListener() {

        mRecyclerView.setHasFixedSize(true)
        //设置布局是垂直显示
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        //创建adapter
        adapter = FocusAdapter(activity!!,null )
        //设置adapter
        mRecyclerView.adapter = adapter

        /*下来刷新时候*/
        mSwipeRefreshLayout.setOnRefreshListener {
            mPersenter.getData()
        }
        //加载更多
        mSwipeRefreshLayout.setOnLoadMoreListener {
            isShowLoading = false
            if (hasMore) {//如果有更多数据，就去加载更多数据
                mPersenter.getMoreData()
            }
        }
    }
    //请求第一页数据
    override fun getSerivceData() {
        mPersenter.getData()
    }

    override fun setData(data: FocusBean, hasMore: Boolean) {
        mSwipeRefreshLayout.finishRefresh()
        this.hasMore = hasMore//是否有更多数据
        adapter.setNewData(data.itemList)//设置请求到的数据
    }

    override fun setMoreData(data: FocusBean, hasMore: Boolean) {
        this.hasMore = hasMore
        adapter.concatData(data.itemList)//添加更多数据
        if (hasMore)mSwipeRefreshLayout.finishLoadMore()else mSwipeRefreshLayout.finishLoadMoreWithNoMoreData()//如果有更多就显示加载完成，如果没有更多，显示没有更多的UI

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