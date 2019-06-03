package com.lhm.sortdoublerecycler.javavs;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lhm.sortdoublerecycler.R;
import com.lhm.sortdoublerecycler.listener.RvListener;

import java.util.List;

public class SortParentAdapter extends RecyclerView.Adapter<SortParentAdapter.SortParentHolder> {

    private int checkedPosition;
    private Context context;
    private List<String> list;
    private RvListener listener;


    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    public SortParentAdapter(Context context, List<String> list, RvListener listener) {
        this.context=context;
        this.list=list;
        this.listener=listener;
    }

    @NonNull
    @Override
    public SortParentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sort_list, viewGroup, false);
        return new SortParentHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SortParentHolder sortParentHolder, int i) {
        sortParentHolder.bindHolder(list.get(i),i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


     class SortParentHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private View mView;
         private RvListener mListener;

        SortParentHolder(View itemView,RvListener listener) {
            super(itemView);
            this.mView = itemView;
            this.mListener=listener;
            tvName = (TextView) itemView.findViewById(R.id.tv_sort);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(), getAdapterPosition());
                }
            });
        }

        public void bindHolder(String string, int position) {
            tvName.setText(string);
            if (position == checkedPosition) {
                mView.setBackgroundColor(Color.parseColor("#f3f3f3"));
                tvName.setTextColor(Color.parseColor("#0068cf"));
            } else {
                mView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tvName.setTextColor(Color.parseColor("#1e1d1d"));
            }
        }

    }
}
