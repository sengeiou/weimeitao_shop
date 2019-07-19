package com.wmtc.wmtb.util;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Obl on 2019/3/27.
 * com.wmtc.wmtb.util
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TimerDown extends CountDownTimer {

    // 传进来的TextView
    private TextView mTextView;

    public TimerDown(TextView textView, long millisInFuture) {
        // 1秒更新
        super(millisInFuture, 1000);
        mTextView = textView;
    }

    /**
     * 剩余的倒计时数值
     *
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {

        mTextView.setText(" " + getData(millisUntilFinished));
    }

    @Override
    public void onFinish() {

    }


    // 利用DateFormat类 格式化毫秒数，转变成  时：分：秒  格式
    // ******注意****** 毫秒数不能大于86400000，即24小时，
    // 此种写法仅适合小于24小时的格式化
    private String getData(long millisUntilFinished) {

        // 初始化Formatter的转换格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        String hms = dateFormat.format(millisUntilFinished);

        return hms;

    }

}
