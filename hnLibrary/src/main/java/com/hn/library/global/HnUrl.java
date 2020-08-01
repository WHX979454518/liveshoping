package com.hn.library.global;

import android.net.Uri;
import android.text.TextUtils;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：项目接口
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */

public class HnUrl extends NetConstant {

    /**
     * 搜索商品
     */
    public static final String SEARCH_GOODS = "/shop/home/searchGoods";
    /**
     * 搜索店铺
     */
    public static final String SEARCH_SHOP = "/shop/home/searchStore";
    /**
     * 省
     */
    public static final String ADDRESS_PRO = "/shop/area/getProvince";
    /**
     * 城市
     */
    public static final String ADDRESS_CITY = "/shop/area/getCity";
    /**
     * 区域
     */
    public static final String ADDRESS_ARE = "/shop/area/getDistrict";
    /**
     * 主播搜商品
     */
    public static final String SEACH_ANCHOR_GOODS = "/shop/goods/anchorList";
    /**
     * 推荐商品
     */
    public static final String GOODS_RECOMMEND = "/shop/goods/recommendShop";
    /**
     * 商品状态
     */
    public static final String GOODS_STATE = "/shop/goods/stateShop";
    /**
     * 剁手榜
     */
    public static final String CONTRI_SHOP = "/shop/ranking/anchorFans";
    /**
     * 订单
     */
    public static final String ORDER_GOODS = "/shop/Buyorder/orderList";
    /**
     * 订单-上架
     */
    public static final String ORDER_GOODS_SHOP = "/shop/storeorder/orderList";
    /**
     * 订单-搜索
     */
    public static final String ORDER_GOODS_SHOP_SEARCH = "/shop/storeorder/searchOrder";
    /**
     * 订单消息
     */
    public static final String ORDER_INFO = "/shop/Buyorder/orderProfile";
    /**
     * 收货地址
     */
    public static final String REC_ADDRESS = "/shop/address/getMyAddress";
    /**
     * 添加地址
     */
    public static final String ADDRESS_ADD = "/shop/address/add";
    /**
     * 编辑地址
     */
    public static final String ADDRESS_EDIT = "/shop/address/edit";
    /**
     * 删除地址
     */
    public static final String DELETE_ADDRESS = "/shop/address/del";
    /**
     * 订单详情
     */
    public static final String ORDER_DETAILS = "/shop/order/orderDetails";
    /**
     * 退货/款 列表
     */
    public static final String REFUN_LIST = "/shop/buyrefund/refundList";
    public static final String REFUN_LIST_SHOP = "/shop/storerefund/refundList";
    /**
     * 退货详情
     */
    public static final String REFUN_DETAILS = "/shop/buyrefund/refundDetails";
    /**
     * 退货详情-商家
     */
    public static final String REFUN_DETAILS_SHOP = "/shop/storerefund/refundDetails";
    /**
     * 退款操作
     */
    public static final String REFUN_OPERATE = "/shop/storerefund/refundHandle";
    /**
     * 商品详情
     */
    public static final String GOODS_DETAILs = "/shop/goods/goodsDetails";
    /**
     * 秒杀商品详情
     */
    public static final String SPIKE_GOODS_DETAILs = "/activities/seckill/goodsdetail";
    /**
     * 团购商品详情
     */
    public static final String GROUP_BUY_GOODS_DETAILs = "/activities/group/groupgoodsdetail";
    /**
     *待成团列表
     */
    public static final String NEED_TO_FINISH_GROUP_LIST = "/activities/group/waitlist";

