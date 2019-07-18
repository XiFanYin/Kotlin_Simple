package com.example.mvvm_simple.baseadapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 使用的时候应该注意：设置layoutManager必须在所有添加头，尾，还有空布局之前
 * 最好的使用顺序是：创建adapter，设置adapter，设置布局管理者，添加头，添加尾，添加空布局
 *
 *
 */
abstract class BaseAdapter<DATA> : RecyclerView.Adapter<CommonViewHolder> {
    /*上下文*/
    var mContext: Context
    /*单条目布局*/
    private var mLayoutId: Int
    /*列表数据集合*/
    private var mDatas: MutableList<DATA>? = null
    /*打气筒*/
    private val mLayoutInflater: LayoutInflater
    /*传入条目布局，返回布局id*/
    private var mTypeSupport: ((DATA) -> Int)? = null
    /*条目点击*/
    private var clickValue: ((View, Int,DATA) -> Unit)? = null
    /*条目长按*/
    private var clickLongValue: ((View, Int,DATA) -> Unit)? = null
    /*子View被点击*/
    private var chindClickValue: ((Int, View, Int,DATA) -> Unit)? = null
    /*设置点击事件子View的ID*/
    private lateinit var clickViewIds: IntArray
    /*储存头部的集合*/
    private val mHeads: SparseArray<View>
    private val mFoods: SparseArray<View>

    companion object {
        /*储存头的key*/
        var BASE_HEAD_KEY = 100000
        /*储存尾的key*/
        var BASE_FOOD_KEY = 300000
    }

    /*空布局*/
    private var mEmptyView: View? = null

    /**
     * 单类型条目的构造方法
     */
    constructor(mContext: Context, mLayoutId: Int, mDatas: MutableList<DATA>? = null) {
        this.mContext = mContext
        this.mLayoutId = mLayoutId
        this.mDatas = mDatas
        mLayoutInflater = LayoutInflater.from(mContext)
        mHeads = SparseArray()
        mFoods = SparseArray()
    }

    /*多类型条目的构造方法*/
    constructor(mContext: Context, mDatas: MutableList<DATA>? = null, value: (data: DATA) -> Int) : this(
            mContext,
            -1,
            mDatas
    ) {
        this.mTypeSupport = value
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {

        /*判断是否是头部*/
        if (mHeads.indexOfKey(viewType) >= 0) {
            //是头部
            return CommonViewHolder(mHeads.get(viewType))
        } else if (mFoods.indexOfKey(viewType) >= 0) {
            //是尾部
            return CommonViewHolder(mFoods.get(viewType))
        } else if (isEmptyViewShow()) {
            /*如果没有数据，就显示空布局*/
            return CommonViewHolder(mEmptyView!!)
        } else {
            /*如果不等于空，证明是需要多布局*/
            mTypeSupport?.let { mLayoutId = viewType }
            /*实例化item对象*/
            val itemView = mLayoutInflater.inflate(mLayoutId, parent, false)
            return CommonViewHolder(itemView)
        }


    }


    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        /*如果没有数据，就显示空布局*/
        if (isEmptyViewShow()) {
            return
        }
        /*获取多少和头*/
        val numberHead = mHeads.size()
        val numberFood = mFoods.size()
        /*如果是头部返回头的类型*/
        if (position < numberHead) {
            return
        }
        /*如果是尾部返回尾部的类型*/
        if (position >= itemCount - numberFood) {
            return
        }
        /*真实的列表有多个条目*/
        val adjPosition = position - numberHead
        /*交给子View去绑定数据*/
        convert(holder, mDatas!!.get(adjPosition), adjPosition)
        /*设置条目点击事件*/
        clickValue?.let { holder.itemView.setOnClickListener { view -> it(view, adjPosition,mDatas!!.get(adjPosition)) } }
        /*设置条目的长按事件*/
        clickLongValue?.let { holder.itemView.setOnLongClickListener { view -> it(view, adjPosition,mDatas!!.get(adjPosition)); false } }
        /*封装子View的点击事件*/
        chindClickValue?.let {
            clickViewIds.forEach {
                holder.getView<View>(it)
                        .setOnClickListener { view -> chindClickValue?.let { it1 -> it1(it, view, adjPosition,mDatas!!.get(adjPosition)) } }
            }

        }

    }


