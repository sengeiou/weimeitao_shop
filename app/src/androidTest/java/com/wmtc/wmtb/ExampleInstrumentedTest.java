package com.wmtc.wmtb;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static org.junit.Assert.*;

/**
 * Instrumented w7Login, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under w7Login.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.wmtc.wmtb", appContext.getPackageName());

        String time = "2019-03-26 16:35:10";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(time);
            long preTime = date.getTime();
            long aftTime = preTime + 1000 * 60 * 10;
            Observable.interval(0,1, TimeUnit.SECONDS).subscribe(aLong -> {
                long fixTime = aftTime - new Date().getTime();
                System.out.println(fixTime / 1000);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