    /**
     *分享开团信息
     */
    public static final String GROUP_BUY_SHARE_INFO = "/activities/group/invitegroup";
    /**
     * 评价列表
     */
    public static final String EVA_PUBLIC_LIST = "/shop/appraise/appraiseList";
    /**
     * 删除订单
     */
    public static final String ORDER_DEL = "/shop/buyorder/delOrder";
    /**
     * 取消订单
     */
    public static final String ORDER_CANCEL = "/shop/buyorder/cancelOrder";
    /**
     * 提交订单
     */
    public static final String ORDER_SUB = "/shop/order/submitOrder";
    /**
     * 提交秒杀订单
     */
    public static final String SPIKE_ORDER_SUB = "/activities/seckill/createorder";
    /**
     * 提交团购订单
     */
    public static final String GROUP_BUY_ORDER_SUB = "/activities/group/createorder";
    /**
     * 确认订单
     */
    public static final String ORDER_SURE = "/shop/buyorder/confirmOrder";
    /**
     * 发货
     */
    public static final String GOODS_SEND = "/shop/storeorder/shipments";
    /**
     * 填写退货单
     */
    public static final String REFUND_GOODS_SHOP = "/shop/buyrefund/refundCode";
    /**
     * 物流公司
     */
    public static final String REFUND_COMPANY = "/shop/logistics/getLogistics";
    /**
     * 查看评论
     */
    public static final String GOODS_EVA = "/shop/goods/getGoodsComment";
    /**
     * 收藏商品
     */
    public static final String COLLECT_GOODS = "/shop/goods/collectShop";
    /**
     * 商店认证
     */
    public static final String AUTH_STORE = "/shop/certification/add_auth";
    /**
     * 店铺类型列表
     */
    public static final String STORE_AUT_LIST = "/shop/home/getClass";
    /**
     * 用户
     */
    public static final String AUD_LIVE_GOODS = "/shop/goods/userList";
    /**
     * 店铺认证详情
     */
    public static final String AUTH_STORE_MSG = "/shop/certification/detail";
    /**
     * 购物车列表
     */
    public static final String GOODS_CAR = "/shop/cart/cartList";
    /**
     * 修改购物车
     */
    public static final String GOODS_CAR_EDIT = "/shop/cart/updateCart";
    /**
     * 添加购物车
     */
    public static final String ADD_CAR_ORD = "/shop/cart/addCart";
    /**
     * 提交购物车
     */
    public static final String SUB_CAR = "/shop/order/submitCart";
    /**
     * 退款详情
     */
    public static final String APP_BACK_DETAILS = "/shop/buyrefund/refundGoods";
    /**
     * 退款申请
     */
    public static final String APP_BACK_SUB = "/shop/buyrefund/refundApply";
    /**
     * 去支付
     */
    public static final String TO_PAY = "/shop/payment/goPay";
    /**
     * 商品规格
     */
    public static final String GOODS_ATT = "/shop/goods/getGoodsSpec";
    /**
     * 店铺详情
     */
    public static final String STORE_DETAILS = "/shop/store/storeHome";
    /**
     * 商品列表
     */
    public static final String GOODS_LIST = "/shop/store/columnGoods";
    /**
     * 收藏店铺
     */
    public static final String COLLECT_SHOP = "/shop/store/storeCollect";
    /**
     * 店铺信息
     */
    public static final String STORE_MSG = "/shop/store/storeDetails";
    /**
     * 卖家中心
     */
    public static final String SELL_CENTER = "/shop/storeorder/orderProfile";
    /**
     * 认证类型
     */
    public static final String AUTH_SORT = "/shop/certification/index";
    /**
     * 商铺编辑
     */
    public static final String Store_EDIT_MSG = "/shop/store/editStore";
    /**
     * 商铺编辑提交
     */
    public static final String Store_EDIT_SUB = "/shop/store/editStoreSave";
    /**
     * 店铺栏目
     */
    public static final String STORE_GROUP_LIST = "/shop/store/columnList";
    /**
     * 商品类型
     */
    public static final String GOODS_TYPE = "/shop/store/getCategory";
    public static final String GOODS_SPEC_ATTR = "/shop/store/attrSpecList";
    /**
     * 店铺栏目
     */
    public static final String EDIT_STORE_GROUP = "/shop/store/columnEdit";
    public static final String EDIT_GOODS_TYPE = "/shop/store/upCategory";
    /**
     * 栏目添加
     */
    public static final String ADD_STORE_GROUP = "/shop/store/columnAdd";
    public static final String ADD_GOODS_TYPE = "/shop/store/addCategory";
    public static final String ADD_GOODS_Spec_Attr = "/shop/store/addAttrSpec";
    /**
     * 商品列表
     */
    public static final String STORE_GOODS_LIST = "/shop/store/goodsList";
    /**
     * 兑换列表
     */
    public static final String EXCHANGE_COIN_LIST = "/shop/exchange/exchangeList";
    /**
     * 兑换
     */
    public static final String EXCHANGE_COIN = "/shop/exchange/startExchange";
    /**
     * 收藏商品
     */
    public static final String COLLECTION_GOODS = "/shop/collect/goodsList";
    /**
     * 删除失效商品
     */
    public static final String GOODS_CAR_DEL = "/shop/cart/delFailure";
    /**
     * 订单物流
     */
    public static final String LOG_ORDER = "/shop/logistics/orderLogistics";
    /**
     * 去发货
     */
    public static final String SEND_GOODS = "/shop/storeorder/shipments";
    /**
     * 发表评价
     */
    public static final String ANNO_ORDER = "/shop/appraise/postedAppraise";
    /**
     * 商品编辑栏目
     */
    public static final String EDIT_STORE_GROUP_GOODS = "/shop/store/goodsColumnEdit";


