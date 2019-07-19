package com.wmtc.wmtb.ui.receiver;

import android.app.LauncherActivity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.wmtc.wmtb.MainActivity;
import com.wmtc.wmtb.mvp.bean.JPushBean;
import com.wmtc.wmtb.mvp.bean.JPushParamBean;
import com.wmtc.wmtb.ui.activity.OrderDetailsActivity;
import com.wmtc.wmtb.ui.activity.SplashActivity;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/3/31.
 * com.wmtc.wmtb.ui.receiver
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class JPushReceiver extends BroadcastReceiver {

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e("------接收到通知---------");
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            LogUtil.e("JPush 用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtil.e("接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtil.e("接受到推送下来的通知");
            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtil.e("用户点击打开了通知");
            openNotification(context, bundle);

        } else {
            LogUtil.e("Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        LogUtil.e(" title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        LogUtil.e("message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtil.e("extras : " + extras);

    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue;
        try {
            if (extras != null && extras.contains("messageType")) {
                Gson gson = new Gson();
                JPushParamBean pushBean = gson.fromJson(extras, JPushParamBean.class);
                String param = pushBean.param;
                JPushBean jPushBean = gson.fromJson(param, JPushBean.class);
                LogUtil.e(jPushBean);
                if ("shopAudit".equals(jPushBean.messageType)) {
                    ActivityUtils.init().startWithTask(context, SplashActivity.class, "", null);
                } else if ("updateTechnicianOrArrivalTime".equals(jPushBean.messageType)) {
                    Bundle bundleOrder = new Bundle();
                    bundleOrder.putString("id", jPushBean.orderId);
                    ActivityUtils.init().startWithTask(context, OrderDetailsActivity.class, "", bundleOrder);
                }
            } else {
                JSONObject extrasJson = new JSONObject(extras);
                myValue = extrasJson.optString("param");
                Bundle bundleOrder = new Bundle();
                bundleOrder.putString("id", myValue);
                ActivityUtils.init().startWithTask(context, OrderDetailsActivity.class, "", bundleOrder);
            }
        } catch (Exception e) {
            LogUtil.e("Unexpected: extras is not a valid json" + e.getMessage());
            return;
        }
    }
}
