package com.lhm.sortdoublerecycler.kotlinvs

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lhm.sortdoublerecycler.R
import com.lhm.sortdoublerecycler.bean.RightBean
import com.lhm.sortdoublerecycler.javavs.DetailChildViewHolder
import com.lhm.sortdoublerecycler.listener.RvListener

/**
 * Created on 2019/6/3 18:26
 * <p>
 * author lhm
 * <p>
 * Description:
 * <p>
 * Remarks:
 */
class SortDetailChildAdapterK(
    private val context: Context,
    private val list: List<RightBean>,
    private val listener: RvListener
) :
    RecyclerView.Adapter<DetailChildViewHolder>() {
    var mInflater: LayoutInflater = LayoutInflater.from(context)


    override fun getItemViewType(position: Int): Int {
        return if (list[position].isTitle == 0) {
            0
        } else if (list[position].isTitle == 1) {
            1
        } else {
            2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailChildViewHolder {
        val viewHolder: DetailChildViewHolder
        when (viewType) {
            0 -> {
                val view = mInflater.inflate(R.layout.item_img, parent, false)
                viewHolder = SortDetailChildImgViewHolder(view, listener)
            }
            1 -> {
                val view = mInflater.inflate(R.layout.item_title, parent, false)
                viewHolder = SortDetailChildTitleViewHolder(view, listener)
            }
            else -> {
                val view = mInflater.inflate(R.layout.item_classify_detail, parent, false)
                viewHolder = SortDetailChildDetailViewHolder(view, listener)
            }
        }
        return viewHolder
    }


    override fun onBindViewHolder(sortDetailChildViewHolder: DetailChildViewHolder, position: Int) {
        sortDetailChildViewHolder.setView(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    internal inner class SortDetailChildImgViewHolder(itemView: View, var mListener: RvListener) :
        DetailChildViewHolder(itemView) {
        private var imageView: ImageView = itemView.findViewById(R.id.iv_img)

        init {
            itemView.setOnClickListener { v -> mListener.onItemClick(v.id, adapterPosition) }
        }

        override fun setView(bean: RightBean) {
            imageView.setImageResource(R.drawable.dk_network_filter_bg)


        }
    }

    internal inner class SortDetailChildTitleViewHolder(itemView: View, var mListener: RvListener) :
        DetailChildViewHolder(itemView) {
        private var textView: TextView = itemView.findViewById(R.id.tv_title)

        init {

            itemView.setOnClickListener { v -> mListener.onItemClick(v.id, adapterPosition) }
        }

        override fun setView(bean: RightBean) {
            textView.text = bean.name
        }
    }

    internal inner class SortDetailChildDetailViewHolder(itemView: View, var mListener: RvListener) :
        DetailChildViewHolder(itemView) {
        private var textView: TextView = itemView.findViewById(R.id.tvName)
        private var imageView: ImageView = itemView.findViewById(R.id.ivAvatar)

        init {

            itemView.setOnClickListener { v -> mListener.onItemClick(v.id, adapterPosition) }

        }

        override fun setView(bean: RightBean) {
            textView.text = bean.name
            imageView.setImageResource(R.drawable.ic_product_default_bg)

        }
    }
}