    /**
     * 热线电话
     */
    public static final String SERVICE_PHONE = "/activities/unit/service_phone";

    /**
     * 实名认证
     */
    public static final String AUTH_DETAILS = "/user/certification/details";
    /**
     * 店铺实名认证
     */
    public static final String CERTIFICATION_DETAIL = "/shop/certification/details";
    /**
     * 收藏列表
     */
    public static final String COLLECT_SHOP_LIST = "/shop/collect/storeList";
    /**
     * 回放详情
     */
    public static final String PLAY_BACK_DEL = "/live/playback/enter";
    /**
     * 隐私
     */
    public static final String STORE_PRIVATE = "/shop/store/setStoreData";
    /**
     * 删除商品
     */
    public static final String GOODS_DEL = "/shop/goodsmanager/delete";
    public static final String GOODS_GROUP_DEL = "/shop/store/columnDel";
    public static final String GOODS_TYOE_DEL = "/shop/store/batchDel";
    public static final String GOODS_ATTR_SPEC_DEL = "/shop/store/delAttrSpec";
    /**
     * 生成订单
     */
    public static final String ORDER_PAY = "/user/recharge/createRecharge";
    /**
     * 支付
     */
    public static final String MONEY_PAY = "/shop/payment/goRecharge";
    /**
     * 店铺 提现规则描述
     */
    public static final String RULE_SELL = "/shop/store/cashrule";
    /**
     * 订单数购物车
     */
    public static final String GOODS_CAR_NUM = "/shop/cart/cartEmpty";
    public static final String EDIT_ATTR_SPEC = "/shop/store/setAttrSpecValue";
    public static final String EDIT_ATTR_NAME = "/shop/store/upAttrSpec";
    /**
     * 兑换记录
     */
    public static final String EXCHANGE_HIS = "/shop/exchange/detailsList";
    /**
     * 订单评价
     */
    public static final String ORDER_EVA = "/shop/storeorder/getOrderComment";
    /**
     * 公告列表
     */
    public static final String OFFICE_ANNO = "/two/official/officialList";
    /**
     * 分類商品
     */
    public static final String RECOMMEND_STORE = "/two/recommend/room";
    /**
     * 首页推荐信息
     */
    public static final String INDEX_HOME = "/two/home/index";
    public static final String LIVE_RECOMMEND = "/two/preview/list";
    public static final String LIVE_RECOMMEND_TAG = "/two/preview/getMePreview";
    public static final String SET_LIVE_NOTICE = "/two/preview/addRemind";
    public static final String ADD_LIVE_NOTICE = "/two/preview/add";
    public static final String BLACK_LIST = "/two/black/list";
    public static final String SET_BLACK_DEL = "/two/black/delete";
    public static final String SET_BLACK_ADD = "/two/black/add";
    public static final String STORE_ADD_INIT = "/shop/goodsmanager/add";
    public static final String STORE_EDIT_INIT = "/shop/goodsmanager/edit";
    public static final String GOODS_SORT = "/shop/goodscategoryconfig/index";
    public static final String GOODS_SORT_OPTION = "/shop/goodscategoryconfig/specattr";
    public static final String GOODS_SERVER = "/shop/goodsmanager/promise";
    public static final String STORE_GOODS_INFO = "/shop/goodsmanager/info";
    public static final String VIS_HISTORY = "/shop/accesslog/userList";
    public static final String VIS_HISTORY_MAIN = "/shop/accesslog/index";
    public static final String IS_SIGN = "/user/signin/judge";
    public static final String SHOP_SORT = "/shop/goods/getAllType";
    public static final String SHOP_RECOMMED = "/shop/goods/goodsRecommend";
    public static final String SHOP_GOODS_LIST = "/shop/goods/goodsTypeList";
    public static final String TRACK = "/shop/goods/getMyTrack";
    public static final String TRACK_DELETE = "/shop/goods/delMyTrack";

