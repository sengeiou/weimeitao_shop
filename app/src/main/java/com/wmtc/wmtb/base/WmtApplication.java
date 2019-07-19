package com.wmtc.wmtb.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.wmtc.wmtb.BuildConfig;
import com.wmtc.wmtb.mvp.WmtServer;

import java.util.ArrayList;
import java.util.Locale;

import cat.ereza.customactivityoncrash.activity.DefaultErrorActivity;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.utils.LogUtil;

import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;

/**
 * Created by Obl on 2019/3/21.
 * com.wmtc.wmtb
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class WmtApplication extends MultiDexApplication {

    public static final String APP_ID = "wxb979d7a7af9e2850";
    public static final String ENDPOINT = "http://oss-cn-qingdao.aliyuncs.com/";
    public static final String B_JMKEY = "823eaafdf72eebfc9e81e382";
    public static final String C_JMKEY = "af81909ace48e59e14d9ca0a";
    public static String user_phone = "";
    public static String user_pwd = "";
    public static String user_avatar = "";
    public static String user_shopId = "";
    public static String url_host = "";
    public static ArrayList<CommonPresenterActivity> mActivityArrayList = new ArrayList<>();
    public static ArrayList<CommonLoginActivity> mActivityLogin = new ArrayList<>();
    public static String mJPushUdid;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        Observable.just(1).subscribeOn(Schedulers.io())
                .map(o -> {
                    LogUtil.e("----------安装Bugly---------");
                    MultiDex.install(base);
                    // 安装tinker
                    Beta.betaPatchListener = new BetaPatchListener() {
                        @Override
                        public void onPatchReceived(String patchFile) {
                            Log.e("-------", "补丁下载地址" + patchFile);
                        }

                        @Override
                        public void onDownloadReceived(long savedLength, long totalLength) {
                            String format = String.format(Locale.getDefault(), "%s %d%%",
                                    Beta.strNotificationDownloading,
                                    (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength));
                            Log.e("-------", "补丁下载" + format);
                        }

                        @Override
                        public void onDownloadSuccess(String msg) {
                            Log.e("-------", "补丁下载成功" + msg);
                        }

                        @Override
                        public void onDownloadFailure(String msg) {
                            Log.e("-------", "补丁下载失败" + msg);
                        }

                        @Override
                        public void onApplySuccess(String msg) {
                            Log.e("-------", "补丁应用成功" + msg);
                        }

                        @Override
                        public void onApplyFailure(String msg) {
                            Toast.makeText(getApplication(), "补丁应用失败", Toast.LENGTH_SHORT).show();
                            Log.e("-------", "补丁应用失败" + msg);
                        }

                        @Override
                        public void onPatchRollback() {
                            Log.e("-------", "补丁回滚");
                        }
                    };

                    Beta.installTinker(this);
                    return o;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();


    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaseInitApplication.with(this)
                .addUrl(WmtServer.KEY_W7, WmtServer.VALUE_W7)//外域api
                .retrofit()//网络请求
                .swipeBack()//侧滑返回
                .fixFileProvide()
                .zxing();//二维码

        if (BuildConfig.DEBUG) {
            url_host = "http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/";
        } else {
            url_host = "http://prod-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/";
        }

        Observable.just(1).subscribeOn(Schedulers.io())
                .map(o -> {
                    LogUtil.e("-----------初始化极光--------");
                    JMessageClient.setDebugMode(true);
                    JMessageClient.init(this);
                    JPushInterface.setDebugMode(true);
                    JPushInterface.init(this);
                    mJPushUdid = JPushInterface.getRegistrationID(this);
                    Bugly.init(this, "62582291fd", true);
                    CaocConfig.Builder.create()
                            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                            .enabled(true) //default: true
                            .showErrorDetails(BuildConfig.DEBUG) //default: true
                            .showRestartButton(true) //default: true
                            .logErrorOnRestart(false) //default: true
                            .trackActivities(true) //default: false
                            .errorActivity(DefaultErrorActivity.class)
                            .minTimeBetweenCrashesMs(2000) //default: 3000
                            .apply();
                    return o;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();


    }

}
