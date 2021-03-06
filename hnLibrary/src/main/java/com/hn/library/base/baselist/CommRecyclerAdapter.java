package com.hn.library.base.baselist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 项目中使用的recyclerview版本是23.2.+的，所以它的item的根布局请使用wrap_content
 * Created by jiang on 2/19/16.
 */
public abstract class CommRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private OnItemClickListener onItemClickListener;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        if (onItemClickListener != null) {
            holder.getmConvertView().setClickable(true);
            holder.getmConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            });
        }
        onBindView(holder, holder.getAdapterPosition());
    }

    protected abstract void onBindView(BaseViewHolder holder, int position);

    /**
     * 这里通过使用layoutID作为item的viewtype
     *
     * @param position 位置
     * @return type
     */
    @Override
    public int getItemViewType(int position) {
        itemID = getLayoutID(position);
        return itemID;
    }

    private int itemID;
    protected abstract int getLayoutID(int position);


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * add-c-6 设置item占布局的比例
     *
     * @param recyclerView
     */
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    ico_return getItemViewType(position) == itemID ? 2 : 1;
//                }
//            });
//        }
//    }


}