    //首页商品的分类
    public static final String CONCENTRATION_GOODS_CATEGORY = "/activities/unit/navbar";


    //团购和秒杀模块
    public static final String SPIKE_AND_GROUPBUY = "/activities/unit/list";
    public static final String SPIKE_TIME_LIST = "/activities/seckill/list";
    public static final String SPIKE_LIST = "/activities/seckill/listgoods";
    public static final String GROUP_BUY_LIST = "/activities/group/listgoods";
    //溯源分类
    public static final String TRACE_LIST = "/shop/trace/list";
    //溯源商品列表页
    public static final String GOODSTYPELIST = "/shop/trace/goodsTypeList";
    //短视频
    public static final String LITTLE_VIDEO_LIST = "/video/video/list";
    public static final String LITTLE_VIDEO_WORD_LIST = "/video/article/list";
    public static final String LITTLE_VIDEO_DETAILS = "/video/video/detail";
    public static final String LITTLE_VIDEO_COMMENT_LIST = "/video/comment/list";
    public static final String LITTLE_VIDEO_ARTICLE_DETAILS = "/video/article/detail";
    public static final String LITTLE_VIDEO_PRAISE = "/video/praise/add";
    public static final String LITTLE_VIDEO_CANCEL_PRAISE = "/video/praise/delete";
    public static final String LITTLE_VIDEO_COMMENT = "/video/comment/add";


    //首页店铺热门推荐
    public static final String LIVE_LIST = "/shop/living/hotAnchorLiving";


    public static Uri setImageUri(String url) {
        if (TextUtils.isEmpty(url)) return null;
        if (url.contains("http")) {
            return Uri.parse(url.trim());
        }
        return Uri.parse(FILE_SERVER + url.trim());
    }

    /**
     * 主播结束直播
     */
    public static final String USER_APP_CONFIG = "/user/app/config";
    /**
     * 未读消息数
     */
    public static final String USER_CHAT_UNREAD = "/user/chat/unread";
    /**
     * 开播协议
     */
    public static final String LIVE_AGREEMENT = NetConstant.SERVER + "/user/app/liveAgreement";
    public static final String STORE_AGREEMENT = NetConstant.SERVER + "/user/app/storeAgreement";
    /**
     * 注册协议
     */
    public static final String REGISTER_AGREEMENT = NetConstant.SERVER + "/user/app/registerAgreement";


    /**
     * 用户注册第一步接口
     */
    public final static String REGISTER_FIRST_STEP = "/index/preRegister";
    /**
     * 用户注册第二部接口
     */
    public final static String REGISTER_SECOND_STEP = "/index/register";

    /**
     * 用户登录
     */
    public final static String LOGIN = "/account/login/index";
    /**
     * 用户QQ登录
     */
    public final static String LOGIN_QQ = "/account/qqoauth/index";
    /**
     * 用户WX登录
     */
    public final static String LOGIN_WX = "/account/wxoauth/index";
    /**
     * 用户SINA登录
     */
    public final static String LOGIN_SINA = "/account/wboauth/index";
    /**
     * 注册验证码
     */
    public final static String VERIFY_CODE_REGISTER = "/user/certification/register";

    /**
     * 店铺认证--认证验证码发送
     */
    public final static String CERTIFICATION_REGISTER = "/shop/certification/register";


    /**
     * 忘记密码验证码
     */
    public final static String VERIFY_CODE_FORGETPWD = "/account/verifycode/forgetPwd";
    /**
     * 获取绑定手机验证码
     */
    public final static String VERIFY_CODE_BINDPHONE = "/account/verifycode/bindPhone";
    /**
     * 获取更换手机验证码
     */
    public final static String VERIFY_CODE_CHANGE_PHONE = "/account/verifycode/changePhone";
    /**
     * 判断更换手机号码验证码(下一步)
     */
    public final static String VERIFY_CODE_JUDGECHANGE_PHONE = "/account/verifycode/judgeChangePhone";


    /**
     * 绑定手机
     */
    public final static String BIND_PHONE = "/user/bind/phone";
    /**
     * 更换绑定手机
     */
    public final static String BIND_CHANGE_PHONE = "/user/bind/changePhone";


    /**
     * 注册
     */
    public final static String REGISTER_PHONE = "/account/register/index";

    /**
     * 获取系统消息
     */
    public final static String SYSTEM_MESSAGE = "/user/chat/systemDialog";

