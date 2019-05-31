package com.lhm.sortdoublerecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * author：lhm on 2018/4/12 11:24
 * <p>
 * email：3186834196@qq.com
 *
 * 消息适配器辅助类
 */
public abstract class DetailChildViewHolder extends RecyclerView.ViewHolder {


    public DetailChildViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setView(RightBean bean);
}
