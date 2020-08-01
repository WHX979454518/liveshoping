package com.hn.library.base.baselist;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    protected final SparseArray<View> mViews;
    protected View mConvertView;


    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        mConvertView = itemView;
    }


    /**
     * 通过控件的Id获取对应的控件，如果没有则加入mViews，则从item根控件中查找并保存到mViews中
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    /**
     * 给对应的item中的TextView设置值
     *
     * @param resId View的id
     * @param text  对应的值
     */
    public void setTextViewText(int resId, CharSequence text) {
        TextView view = getView(resId);
        if (view instanceof TextView) {
            TextView tv =  view;
            tv.setText(text);
        } else {
            throw new ClassCastException("类型转换异常。。。");
        }

    }

    /**
     * 给对应的item中的TextView设置值
     *
     * @param resId View的id
     * @param text  对应的值
     */
    public void setTextViewText(int resId, int text) {
        TextView view = getView(resId);
        if (view instanceof TextView) {
            TextView tv =  view;
            tv.setText(text);
        } else {
            throw new ClassCastException("类型转换异常。。。");
        }

    }

    public View getmConvertView() {
        return mConvertView;
    }


}