    /**
     * 推送开始直播接口
     */
    public static final String PUSH_STARTLIVE = "/live/anchor/startLive";

    /**
     * 收礼物记录
     */
    public static final String GET_GIFT_LOG = "/user/amountrecord/giftIncreaseDot";

    /**
     * 获取送礼记录
     */
    public static final String SEND_GIFT_LOG = "/user/amountrecord/giftDecreaseCoin";
    /**
     * 购买VIP记录
     */
    public static final String BUY_VIP_RECORD = "/user/amountrecord/vipDecreaseCoin";
    /**
     * 开播收入记录
     */
    public static final String LIVE_INCOME_LOG = "/user/amountrecord/liveIncreaseDot";
    /**
     * 收益邀请详情
     */
    public static final String USER_AMOUNRECORD_INVITE_COIN = "/user/amountrecord/inviteIncreaseCoin";
    /**
     * 观看收入记录
     */
    public static final String LIVE_PAY_LOG = "/user/amountrecord/liveDecreaseCoin";

    /**
     * 获取充值记录
     */
    public static final String API_GETPAYLOGS = "/user/recharge/log";

    /**
     * 商场分类
     */
    public static final String SHOP_TYPE = "/shop/goods/getGoodsType";

    /**
     * 获取提现记录
     */
    public static final String API_GETWITHDRAWLOGS = "/user/withdraw/log";

    /**
     * 私信列表
     */
    public static final String PRIVATE_LETTER_LIST = "/user/chat/dialogList";

    /**
     * 获取用户关注列表
     */
    public static final String USER_FOCUS = "/user/follow/follows";

    /**
     * 获取用户粉丝列表
     */
    public static final String USER_FANS = "/user/follow/fans";

    /**
     * 私信详情
     */
    public static final String PRIVATELETTER_DETAIL = "/user/chat/dialog";

    /**
     * 发送私信
     */
    public static final String SEND_PRIVATELETTER = "/user/chat/send";

    /**
     * 退出登录
     */
    public static final String USER_EXIT = "/account/logout/index";

    /**
     * 用户信息保存
     */
    public static final String SAVE_USER_INFO = "/user/profile/update";
    /**
     * 建议与反馈
     */
    public static final String FEED_BACK = "/user/help/feedback";
    /**
     * 实名认证申请
     */
    public static final String CERTIFICATION_ADD = "/user/certification/add_auth";


    /**
     * 获取验证码
     */
    public static final String SENDSMS = "/index/sendSms";
    /**
     * 修改密码
     */
    public static final String MODIFYPWD = "/user/profile/updatePwd";

    /**
     * 忽略未读
     */
    public static final String IGNORE_NOTREAD = "/user/chat/ignoreUnread";

    /**
     * 忘记密码
     */
    public static final String FORGETPWD = "/account/login/resetPwd";

    /**
     * 开播提醒设置
     */
    public static final String SET_LIVE_REMIND = "/user/follow/updateRemind";
    /**
     * 实名认证结果
     */
    public static final String CERTIFICATION_CHECK = "/user/certification/check";
    /**
     * 主播获取开始直播信息
     */
    public static final String LIVE_GET_LIVE_INFO = "/live/anchor/getLiveInfo";


    /**
     * 删除私信
     */
    public static final String DESTORY_MSG = "/user/chat/deleteUserDialog";
    /**
     * 系统最近消息对话列表
     */
    public static final String CHAT_SYSTEM_DIALOG_LIST = "/user/chat/systemDialogList";

    /**
     * 自动登录
     */
    public static final String AUTO_LOGIN = "/index/autoLogin";
    /**
     * 用户信息
     */
    public static final String PROFILE = "/user/profile/index";
    /**
     * 获取账户信息
     */
    public static final String Get_Account = "/user/recharge/index";
    /**
     * 购买VIP
     */
    public static final String Buy_vip = "/user/vip/recharge";
    /**
     * 退出对话详情
     */
    public static final String Exit_Msg_Detail = "/msg/quitMessageDetail";
    /**
     * 直播分类
     */
    public static final String Live_NAVBAR = "/shop/home/getClass";
    /**
     * 首页热门和其他分类直播
     */
    public static final String Live_HOT = "/shop/home/hotAnchor";
    /**
     * 首页游戏接口
     */
    public static final String Live_Game = "/live/live/game";
    /**
     * 搜索
     */
    public static final String SEARCH_GET_RECOMMEND = "/live/search/getRecommend";
    /**
     * 搜索主播
     */
    public static final String SEARCH_ANCHOR = "/live/search/anchor";
    /**
     * 搜索用户
     */
    public static final String SEARCH_USER = "/shop/home/searchUser";
    /**
     * 搜索直播
     */
    public static final String SEARCH_LIVE = "/live/search/live";

