package com.live.shoplib.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.manager.HnAppManager;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.bean.FinishExceptMainActivityEvent;
import com.live.shoplib.bean.NeedToFinishGroupModel;
import com.live.shoplib.bean.ShareGroupBuyModel;
import com.live.shoplib.ui.dialog.ShareDialog;
import com.live.shoplib.utils.CountDownView;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 支付结果
 * 作者：Mr.Xu
 * 时间：2017/12/28
 */
@Route(path = "/shoplib/GroupBuyDetailsAct")
public class GroupBuyDetailsAct extends BaseActivity {

    private FrescoImageView goods_img;
    private FrescoImageView start_group_person_head;
    private TextView goods_name;
    private TextView group_price;
    private TextView group_old_price;
    private TextView start_group_info;
    private TextView return_index_page;
    private TextView invite_friend;
    private RecyclerView add_group_person_listview;
    private ShareDialog dialog;


    private String orderId = "";
    private String groupBuyOrderId = "";

    private CountDownView countDown;

    @Override
    public int getContentViewId() {
        return R.layout.act_group_buy_details;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("付款结果");
        setShowBack(true);
        goods_img = findViewById(R.id.goods_img);
        start_group_person_head = findViewById(R.id.start_group_person_head);
        goods_name = findViewById(R.id.goods_name);
        group_price = findViewById(R.id.group_price);
        group_old_price = findViewById(R.id.group_old_price);
        start_group_info = findViewById(R.id.start_group_info);
        return_index_page = findViewById(R.id.return_index_page);
        invite_friend = findViewById(R.id.invite_friend);
        add_group_person_listview = findViewById(R.id.add_group_person_listview);
        //倒计时
        countDown = findViewById(R.id.count_down);
        add_group_person_listview.setLayoutManager(new LinearLayoutManager(this));
        initViews();
    }

    private void initViews() {

        return_index_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new FinishExceptMainActivityEvent());
                finish();
            }
        });
    }

    @Override
    public void getInitData() {
        orderId = getIntent().getStringExtra("orderId");
        groupBuyOrderId = getIntent().getStringExtra("groupBuyOrderId");
        RequestParams param = new RequestParams();
        param.put("order_id", orderId);
        param.put("group_order_id", groupBuyOrderId);
        HnHttpUtils.postRequest(HnUrl.GROUP_BUY_SHARE_INFO, param, "获取开团详情", new HnResponseHandler<ShareGroupBuyModel>(ShareGroupBuyModel.class) {
            @SuppressLint("StringFormatMatches")
            @Override
            public void hnSuccess(String response) {
                goods_img.setImageURI(Uri.parse(model.getD().getGroup_goods_info().getGoods_img()));
                if (model.getD().getUserinfo() != null && model.getD().getUserinfo().size() > 0) {
                    start_group_person_head.setImageURI(Uri.parse(model.getD().getUserinfo().get(0).getUser_avatar()));
                }
                start_group_info.setText(String.format(getResources().getString(R.string.need_to_finish_group_tips), String.valueOf(model.getD().getLast_num()), HnDateUtils.getFinishTime(HnDateUtils.getToday24TimeStamp())));
                //倒计时
                countdown(HnDateUtils.getToday24TimeStamp());
                goods_name.setText(model.getD().getGroup_goods_info().getGoods_name());
                group_price.setText(model.getD().getGroup_goods_info().getGroup_price());
                group_old_price.setText(model.getD().getGroup_goods_info().getGoods_price());
                invite_friend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo 分享操作待完成
                        dialog = new ShareDialog(ShareDialog.Type.Goods, getSupportFragmentManager())
                                .setGoodsShare(model.getD().getGroup_goods_info().getGoods_name(),
                                        model.getD().getGroup_goods_info().getGoods_img(),
                                        model.getD().getInvite_url())
                                .show();
                    }
                });

                add_group_person_listview.setAdapter(
                        new CommRecyclerAdapter() {
                            @Override
                            protected void onBindView(BaseViewHolder holder, final int position) {
                                ((FrescoImageView) holder.getView(R.id.start_group_person_head)).setImageURI(Uri.parse(model.getD().getUserinfo().get(position).getUser_avatar()));
                                ((TextView) holder.getView(R.id.start_group_person_name)).setText(model.getD().getUserinfo().get(position).getUser_nickname());
                                if (position == 0) {
                                    ((TextView) holder.getView(R.id.group_person_add_info)).setText(HnDateUtils.getDateTime(model.getD().getStart_time()) + "开团");
                                } else {
                                    ((TextView) holder.getView(R.id.group_person_add_info)).setText("");
                                }
                            }

                            @Override

                            protected int getLayoutID(int position) {
                                return R.layout.item_start_group_person;
                            }

                            @Override
                            public int getItemCount() {
                                return model.getD().getUserinfo().size();
                            }
                        }
                );
            }


            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void countdown(long time) {
        // 获取当前时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = formatter.format(currentTime);
        // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
        String date2 = formatData("yyyy-MM-dd HH:mm:ss", time);
        // 计算的时间差
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
            change((int) diff/1000);
            countDown.initTime(h, d, s);
            countDown.start();
        } catch (Exception e) {
        }


    }

    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }
    int h = 0;
    int d = 0;
    int s = 0;
    public void change(int second) {
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
    }
}
