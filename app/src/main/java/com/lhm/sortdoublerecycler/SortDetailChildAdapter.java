package com.lhm.sortdoublerecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created on 2019/5/31 16:07
 * <p>
 * author lhm
 * <p>
 * Description:
 * <p>
 * Remarks:
 */
public class SortDetailChildAdapter extends RecyclerView.Adapter<DetailChildViewHolder> {

    private List<RightBean> list;
    private RvListener listener;
    private Context context;
    protected LayoutInflater mInflater;

    public SortDetailChildAdapter(Context context, List<RightBean> list, RvListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getIsTitle() == 0) {
            return 0;
        } else if (list.get(position).getIsTitle() == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    @NonNull
    @Override
    public DetailChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DetailChildViewHolder viewHolder;
        if (viewType == 0) {
            View view = mInflater.inflate(R.layout.item_img, parent, false);
            viewHolder = new SortDetailChildImgViewHolder(view, listener);
        } else if (viewType == 1) {
            View view = mInflater.inflate(R.layout.item_title, parent, false);
            viewHolder = new SortDetailChildTitleViewHolder(view, listener);
        } else {
            View view = mInflater.inflate(R.layout.item_classify_detail, parent, false);
            viewHolder = new SortDetailChildDetailViewHolder(view, listener);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull DetailChildViewHolder sortDetailChildViewHolder, int position) {
        sortDetailChildViewHolder.setView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SortDetailChildImgViewHolder extends DetailChildViewHolder {
        ImageView imageView;
        RvListener mListener;

        public SortDetailChildImgViewHolder(View itemView, RvListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_img);
            this.mListener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(), getAdapterPosition());
                }
            });
        }

        @Override
        public void setView(RightBean bean) {
            imageView.setImageResource(R.drawable.dk_network_filter_bg);


        }
    }

    class SortDetailChildTitleViewHolder extends DetailChildViewHolder {
        TextView textView;
        RvListener mListener;

        public SortDetailChildTitleViewHolder(View itemView, RvListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_title);
            this.mListener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(), getAdapterPosition());
                }
            });
        }

        @Override
        public void setView(RightBean bean) {
            textView.setText(bean.getName());
        }
    }

    class SortDetailChildDetailViewHolder extends DetailChildViewHolder {
        TextView textView;
        ImageView imageView;
        RvListener mListener;

        public SortDetailChildDetailViewHolder(View itemView, RvListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvName);
            imageView = itemView.findViewById(R.id.ivAvatar);
            this.mListener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(), getAdapterPosition());
                }
            });

        }

        @Override
        public void setView(RightBean bean) {
            textView.setText(bean.getName());
            imageView.setImageResource(R.drawable.ic_product_default_bg);

        }
    }
}
