package com.hotniao.live.fragment.search;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnRefreshDirection;
import com.hotniao.live.R;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索用户
 * 作者：Mr.Xu
 * 时间：2017/12/7
 */
public class SearchUserFrag extends CommListFragment {

    private List<String> mData = new ArrayList<>();

    @Override
    protected String setTAG() {
        EventBus.getDefault().register(this);
        return "搜索-用户";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {

            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_search_user;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
    }

    @Override
    protected RequestParams setRequestParam() {
        return null;
    }

    @Override
    protected String setRequestUrl() {
        return "";
    }

    @Override
    protected HnResponseHandler setResponseHandler(HnRefreshDirection state) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