    override fun getItemCount(): Int {
        /*如果没有数据，就显示空布局*/
        if (isEmptyViewShow()) {
            return 1
        }
        return (mDatas?.size ?: 0) + mHeads.size() + mFoods.size()
    }


    override fun getItemViewType(position: Int): Int {
        /*获取多少和头*/
        val numberHead = mHeads.size()
        val numberFood = mFoods.size()
        /*如果是头部返回头的类型*/

        if (position < numberHead) {
            return mHeads.keyAt(position)
        }
        /*如果是尾部返回尾部的类型*/
        if (position >= itemCount - numberFood) {
            return mFoods.keyAt(position - (itemCount - numberFood))
        }
        /*如果没有数据，就显示空布局*/
        if (isEmptyViewShow()) {
            return -999
        } else {
            /*真实的列表有多个条目*/
            val adjPosition = position - numberHead
            /*多类型布局*/
            mTypeSupport?.let { return it.invoke(mDatas!!.get(adjPosition)) }
            return super.getItemViewType(adjPosition)
        }

    }


    /**
     * 判断是否应该显示空布局
     */
    private fun isEmptyViewShow(): Boolean {
        return (mEmptyView != null) && (mDatas != null) && (mDatas?.size ?: 0) + mHeads.size() + mFoods.size() == 0
    }

    /**
     * 初始化数据监听，当布局模式是GridLayoutManager的时候添加头，改变布局样式
     */
    private fun initObserver(mRecyclerView: RecyclerView) {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                Log.e("eee","onChanged")
                if (mRecyclerView.layoutManager is GridLayoutManager) {
                    (mRecyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
                            object : GridLayoutManager.SpanSizeLookup() {
                                override fun getSpanSize(position: Int): Int {
                                    if (isHead(position) || isFood(position)) {
                                        return (mRecyclerView.layoutManager as GridLayoutManager).getSpanCount()
                                    } else {
                                        return 1
                                    }
                                }
                            }
                }
            }
        })
    }

    /**
     * 判断当前位置时候为头的位置
     */
    private fun isHead(position: Int): Boolean {
        val numberHead = mHeads.size()
        return position < numberHead
    }

    /**
     * 判断当前位置时候为尾部的位置
     */
    private fun isFood(position: Int): Boolean {
        val numberFood = mFoods.size()
        return position >= itemCount - numberFood
    }

    /**
     * 获取当前绑定的RecyclerView
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        initObserver(recyclerView)
    }


//==========================================子类复写================================================

    /*绑定数据,扔给子类去实现*/
    abstract fun convert(holder: CommonViewHolder, data: DATA, position: Int)


//    ==============================API提供=================================================


    /**
     * 设置新数据
     */
    fun setNewData(mDatas: MutableList<DATA>) {
        this.mDatas = mDatas
        notifyDataSetChanged()
    }

    /**
     * 设置加载更多的数据
     */
    fun concatData(mDatas: MutableList<DATA>) {
        this.mDatas?.addAll(mDatas)
        notifyDataSetChanged()
    }

    /**
     * 设置条目点击事件
     */
    fun setItemClickListener(clickValue: (view: View, position: Int,data:DATA) -> Unit) {
        this.clickValue = clickValue
    }

    /**
     * 设置条目长按事件
     */
    fun setItemLongClickListener(clickLongValue: (view: View, position: Int,data:DATA) -> Unit) {
        this.clickLongValue = clickLongValue
    }

    /**
     * 设置条目内部的子View的点击事件
     */
    fun setItemChindClickListener(vararg viewIds: Int, chindClickValue: (viewId: Int, view: View, position: Int,dat:DATA) -> Unit) {
        this.clickViewIds = viewIds
        this.chindClickValue = chindClickValue
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

    /**
     *设置当前空布局
     */
    fun setEmptyView(mEmptyView: View) {
        this.mEmptyView = mEmptyView
        notifyDataSetChanged()
    }


}
