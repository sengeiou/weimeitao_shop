package com.wmtc.wmtb.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/3/26.
 * com.wmtc.wmtb.util
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TimeUtil {

    private Disposable mSubscribe;
    private static TimeUtil timeUtil;
    private TimeListener listener;

    private TimeUtil() {
    }

    public static TimeUtil init() {
        if (timeUtil == null) {
            timeUtil = new TimeUtil();
        }
        return timeUtil;
    }

    public TimeUtil setListener(TimeListener listener) {
        this.listener = listener;
        return this;
    }

    public interface TimeListener {
        void onListener(String time);
    }

    public void downTime(String time) {
        try {
            long preTime = DateUtils.dateToStamp(time);
            long aftTime = preTime + 1000 * 60 * 10;
            LogUtil.e(preTime);
            mSubscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .map(aLong -> {
                        long now = new Date().getTime();
                        long fixTime = aftTime - now;
                        long second = fixTime / 1000;
                        String returnTime = "";
                        if (second > 0) {
                            long m = second / 60;
                            long s = second % 60;
                            String mStr = m < 10 ? "0" + m : m + "";
                            String sStr = s < 10 ? "0" + s : s + "";
                       /*     System.out.println(String.format(Locale.CHINA,
                                    "当前剩余时间：%s:%s", mStr, sStr));*/
                            returnTime = mStr + ":" + sStr;
                        } else {
//                            LogUtil.e("订单已截止");
                            if (mSubscribe != null && !mSubscribe.isDisposed()) {
                                mSubscribe.dispose();
                            }
                        }
                        return returnTime;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(retime -> {
                        if (listener != null) {
                            listener.onListener(retime);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWeek(String time) throws Exception {
        Calendar calendar = getCalendar(time);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        if (Calendar.MONDAY == weekDay) {
            return "星期一";
        } else if (Calendar.TUESDAY == weekDay) {
            return "星期二";
        } else if (Calendar.WEDNESDAY == weekDay) {
            return "星期三";
        } else if (Calendar.THURSDAY == weekDay) {
            return "星期四";
        } else if (Calendar.FRIDAY == weekDay) {
            return "星期五";
        } else if (Calendar.SATURDAY == weekDay) {
            return "星期六";
        } else {
            return "星期日";
        }
    }

    public String getMD(String time) throws Exception {
        Calendar calendar = getCalendar(time);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String monthStr = month < 10 ? "0" + month : "" + month;
        String dayStr = day < 10 ? "0" + day : "" + day;
        return monthStr + "-" + dayStr;
    }

    private Calendar getCalendar(String s) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = simpleDateFormat.parse(s);
        calendar.setTime(date);
        return calendar;
    }

}
