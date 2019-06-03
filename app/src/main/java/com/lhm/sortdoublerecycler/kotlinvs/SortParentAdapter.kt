package com.lhm.sortdoublerecycler.kotlinvs

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lhm.sortdoublerecycler.R
import com.lhm.sortdoublerecycler.listener.RvListener

/**
 * Created on 2019/6/3 18:19
 * <p>
 * author lhm
 * <p>
 * Description:
 * <p>
 * Remarks:
 */
class SortParentAdapterK(
    private val context: Context,
    private val list: List<String>,
    private val listener: RvListener
) :
    RecyclerView.Adapter<SortParentAdapterK.SortParentHolder>() {

    private var checkedPosition: Int = 0


    fun setCheckedPosition(checkedPosition: Int) {
        this.checkedPosition = checkedPosition
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SortParentHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sort_list, viewGroup, false)
        return SortParentHolder(view, listener)
    }

    override fun onBindViewHolder(sortParentHolder: SortParentHolder, i: Int) {
        sortParentHolder.bindHolder(list[i], i)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class SortParentHolder(private val mView: View, private val mListener: RvListener) :
        RecyclerView.ViewHolder(mView) {

        private val tvName: TextView = mView.findViewById(R.id.tv_sort) as TextView
        private val mLine: View = mView.findViewById(R.id.sort_line)

        init {
            mView.setOnClickListener { v -> mListener.onItemClick(v.id, adapterPosition) }
        }

        fun bindHolder(string: String, position: Int) {
            tvName.text = string
            if (position == checkedPosition) {
                mView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                tvName.setTextColor(ContextCompat.getColor(context, R.color.redD45949))
                tvName.textSize = 17f
                mLine.setBackgroundColor(ContextCompat.getColor(context, R.color.redD45949))
            } else {
                mView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
                tvName.setTextColor(ContextCompat.getColor(context, R.color.black))
                tvName.textSize = 14f
                mLine.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
        }

    }
}
