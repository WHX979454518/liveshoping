package com.hotniao.live.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.CommDialog;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class HnMyUitls {
    public static boolean isMobileNo(String phoneNum) {
        String telRegex = "[1][358]\\d{9}";
        Pattern p = Pattern
                .compile(telRegex);
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    /**
     * 15位身份证号
     * 18位身份证号（前17位位数字，最后一位为字母x（支持大小写）
     * 18为身份证号（18位都是数字
     *
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        String regx = "[0-9]{17}[xX]";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return idCard.matches(regx) || idCard.matches(reg1) || idCard.matches(regex);
    }

    public static boolean isIdNum(String idNum) {

        // 中国公民身份证格式：长度为15或18位，最后一位可以为字母
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

        // 格式验证
        if (!idNumPattern.matcher(idNum).matches())
            return false;

        // 合法性验证

        int year = 0;
        int month = 0;
        int day = 0;

        if (idNum.length() == 15) {

            // 一代身份证

            System.out.println("一代身份证：" + idNum);

            // 提取身份证上的前6位以及出生年月日
            Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{2})(\\d{2})(\\d{2}).*");

            Matcher birthDateMather = birthDatePattern.matcher(idNum);

            if (birthDateMather.find()) {

                year = Integer.valueOf("19" + birthDateMather.group(1));
                month = Integer.valueOf(birthDateMather.group(2));
                day = Integer.valueOf(birthDateMather.group(3));

            }

        } else if (idNum.length() == 18) {

            // 二代身份证

            System.out.println("二代身份证：" + idNum);

            // 提取身份证上的前6位以及出生年月日
            Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");

            Matcher birthDateMather = birthDatePattern.matcher(idNum);

            if (birthDateMather.find()) {

                year = Integer.valueOf(birthDateMather.group(1));
                month = Integer.valueOf(birthDateMather.group(2));
                day = Integer.valueOf(birthDateMather.group(3));
            }

        }

        // 年份判断，100年前至今

        Calendar cal = Calendar.getInstance();

        // 当前年份
        int currentYear = cal.get(Calendar.YEAR);

        if (year <= currentYear - 100 || year > currentYear)
            return false;

        // 月份判断
        if (month < 1 || month > 12)
            return false;

        // 日期判断

        // 计算月份天数

        int dayCount = 31;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                dayCount = 31;
                break;
            case 2:
                // 2月份判断是否为闰年
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    dayCount = 29;
                    break;
                } else {
                    dayCount = 28;
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                dayCount = 30;
                break;
        }

        if (day < 1 || day > dayCount)
            return false;

        return true;
    }

    /**
     * 复制
     *
     * @param content
     * @param context
     */
    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        HnToastUtils.showToastShort("已成功复制到剪贴板");
    }

    /**
     * 判断是否有无网络
     *
     * @param context
     * @return
     */

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    /**
     * 判断是否禁用下载程序
     * @param context
     * @return
     */
    public static boolean isDownloadManagerAvailable(Context context) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED_USER
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED ) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void openDownloadManager(Context context){
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(intent);
    }




}
