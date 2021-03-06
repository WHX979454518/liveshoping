package com.hotniao.livelibrary.biz.tencent.pull;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：youbo
 * 类描述：推流观察者
 * 创建人：mj
 * 创建时间：2017/10/11 13:06
 * 修改人：Administrator
 * 修改时间：2017/10/11 13:06
 * 修改备注：
 * Version:  1.0.0
 */
public interface HnTXPullLiveObserver {

    /**
     * 正在开始拉流中
     */
    void  onStartPullLiveIng();

    /**
     * 拉流失败
     * @param code     错误码
     * @param type     类型：标识符
     * @param object   携带的数据
     */
    void  onPulliveFail(int code, String type, Object object);

    /**
     * 拉流成功
     * @param code     错误码
     * @param type     类型：标识符
     * @param object   携带的数据
     */
    void onPullSuccess(int code, String type, Object object);

    /**
     * 网络状态
     * @param state
     */
    void  onNetWorkState(int state);
}
