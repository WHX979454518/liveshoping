package com.live.shoplib.ui.frag;

import com.hn.library.base.BaseFragment;
import com.live.shoplib.widget.scollorlayout.ScrollableHelper;
import com.reslibrarytwo.CommListScrollFragment;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：主页面
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public abstract class BaseScollFragment extends CommListScrollFragment implements ScrollableHelper.ScrollableContainer  {

    public abstract void pullToRefresh();
    public abstract void refreshComplete();
}
