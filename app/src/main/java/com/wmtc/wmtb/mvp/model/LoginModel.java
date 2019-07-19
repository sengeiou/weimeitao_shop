package com.wmtc.wmtb.mvp.model;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.LoginBean;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.bean.VerLoginBean;
import com.wmtc.wmtb.mvp.bean.W7Bean;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.pojo.SmsCodePojo;
import com.wmtc.wmtb.mvp.pojo.VerLoginPojo;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import top.jplayer.baseprolibrary.mvp.model.BaseModel;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.mvp.model
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LoginModel extends BaseModel<WmtServer> {
    public LoginModel(Class<WmtServer> t) {
        super(t);
    }

    public Observable<VerLoginBean> verLogin(VerLoginPojo verLoginPojo) {

        return mServer.verLogin(verLoginPojo).compose(new IoMainSchedule<>());
    }

    public Observable<LoginBean> toLogin(VerLoginPojo verLoginPojo) {
        return mServer.toLogin(verLoginPojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> smsCode(SmsCodePojo pojo) {
        return mServer.smsCode(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> smsVer(VerLoginPojo pojo) {
        return mServer.smsVer(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<LoginBean> register(VerLoginPojo pojo) {
        return mServer.register(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<LoginBean> forget(VerLoginPojo pojo) {
        return mServer.forget(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ShopsBean> shopEnter(String userId) {
        return mServer.shopEnter(userId).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> createShop(Map<String, RequestBody> body) {

        return mServer.createShop(body).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> logout(OrderPojo pojo) {
        return mServer.logout(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> avatar(Map<String, RequestBody> body) {
        return mServer.avatar(body).compose(new IoMainSchedule<>());
    }


}
