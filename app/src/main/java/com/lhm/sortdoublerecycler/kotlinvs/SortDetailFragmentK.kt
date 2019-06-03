package com.lhm.sortdoublerecycler.kotlinvs

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lhm.sortdoublerecycler.R
import com.lhm.sortdoublerecycler.bean.RightBean
import com.lhm.sortdoublerecycler.bean.SortBean
import com.lhm.sortdoublerecycler.javavs.ItemHeaderDecoration
import com.lhm.sortdoublerecycler.javavs.SortDetailChildAdapter
import com.lhm.sortdoublerecycler.javavs.SortDetailFragment
import com.lhm.sortdoublerecycler.listener.CheckListener
import com.lhm.sortdoublerecycler.listener.RvListener
import kotlinx.android.synthetic.main.fragment_sort_detail.*
import java.util.*

/**
 * Created on 2019/6/3 17:52
 * <p>
 * author lhm
 * <p>
 * Description:
 * <p>
 * Remarks:
 */
class SortDetailFragmentK : Fragment(), CheckListener {


    private lateinit var mAdapter: SortDetailChildAdapterK
    private lateinit var mManager: GridLayoutManager
    private val mDatas = ArrayList<RightBean>()
    private lateinit var mDecoration: ItemHeaderDecoration
    private var move = false
    private var mIndex = 0
    private lateinit var checkListener: CheckListener


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rv.addOnScrollListener(RecyclerViewListener())

        mManager = GridLayoutManager(activity, 3)
        //通过isTitle的标志来判断是否是title
        mManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (mDatas[position].isTitle == 0 || mDatas[position].isTitle == 1) {
                    3
                } else {
                    1
                }
                //                return mDatas.get(position).isTitle() ? 3 : 1;
            }
        }
        rv.setLayoutManager(mManager)
        mAdapter = SortDetailChildAdapterK(activity!!, mDatas,
            RvListener { id, position ->
                var content = ""
                when (id) {
                    R.id.root -> content = "title"
                    R.id.content -> content = "content"
                }
                val snackbar =
                    Snackbar.make(rv, "当前点击的是" + content + ":" + mDatas[position].name, Snackbar.LENGTH_SHORT)
                val mView = snackbar.view
                mView.setBackgroundColor(Color.BLUE)
                //                TextView text = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
                //                text.setTextColor(Color.WHITE);
                //                text.setTextSize(25);
                snackbar.show()
            })

        rv.adapter = mAdapter
        mDecoration = ItemHeaderDecoration(activity!!, mDatas)
        rv.addItemDecoration(mDecoration)
        mDecoration.setCheckListener(checkListener)
        initData()
        return view
    }


    private fun initData() {
        val rightList = arguments!!.getParcelableArrayList<SortBean.CategoryOneArrayBean>("right")
        val img = RightBean()
        //头部设置为true
        img.isTitle = 0
        img.titleName = rightList!![0].name
        img.tag = 0.toString()
        img.imgsrc = rightList[0].imgsrc
        mDatas.add(img)
        for (i in rightList.indices) {
            val head = RightBean()
            //头部设置为true
            head.isTitle = 1
            head.name = rightList[i].name
            head.titleName = rightList[i].name
            head.tag = i.toString()
            mDatas.add(head)
            val categoryTwoArray = rightList[i].categoryTwoArray
            for (j in categoryTwoArray.indices) {
                val body = RightBean()
                body.isTitle = 2
                body.tag = i.toString()
                body.name = rightList[i].name
                val name = rightList[i].name
                body.titleName = name
                mDatas.add(body)
            }

        }

        mAdapter.notifyDataSetChanged()
        mDecoration.setData(mDatas)
    }


    fun setData(n: Int) {
        mIndex = n
        rv.stopScroll()
        smoothMoveToPosition(n)
    }

    fun setListener(listener: CheckListener) {
        this.checkListener = listener
    }

    override fun check(position: Int, isScroll: Boolean) {
        checkListener!!.check(position, isScroll)

    }

    private fun smoothMoveToPosition(n: Int) {
        val firstItem = mManager.findFirstVisibleItemPosition()
        val lastItem = mManager.findLastVisibleItemPosition()
        Log.d("first--->", firstItem.toString())
        Log.d("last--->", lastItem.toString())
        if (n <= firstItem) {
            rv.scrollToPosition(n)
        } else if (n <= lastItem) {
            Log.d("pos---->", n.toString() + "VS" + firstItem)
            val top = rv.getChildAt(n - firstItem).getTop()
            Log.d("top---->", top.toString())
            rv.scrollBy(0, top)
        } else {
            rv.scrollToPosition(n)
            move = true
        }
    }


    private inner class RecyclerViewListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false
                val n = mIndex - mManager.findFirstVisibleItemPosition()
                Log.d("n---->", n.toString())
                if (0 <= n && n < rv.getChildCount()) {
                    val top = rv.getChildAt(n).getTop()
                    Log.d("top--->", top.toString())
                    rv.smoothScrollBy(0, top)
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (move) {
                move = false
                val n = mIndex - mManager.findFirstVisibleItemPosition()
                if (0 <= n && n < rv.getChildCount()) {
                    val top = rv.getChildAt(n).getTop()
                    rv.scrollBy(0, top)
                }
            }
        }
    }


}