    /**
     * 主播-结束直播
     */
    public static final String Stop_Live = "/live/anchor/endLive";
    /**
     * 我的收益-充值支付宝
     */
    public static final String Pre_Pay_ZFB = "/user/recharge/zfb";
    /**
     * 我的收益-充值微信
     */
    public static final String Pre_Pay_WX = "/user/recharge/wx";
    /**
     * 测试充值
     */
    public static final String USER_RECHARGE_TEXTPAY = "/user/recharge/testPay";
    /**
     * 关注的直播
     */
//    public static final String Follow_Live_List = "/live/live/follow";
    public static final String Follow_Live_List = "/shop/home/careAnchor";
    /**
     * 附近的直播
     */
    public static final String NEAR_Live_List = "/shop/home/nearAnchor";
    /**
     * 上下切换房间列表
     */
    public static final String LIVE_ROOM_SWITCH = "/live/live/room";
    /**
     * 会员中心
     */
    public static final String VIP_INDEX = "/user/vip/index";
    /**
     * 粉丝贡献榜
     */
    public static final String LIVE_RANK_ANCHORFANS = "/live/ranking/anchorFans";
    /**
     * 主播排行榜
     */
    public static final String LIVE_RANK_ANCHOR = "/live/ranking/anchor";
    /**
     * 用户排行榜
     */
    public static final String LIVE_RANK_USER = "/live/ranking/user";
    /**
     * 网页
     */
    public static final String Web_Url = SERVER + "/h5/index/page/";
    /**
     * 轮播图
     */
//    public static final String BANNER = "/live/live/banner";
    public static final String BANNER = "/shop/banner/index";
    /**
     * 主播回放
     */
    public static final String LIVE_PLAYBACK_ANCHOR = "/live/playback/anchor";
    /**
     * 我的邀请页面
     */
    public static final String USER_INVITE_DETAIL = "/user/invite/detail";
    /**
     * 我邀请的人
     */
    public static final String USER_INVITE_INDEX = "/user/invite/index";
    /**
     * 提现账户
     */
    public static final String USER_WITHDRAW_INDEX = "/user/withdraw/index";
    /**
     * 发送提现验证码
     */
    public static final String USER_WITHDRAW_VERCODE = "/account/verifycode/withdraw";
    /**
     * 申请提现
     */
    public static final String USER_WITHDRAW_ADD = "/user/withdraw/add";
    /**
     * 提现详情
     */
    public static final String USER_WITHDRAW_DETAIL = "/user/withdraw/detail";
    /**
     * 开播提醒
     */
    public static final String USER_FOLLOW_REMINDS = "/user/follow/reminds";
    /**
     * 我的房管
     */
    public static final String LIVE_ROOMADMIN_INDEX = "/live/roomadmin/index";
    /**
     * 搜索房管
     */
    public static final String LIVE_ROOMADMIN_SEARCH = "/live/roomadmin/search";
    /**
     * 添加房管
     */
    public static final String LIVE_ROOMADMIN_ADD = "/live/roomadmin/add";
    /**
     * 删除房管
     */
    public static final String LIVE_ROOMADMIN_DELETE = "/live/roomadmin/delete";

    /**
     * 签到
     */
    public static final String USER_SIGNIN_DETAIL = NetConstant.SERVER + "/user/signin/detail";
    /**
     * 帮助
     */
    public static final String USER_HELP_HOTQUESTION = NetConstant.SERVER + "/user/help/hotQuestion";
    /**
     * 关于我们
     */
    public static final String USER_APP_ABOUTS = "/user/app/aboutUs";
    /**
     * 关于我们详情
     */
    public static final String USER_APP_ABOUTS_DETAIL = NetConstant.SERVER + "/user/app/aboutUsDetail";
    /**
     * 主播等级
     */
    public static final String USER_LEVEL_ANCHOR = NetConstant.SERVER + "/user/level/anchor";
    /**
     * 我的等级
     */
    public static final String USER_LEVEL_USER = NetConstant.SERVER + "/user/level/index";



}
