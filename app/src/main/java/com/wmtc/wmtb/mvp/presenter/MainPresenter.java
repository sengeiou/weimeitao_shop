package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.MainActivity;
import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.CommentBannerBean;
import com.wmtc.wmtb.mvp.bean.NewOrderBean;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.bean.TodayAmountBean;
import com.wmtc.wmtb.mvp.bean.VersionBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.BannerPojo;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.pojo.OrderStatusPojo;
import com.wmtc.wmtb.mvp.pojo.TodayAmountPojo;
import com.wmtc.wmtb.mvp.pojo.UpdateJPushPojo;
import com.wmtc.wmtb.ui.fragment.MainFragment;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/3/26.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MainPresenter extends BasePresenter<MainFragment> {

    private final CommonModel mModel;

    public MainPresenter(MainFragment iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);

    }

    public void shopEnter() {
        mModel.shopEnter(BaseInitApplication.mHeardMap.get("id")).subscribe(new DefaultCallBackObserver<ShopsBean>(this) {

            @Override
            public void responseSuccess(ShopsBean bean) {
                mIView.shopEnter(bean);
            }

            @Override
            public void responseFail(ShopsBean bean) {

            }
        });
    }

    public void getHeaderBanner() {
        BannerPojo pojo = new BannerPojo();
        pojo.setBannerType("141");
        mModel.getBanner(pojo).subscribe(new DefaultCallBackObserver<CommentBannerBean>(this) {
            @Override
            public void responseSuccess(CommentBannerBean commentBannerBean) {
                mIView.headerBanner(commentBannerBean);
            }

            @Override
            public void responseFail(CommentBannerBean commentBannerBean) {

            }
        });
    }


    public void getNewVersion() {
        mModel.getNewVersion().subscribe(new DefaultCallBackObserver<VersionBean>(this) {

            @Override
            public void responseSuccess(VersionBean bean) {
                mIView.mMainActivity.version(bean);
            }

            @Override
            public void responseFail(VersionBean bean) {

            }
        });
    }

    public void updateShopUserInfo(String jPushId) {
        UpdateJPushPojo updateJPushPojo = new UpdateJPushPojo();
        updateJPushPojo.jigungId = jPushId;
        updateJPushPojo.shopUserId = BaseInitApplication.mHeardMap.get("id");
        mModel.updateShopUserInfo(updateJPushPojo).subscribe(new DefaultCallBackObserver<BaseBean>(this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.updatePush();
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }

    public void todayAmount() {
        TodayAmountPojo pojo = new TodayAmountPojo();
        pojo.setShopId(WmtApplication.user_shopId);
        mModel.getTodayAmount(pojo).subscribe(new DefaultCallBackObserver<TodayAmountBean>(this) {
            @Override
            public void responseSuccess(TodayAmountBean bean) {
                mIView.todayAmount(bean);
            }

            @Override
            public void responseFail(TodayAmountBean bean) {

            }
        });
    }

    public void getOrderList(OrderPojo pojo) {

        mModel.getOrderList(pojo).subscribe(new DefaultCallBackObserver<NewOrderBean>(this) {
            @Override
            public void responseSuccess(NewOrderBean bean) {
                mIView.orderList(bean, pojo.getOrderStatus());
            }

            @Override
            public void responseFail(NewOrderBean bean) {

            }
        });
    }

    public void updateOrderByShop(OrderStatusPojo pojo) {

        mModel.updateOrderByShop(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity),this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.orderStatusUp(pojo.getOrderStatus());
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }
}
