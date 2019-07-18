package com.example.mvvm_simple.baseadapter

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup

/**
 * 仿照listView去实现RecyclerView添加头部和尾部
 */
@Deprecated("")
class WrapRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /*原始的adapter，不包含头部和底部*/
    private val mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    /*储存头部的集合*/
    private val mHeads: SparseArray<View>
    private val mFoods: SparseArray<View>

    companion object {
        var BASE_HEAD_KEY = 100000
        var BASE_FOOD_KEY = 300000
    }

    /*构造方法*/
    constructor(mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        this.mAdapter = mAdapter
        mHeads = SparseArray()
        mFoods = SparseArray()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        /*判断是否是头部*/
        if (mHeads.indexOfKey(viewType) >= 0) {
            //是头部
            return creatFooterHeaderViewHeader(mHeads.get(viewType))
        } else if (mFoods.indexOfKey(viewType) >= 0) {
            //是尾部
            return creatFooterHeaderViewHeader(mFoods.get(viewType))
        } else {
            //如果是正常的列表
            return mAdapter.onCreateViewHolder(parent, viewType)
        }

    }

    override fun getItemCount(): Int {
        return mAdapter.itemCount + mHeads.size() + mFoods.size()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        /*头部和尾部都不用去绑定数据*/
        val numberHead = mHeads.size()
        //如果是头部，需要return
        if (position < numberHead) {
            return
        }
        /*真实的列表有多个条目*/
        val adjPosition = position - numberHead
        val adapterCount = mAdapter.itemCount
        if (adjPosition < adapterCount) {
            mAdapter.onBindViewHolder(holder, adjPosition)
        }
    }


    override fun getItemViewType(position: Int): Int {
        /*获取多少和头*/
        val numberHead = mHeads.size()
        if (position < numberHead) {
            return mHeads.keyAt(position)
        }
        /*真实的列表有多个条目*/
        val adjPosition = position - numberHead
        var adapterCount = 0
        adapterCount = mAdapter.itemCount
        if (adjPosition < adapterCount) {
            return mAdapter.getItemViewType(adjPosition)
        }
        return mFoods.keyAt(adjPosition - adapterCount)
    }


    fun creatFooterHeaderViewHeader(mView: View): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(mView) {
        }
    }


    /**
     * 添加头
     */
    fun addHeadView(mView: View) {
        /*如果集合中没有，就添加进去，防止重复添加*/
        if (mHeads.indexOfValue(mView) == -1) {
            mHeads.put(BASE_HEAD_KEY++, mView)
            notifyDataSetChanged()
        }
    }

    /**
     * 添加尾
     */
    fun addFoodView(mView: View) {
        /*如果集合中没有，就添加进去，防止重复添加*/
        if (mFoods.indexOfValue(mView) == -1) {
            mFoods.put(BASE_FOOD_KEY++, mView)
            notifyDataSetChanged()
        }

    }

    /**
     *移除头
     */
    fun removeHeadView(mView: View) {
        if (mHeads.indexOfValue(mView) >= 0) {
            /*移除当前head*/
            mHeads.removeAt(mHeads.indexOfValue(mView))
            notifyDataSetChanged()
        }
    }

    /**
     * 移除尾
     */
    fun removeFoodView(mView: View) {
        if (mFoods.indexOfValue(mView) >= 0) {
            /*移除当前head*/
            mFoods.removeAt(mFoods.indexOfValue(mView))
            notifyDataSetChanged()
        }
    }
}



