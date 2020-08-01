package com.live.shoplib.other;

import android.view.View;
import android.widget.TextView;

import com.hn.library.global.HnUrl;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;

/**
 * 处理指定布局注入
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
public class GoodsOrderInject {

    public static final int ORDER_NONE = -1;
    public static final int ORDER_ALL = 0;
    public static final int ORDER_PAY = 1;//待付款
    public static final int ORDER_SEND = 2;//待发货
    public static final int ORDER_GET = 3;//待收货
    public static final int ORDER_EVA = 4;//待评价
    public static final int ORDER_REFUND = 5;//退货
    public static final int ORDER_CANCEL = 6;//已取消
    public static final int ORDER_NULL = 7;//已无效

    /**
     * 用户 item_order_user_msg 条目数据设置
     */
    public static void setUserView(View rootView, String icon, String name, int state) {
        setUserMsgValue(
                (FrescoImageView) rootView.findViewById(R.id.mIvUserIco), icon,
                (TextView) rootView.findViewById(R.id.mTvUserName), name,
                (TextView) rootView.findViewById(R.id.mTvState), state);
    }

    /**
     * 用户 item_order_user_msg 条目数据设置
     */
    private static void setUserMsgValue(FrescoImageView icon, String url,
                                        TextView nameTv, String name,
                                        TextView stateTv, int state) {
        switch (state) {
            case ORDER_PAY://1
                stateTv.setText("待付款");
                break;
            case ORDER_SEND://2
                stateTv.setText("待发货");
                break;
            case ORDER_GET://3
                stateTv.setText("待收货");
                break;
            case ORDER_EVA://4
                stateTv.setText("待评价");
                break;
            case ORDER_REFUND://5
                stateTv.setText("已完成");
                break;
            case ORDER_CANCEL://6
                stateTv.setText("已取消");
                break;
            case ORDER_NULL://7
                stateTv.setText("已无效");
                break;
            default://其他
                stateTv.setText("已完成");
        }
        icon.setImageURI(HnUrl.setImageUri(HnUrl.setImageUrl(url)));
        nameTv.setText(name);
    }


    /**
     * 商品 item_order_goods_msg 条目数据设置
     */
    public static void setGoodsView(View rootView, String icon, String name, String msg, String price, String num, String state,boolean isEqu) {
        setGoodsMsgValue(
                (FrescoImageView) rootView.findViewById(R.id.mIvGoodsIco), icon,
                (TextView) rootView.findViewById(R.id.mTvGoodsName), name,
                (TextView) rootView.findViewById(R.id.mTvGoodsMsg), msg,
                (TextView) rootView.findViewById(R.id.mTvPrice), price,
                (TextView) rootView.findViewById(R.id.mTvGoodsNum), num,
                (TextView) rootView.findViewById(R.id.mTvGoodsTag), Integer.parseInt(state),isEqu);
    }

    /**
     * 商品 item_order_goods_msg 条目数据设置
     */
    private static void setGoodsMsgValue(FrescoImageView icon, String url,
                                         TextView nameTv, String name,
                                         TextView msgTv, String msg,
                                         TextView moneyTv, String price,
                                         TextView numTv, String num,
                                         TextView stateTv, int state,boolean isEqu) {
        //状态 0:正常状态 1：退款中 2 退款完成 3 退款关闭
        stateTv.setVisibility(View.VISIBLE);
        switch (state) {
            case 1:
                if(isEqu) {
                    stateTv.setText("退款中");
                }else {
                    stateTv.setText("部分退款中");
                }
                break;
            case 2:
                stateTv.setText("退款成功");
                break;
            case 3:
                stateTv.setText("退款关闭");
                break;
            default:
                stateTv.setVisibility(View.INVISIBLE);
                break;
        }
        icon.setImageURI(HnUrl.setImageUri(HnUrl.setImageUrl(url)));
        nameTv.setText(name);
        msgTv.setText(msg);
        moneyTv.setText(price);
        numTv.setText(num);
    }


    public static int calculateState(int order, int pay, int shop) {
        if (order == 0) {
            if (pay == 0) {
                return ORDER_PAY;
            } else if (pay == 1) {

            } else if (pay == 2) {
                if (shop == 0) {
                    return ORDER_SEND;
                } else if (shop == 1) {
                    return ORDER_GET;
                } else if (shop == 2) {
                    return ORDER_EVA;
                }
            }
        } else if (order == 1) {
            return ORDER_EVA;
        } else if (order == 2) {
            return ORDER_CANCEL;
        } else if (order == 3) {
            return ORDER_NULL;
        } else if (order == 4) {
            return ORDER_REFUND;
        }
        return ORDER_NONE;
    }

}
