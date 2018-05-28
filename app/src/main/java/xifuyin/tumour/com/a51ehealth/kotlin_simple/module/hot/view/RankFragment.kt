package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_rank_layout.*

import xifuyin.tumour.com.a51ehealth.kotlin_simple.R
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseMvpFragment
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.view.VideoDetailActivity
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.model.RankBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.persenter.RankPersenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.persenter.contact.RankContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.view.adapter.RankAdapter

/**
 * Created by Administrator on 2018/5/28.
 */
class RankFragment : BaseMvpFragment<RankPersenter>(), RankContact.View {


    override fun initPersenter(): RankPersenter = RankPersenter(this)

    //传递过来接口的URL
    var url: String? = null
    var adapter: RankAdapter? = null

    //静态方法
    companion object {
        fun getInstance(url: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            bundle.putString("apiUrl", url)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments?.getString("apiUrl")
    }

    override fun getLayoutId(): Int = R.layout.fragment_rank_layout

    override fun initListener() {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = RankAdapter(null, R.layout.item_rank_layout)
        mRecyclerView.adapter = adapter
        //条目点击事件
        adapter?.setOnItemClickListener { _, _, position ->
            var intent = Intent(activity, VideoDetailActivity::class.java)
            intent.putExtra("video_url", this.adapter?.data?.get(position)?.data?.playUrl)
            intent.putExtra("video_title", this.adapter?.data?.get(position)?.data?.title)
            startActivity(intent)
        }
    }

    override fun getSerivceData() {
        mPersenter.getRankData(this?.url!!)
    }

    override fun setRankData(data: RankBean) {
        adapter?.setNewData(data.itemList)
    }

    override fun onRetry() {
        mPersenter.getRankData(this?.url!!)
    }

}