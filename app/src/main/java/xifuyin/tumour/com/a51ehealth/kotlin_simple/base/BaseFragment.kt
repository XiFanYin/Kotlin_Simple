package xifuyin.tumour.com.a51ehealth.kotlin_simple.base


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gyf.barlibrary.ImmersionBar
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R

/**
 * Created by Administrator on 2018/5/21.
 *
 *
 */
abstract class BaseFragment : Fragment() {

    var isVisibleToUser: Boolean = false//当前页面是否显示在用户面前
    var isViewInitiated: Boolean = false //当前页面是否初始化
    var isDataRequested: Boolean = false //是否已经请求了数据
    var mImmersionBar: ImmersionBar? = null

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareGetData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayoutId2(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isViewInitiated = true
        initListener2()
        prepareGetData()
    }

    /*布局*/
    open fun getLayoutId2(): Int {

        return getLayoutId()
    }

    /*布局*/
    abstract fun getLayoutId(): Int


    open protected fun initListener2() {
        initListener()
    }

    protected abstract fun initListener()


    /**
     * 如果只想第一次进入该页面请求数据，return prepareGetData(false)
     * 如果想每次进入该页面就请求数据，return prepareGetData(true)
     *
     * @return
     */
    open fun prepareGetData() {
        return GetData(false)
    }


    fun GetData(isforceUpdate: Boolean) {
        //和状态栏有关的
        if (isViewInitiated && isVisibleToUser) {
            initImmersionBar()
        }
        //和请求数据有关的
        if (isViewInitiated && isVisibleToUser && (!isDataRequested || isforceUpdate)) {
            /*从服务器获取数据*/
            getSerivceData()
            isDataRequested = true
        }


    }

    /**
     * 初始化沉浸式
     */
    open fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.keyboardEnable(true)?.navigationBarWithKitkatEnable(false)?.init()

    }

    /**
     * 当用户可见时候获取数据
     */
    abstract fun getSerivceData()


    override fun onDestroy() {
        super.onDestroy()
        if (mImmersionBar != null)
            mImmersionBar?.destroy()

    }
}