package com.wmtc.wmtb.mvp.model;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.BannerInfosBean;
import com.wmtc.wmtb.mvp.bean.CardInforBean;
import com.wmtc.wmtb.mvp.bean.CardNameBean;
import com.wmtc.wmtb.mvp.bean.CommentBannerBean;
import com.wmtc.wmtb.mvp.bean.DictListBean;
import com.wmtc.wmtb.mvp.bean.ForumListBean;
import com.wmtc.wmtb.mvp.bean.MsgListBean;
import com.wmtc.wmtb.mvp.bean.NewOrderBean;
import com.wmtc.wmtb.mvp.bean.OSSBean;
import com.wmtc.wmtb.mvp.bean.PoolAmountBean;
import com.wmtc.wmtb.mvp.bean.PoolListBean;
import com.wmtc.wmtb.mvp.bean.PoolRemainListBean;
import com.wmtc.wmtb.mvp.bean.ProListBean;
import com.wmtc.wmtb.mvp.bean.ProjectDetialBean;
import com.wmtc.wmtb.mvp.bean.ProjectSelListBean;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.bean.TeachBean;
import com.wmtc.wmtb.mvp.bean.TeachDetailBean;
import com.wmtc.wmtb.mvp.bean.TodayAmountBean;
import com.wmtc.wmtb.mvp.bean.VersionBean;
import com.wmtc.wmtb.mvp.bean.W7Bean;
import com.wmtc.wmtb.mvp.pojo.AfterSalePojo;
import com.wmtc.wmtb.mvp.pojo.AppointPojo;
import com.wmtc.wmtb.mvp.pojo.BannerPojo;
import com.wmtc.wmtb.mvp.pojo.BonusPoolPojo;
import com.wmtc.wmtb.mvp.pojo.CardPojo;
import com.wmtc.wmtb.mvp.pojo.DictCodePojo;
import com.wmtc.wmtb.mvp.pojo.DictPojo;
import com.wmtc.wmtb.mvp.pojo.FeedBackPojo;
import com.wmtc.wmtb.mvp.pojo.ForumPojo;
import com.wmtc.wmtb.mvp.pojo.MsgPojo;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.pojo.OrderStatusPojo;
import com.wmtc.wmtb.mvp.pojo.ProListPojo;
import com.wmtc.wmtb.mvp.pojo.ProSelPojo;
import com.wmtc.wmtb.mvp.pojo.ProjectDetailPojo;
import com.wmtc.wmtb.mvp.pojo.ProjectStatusPojo;
import com.wmtc.wmtb.mvp.pojo.SendCodePojo;
import com.wmtc.wmtb.mvp.pojo.SmsCodePojo;
import com.wmtc.wmtb.mvp.pojo.TeachInfoPojo;
import com.wmtc.wmtb.mvp.pojo.TodayAmountPojo;
import com.wmtc.wmtb.mvp.pojo.UpdateJPushPojo;
import com.wmtc.wmtb.mvp.pojo.VerLoginPojo;
import com.wmtc.wmtb.mvp.pojo.VideoVrPojo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import top.jplayer.baseprolibrary.mvp.model.BaseModel;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.mvp.model
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CommonModel extends BaseModel<WmtServer> {
    public CommonModel(Class<WmtServer> t) {
        super(t);
    }

    public Observable<ShopsBean> shopEnter(String userId) {
        return mServer.shopEnter(userId).compose(new IoMainSchedule<>());
    }

    public Observable<CommentBannerBean> getBanner(BannerPojo Pojo) {
        return mServer.getBanner(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<DictListBean> getxingqu(DictCodePojo Pojo) {
        return mServer.getDict(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> handleAfterSale(AfterSalePojo Pojo) {
        return mServer.handleAfterSale(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<VersionBean> getNewVersion() {
        return mServer.getNewVersion().compose(new IoMainSchedule<>());
    }

    public Observable<W7Bean> w7Login(String mobile, String pwd) {
        return mServer.w7Login(mobile, pwd).compose(new IoMainSchedule<>());
    }

    public Observable<ProListBean> projectList(ProListPojo pojo) {
        return mServer.projectList(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<OSSBean> ossToken() {
        return mServer.ossToken().compose(new IoMainSchedule<>());
    }

    public Observable<ProjectSelListBean> getProjectList(ProSelPojo pojo) {
        return mServer.getProjectList(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<PoolAmountBean> getBonusPaidAmount(BonusPoolPojo pojo) {
        return mServer.getBonusPaidAmount(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> updateShopUserInfo(UpdateJPushPojo pojo) {
        return mServer.updateShopUserInfo(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<PoolListBean> getBonusPoolsDetail(BonusPoolPojo pojo) {
        return mServer.getBonusPoolsDetail(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<PoolRemainListBean> getBonusPoolsRemainDetail(BonusPoolPojo pojo) {
        return mServer.getBonusPoolsRemainDetail(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<PoolAmountBean> getBonusPoolsRemainTotalMoney(BonusPoolPojo pojo) {
        return mServer.getBonusPoolsRemainTotalMoney(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> createShop(Map<String, RequestBody> body) {

        return mServer.createShop(body).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> shopBannerDelOne(String shopId, String picId) {
        return mServer.shopBannerDelOne(shopId, picId).compose(new IoMainSchedule<>());
    }

    public Observable<BannerInfosBean> shopBannerInfo(String shopId) {
        return mServer.shopBannerInfo(shopId).compose(new IoMainSchedule<>());
    }

    public Observable<DictListBean> dictList(DictPojo pojo) {
        return mServer.dictList(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<CardNameBean> dictListName(DictPojo pojo) {
        return mServer.dictListName(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> createProject(Map<String, RequestBody> body) {
        return mServer.createProject(body).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> updateProject(Map<String, RequestBody> body) {
        return mServer.updateProject(body).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> saveTechnicianAttach(Map<String, RequestBody> body) {
        return mServer.saveTechnicianAttach(body).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> appointSet(AppointPojo pojo) {
        return mServer.appointSet(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> addFeedback(FeedBackPojo pojo) {
        return mServer.addFeedback(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> updateProjectPic(Map<String, RequestBody> body) {
        return mServer.updateProjectPic(body).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> shopBannerCreate(Map<String, RequestBody> body) {
        return mServer.shopBannerCreate(body).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> updateTechnicianAttach(Map<String, RequestBody> body) {
        return mServer.updateTechnicianAttach(body).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> updateProjectStatus(ProjectStatusPojo pojo) {
        return mServer.updateProjectStatus(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> updateOrderByShop(OrderStatusPojo pojo) {
        return mServer.updateOrderByShop(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> updateTechnicianDetail(TeachInfoPojo pojo) {
        return mServer.updateTechnicianDetail(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<TodayAmountBean> getTodayAmount(TodayAmountPojo pojo) {
        return mServer.getTodayAmount(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<NewOrderBean> getOrderList(OrderPojo pojo) {
        return mServer.getOrderList(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> saveVideoOrVr(VideoVrPojo pojo) {
        return mServer.saveVideoOrVr(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<TeachBean> getTechnicianList(String shopId) {
        return mServer.getTechnicianList(shopId).compose(new IoMainSchedule<>());
    }

    public Observable<TeachDetailBean> getTechnicianDetail(String id) {
        return mServer.getTechnicianDetail(id).compose(new IoMainSchedule<>());
    }

    public Observable<ProjectDetialBean> projectDetail(ProjectDetailPojo pojo) {
        return mServer.projectDetail(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<CardInforBean> getInfor(OrderPojo pojo) {
        return mServer.getInfor(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> smsCode(SmsCodePojo pojo) {
        return mServer.smsCode(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> sendCode(SendCodePojo pojo) {
        return mServer.sendCode(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> smsVer(VerLoginPojo pojo) {
        return mServer.smsVer(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> addCard(CardPojo pojo) {
        return mServer.addCard(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<MsgListBean> getMessageList(MsgPojo pojo) {
        return mServer.getMessageList(pojo).compose(new IoMainSchedule<>());
    }

}
