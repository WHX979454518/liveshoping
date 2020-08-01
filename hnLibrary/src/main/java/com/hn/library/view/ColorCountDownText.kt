package com.hn.library.view

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.hn.library.R
import com.hn.library.utils.HnDateUtils
import kotlinx.android.synthetic.main.count_down_text.view.*

/**
 * Created by Alan on 2019/6/25.
 */
class ColorCountDownText : LinearLayout {

    private var countTimer: CountDownTimer? = null

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.count_down_text, this)
    }

    fun setTextColor(colorId: Int) {
        hour_text.setTextColor(resources.getColor(colorId))
        minute_text.setTextColor(resources.getColor(colorId))
        second_text.setTextColor(resources.getColor(colorId))
    }

    fun setIsBlackStyle(isBlack: Boolean) {
        if (isBlack) {
            setSplitTextColor(R.color.spike_unselect)
            setTextBkg(R.drawable.red_count_down_text_bkg)
        } else {
            setSplitTextColor(R.color.main_color)
            setTextBkg(R.drawable.black_count_down_text_bkg)
        }
    }


    private fun setSplitTextColor(colorId: Int) {
        one_split.setTextColor(resources.getColor(colorId))
        two_split.setTextColor(resources.getColor(colorId))
    }

    private fun setTextBkg(drawableIdId: Int) {
        hour_text.background = resources.getDrawable(drawableIdId)
        minute_text.background = resources.getDrawable(drawableIdId)
        second_text.background = resources.getDrawable(drawableIdId)
    }


    fun setCountTime(countTime: Long) {
        countTimer?.cancel()
        countTimer = MyCountDownTimer(countTime, 1000, arrayOf(hour_text, minute_text, second_text))
        countTimer?.start()
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        countTimer?.cancel()
    }


    class MyCountDownTimer
    /**
     *
     * @param millisInFuture   剩余时间  毫秒
     * @param countDownInterval 间隔时间  毫秒
     * @param mTvTimes   控件
     */
    (millisInFuture: Long, countDownInterval: Long, private val mTvTimes: Array<TextView>) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            mTvTimes[0].text = "00"
            mTvTimes[1].text = "00"
            mTvTimes[2].text = "00"
        }

        override fun onTick(millisUntilFinished: Long) {
            val texts = HnDateUtils.getHourMinuteSecond(millisUntilFinished / 1000)
            mTvTimes[0].text = texts[0].toString()
            mTvTimes[1].text = texts[1].toString()
            mTvTimes[2].text = texts[2].toString()
        }

    }

}