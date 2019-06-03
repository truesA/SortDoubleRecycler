package com.lhm.sortdoublerecycler

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.gson.Gson
import com.lhm.sortdoublerecycler.javavs.ItemHeaderDecoration
import com.lhm.sortdoublerecycler.bean.SortBean
import com.lhm.sortdoublerecycler.javavs.SortDetailFragment
import com.lhm.sortdoublerecycler.javavs.SortParentAdapter
import com.lhm.sortdoublerecycler.listener.CheckListener
import com.lhm.sortdoublerecycler.listener.RvListener
import java.io.IOException
import java.util.ArrayList

class MainActivity : AppCompatActivity() , CheckListener {
    private var rvSort: RecyclerView? = null
    private var mSortAdapter: SortParentAdapter? = null
    private var mSortDetailFragment: SortDetailFragment? = null
    private var mContext: Context? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var targetPosition: Int = 0//点击左边某一个具体的item的位置
    private var isMoved: Boolean = false
    private var mSortBean: SortBean? = null


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
    }

     override fun onResume() {
        super.onResume()
        initView()
        initData()
    }

    private fun initData() {
        //获取asset目录下的资源文件
        val assetsData = getAssetsData("sort.json")
        val gson = Gson()
        mSortBean = gson.fromJson<SortBean>(assetsData, SortBean::class.java)
        val categoryOneArray = mSortBean!!.categoryOneArray
        val list = ArrayList<String>()
        //初始化左侧列表数据
        for (i in categoryOneArray.indices) {
            list.add(categoryOneArray[i].name)
        }
        mSortAdapter = SortParentAdapter(mContext, list,
            RvListener { id, position ->
                if (mSortDetailFragment != null) {
                    isMoved = true
                    targetPosition = position
                    setChecked(position, true)
                }
            })
        rvSort!!.adapter = mSortAdapter
        createFragment()
    }

    //从资源文件中获取分类json
    private fun getAssetsData(path: String): String {
        var result = ""
        try {
            //获取输入流
            val mAssets = getAssets().open(path)
            //获取文件的字节数
            val lenght = mAssets.available()
            //创建byte数组
            val buffer = ByteArray(lenght)
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer)
            mAssets.close()
            result = String(buffer)
            return result
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("fuck", e.message)
            return result
        }

    }


    fun createFragment() {
        val fragmentTransaction = getSupportFragmentManager().beginTransaction()
        mSortDetailFragment = SortDetailFragment()
        val bundle = Bundle()
        bundle.putParcelableArrayList("right", mSortBean!!.categoryOneArray)
        mSortDetailFragment!!.setArguments(bundle)
        mSortDetailFragment!!.setListener(this)
        fragmentTransaction.add(R.id.lin_fragment, mSortDetailFragment!!)
        fragmentTransaction.commit()
    }

    private fun setChecked(position: Int, isLeft: Boolean) {
        Log.d("p-------->", position.toString())
        if (isLeft) {
            mSortAdapter!!.setCheckedPosition(position)
            //此处的位置需要根据每个分类的集合来进行计算
            var count = 0
            for (i in 0 until position) {
                count += mSortBean!!.categoryOneArray[i].categoryTwoArray.size
            }
            count += position
            //            mSortDetailFragment.setData(count);
            if (count == 0) {
                mSortDetailFragment!!.setData(count)

            } else {
                mSortDetailFragment!!.setData(count + 1)

            }
            //            ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition));//凡是点击左边，将左边点击的位置作为当前的tag
            ItemHeaderDecoration.setCurrentTag(targetPosition.toString())//凡是点击左边，将左边点击的位置作为当前的tag
        } else {
            if (isMoved) {
                isMoved = false
            } else
                mSortAdapter!!.setCheckedPosition(position)
            ItemHeaderDecoration.setCurrentTag(position.toString())//如果是滑动右边联动左边，则按照右边传过来的位置作为tag
            //            ItemHeaderDecoration.setCurrentTag(String.valueOf(position));//如果是滑动右边联动左边，则按照右边传过来的位置作为tag

        }
        moveToCenter(position)

    }

    //将当前选中的item居中
    private fun moveToCenter(position: Int) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        val childAt = rvSort!!.getChildAt(position - mLinearLayoutManager!!.findFirstVisibleItemPosition())
        if (childAt != null) {
            val y = childAt.top - rvSort!!.height / 2
            rvSort!!.smoothScrollBy(0, y)
        }

    }


    private fun initView() {
        rvSort = findViewById(R.id.rv_sort) as RecyclerView
        mLinearLayoutManager = LinearLayoutManager(mContext)
        rvSort!!.layoutManager = mLinearLayoutManager
//        val decoration = DividerItemDecoration(mContext!!, DividerItemDecoration.VERTICAL)
//        rvSort!!.addItemDecoration(decoration)

    }


    override fun check(position: Int, isScroll: Boolean) {
        setChecked(position, isScroll)

    }
}