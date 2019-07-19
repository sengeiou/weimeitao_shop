package com.wmtc.wmtb;

import android.os.SystemClock;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit w7Login, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        String s = "2019-03-27 08:20:00";
        try {
            String week = getWeek(s);
            String md = getMD(s);
            System.out.println(md + week);
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