package com.wmtc.wmtb.mvp;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.mvp.bean.BindDetailBean;
import com.wmtc.wmtb.mvp.bean.BindListBean;
import com.wmtc.wmtb.mvp.bean.CanNotTixianBean;
import com.wmtc.wmtb.mvp.bean.CardInforBean;
import com.wmtc.wmtb.mvp.bean.CardListBean;
import com.wmtc.wmtb.mvp.bean.CardNameBean;
import com.wmtc.wmtb.mvp.bean.CommentBannerBean;
import com.wmtc.wmtb.mvp.bean.CustomChatBean;
import com.wmtc.wmtb.mvp.bean.ForumListBean;
import com.wmtc.wmtb.mvp.bean.MsgListBean;
import com.wmtc.wmtb.mvp.bean.OSSBean;
import com.wmtc.wmtb.mvp.bean.OffLineDetailBean;
import com.wmtc.wmtb.mvp.bean.OnLineDetailBean;
import com.wmtc.wmtb.mvp.bean.PoolAmountBean;
import com.wmtc.wmtb.mvp.bean.PoolListBean;
import com.wmtc.wmtb.mvp.bean.PoolRemainListBean;
import com.wmtc.wmtb.mvp.bean.ProjectSelListBean;
import com.wmtc.wmtb.mvp.bean.ShoukuanBean;
import com.wmtc.wmtb.mvp.bean.TixianBean;
import com.wmtc.wmtb.mvp.bean.BannerInfosBean;
import com.wmtc.wmtb.mvp.bean.DictListBean;
import com.wmtc.wmtb.mvp.bean.LoginBean;
import com.wmtc.wmtb.mvp.bean.OrderDetailsBean;
import com.wmtc.wmtb.mvp.bean.ProListBean;
import com.wmtc.wmtb.mvp.bean.NewOrderBean;
import com.wmtc.wmtb.mvp.bean.OrderStateBean;
import com.wmtc.wmtb.mvp.bean.ProjectDetialBean;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.bean.TeachBean;
import com.wmtc.wmtb.mvp.bean.TeachDetailBean;
import com.wmtc.wmtb.mvp.bean.TixianMainBean;
import com.wmtc.wmtb.mvp.bean.TodayAmountBean;
import com.wmtc.wmtb.mvp.bean.VerLoginBean;
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
import com.wmtc.wmtb.mvp.pojo.OrderRecordPojo;
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
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import top.jplayer.baseprolibrary.BaseInitApplication;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.mvp
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public interface WmtServer {

    String KEY_W7 = "w7";
    String VALUE_W7 = "https://weixin.weimeitao.net/";
    String W7 = BaseInitApplication.urlHeardHost + ":" + KEY_W7;

    @Headers(W7)
    @POST("app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=account.login")
    Observable<W7Bean> w7Login(@Query("mobile") String mobile, @Query("pwd") String pwd);

    @POST("api/user/suser/shopUserVer?")
    Observable<VerLoginBean> verLogin(@Body VerLoginPojo verLoginPojo);

    @POST("api/user/getNewVersion?")
    Observable<VersionBean> getNewVersion();

    @POST("api/user/suser/shopUserLogin?")
    Observable<LoginBean> toLogin(@Body VerLoginPojo verLoginPojo);

    @POST("api/user/suser/smsver?")
    Observable<BaseBean> smsVer(@Body VerLoginPojo verLoginPojo);

    //获取肤质
    @POST("api/yuyue/getDictList?")
    Observable<DictListBean> getDict(@Body DictCodePojo dictCodePojo);

    //获取banner
    @POST("api/yuyue/getBannerList?")
    Observable<CommentBannerBean> getBanner(@Body BannerPojo pojo);

    //获取banner
    @POST("api/yuyue/handleAfterSale?")
    Observable<BaseBean> handleAfterSale(@Body AfterSalePojo pojo);

    @POST("api/yuyue/shopEnter")
    @FormUrlEncoded
    Observable<ShopsBean> shopEnter(@Field("shopUserId") String shopUserId);

    @POST("api/user/suser/register?")
    Observable<LoginBean> register(@Body VerLoginPojo verLoginPojo);

    @POST("api/user/suser/updateShopUserInfo?")
    Observable<BaseBean> updateShopUserInfo(@Body UpdateJPushPojo pojo);

    @POST("api/user/suser/forget?")
    Observable<LoginBean> forget(@Body VerLoginPojo verLoginPojo);

    @POST("api/user/suser/smsCode?")
    Observable<BaseBean> smsCode(@Body SmsCodePojo pojo);

    //发送验证码
    @POST("api/user/sendVerificationCode?")
    Observable<BaseBean> sendCode(@Body SendCodePojo pojo);

    @POST("api/yuyue/projectList?")
    Observable<ProListBean> projectList(@Body ProListPojo pojo);

    @POST("api/user/oss/createAppOssToken")
    Observable<OSSBean> ossToken();

    @POST("api/yuyue/getProjectList")
    Observable<ProjectSelListBean> getProjectList(@Body ProSelPojo pojo);


    //平台奖金总金额
    @POST("api/yuyue/getBonusPaidAmount")
    Observable<PoolAmountBean> getBonusPaidAmount(@Body BonusPoolPojo pojo);

    //平台奖金明细
    @POST("api/yuyue/getBonusPoolsDetail")
    Observable<PoolListBean> getBonusPoolsDetail(@Body BonusPoolPojo pojo);

    //平台奖金列表
    @POST("api/yuyue/getBonusPoolsRemainDetail")
    Observable<PoolRemainListBean> getBonusPoolsRemainDetail(@Body BonusPoolPojo pojo);

    //平台奖金剩余金额
    @POST("api/yuyue/getBonusPoolsRemainTotalMoney")
    Observable<PoolAmountBean> getBonusPoolsRemainTotalMoney(@Body BonusPoolPojo pojo);

    @POST("api/yuyue/getDictList?")
    Observable<DictListBean> dictList(@Body DictPojo pojo);

    @POST("api/yuyue/getDictList?")
    Observable<CardNameBean> dictListName(@Body DictPojo pojo);

    @POST("api/yuyue/projectDetail?")
    Observable<ProjectDetialBean> projectDetail(@Body ProjectDetailPojo pojo);

    @Multipart
    @POST("api/yuyue/createShop?")
    Observable<BaseBean> createShop(@PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("api/yuyue/shopBannerDelOne?")
    Observable<BaseBean> shopBannerDelOne(@Field("shopId") String shopId, @Field("picId") String picId);

    @FormUrlEncoded
    @POST("api/yuyue/shopBannerInfo?")
    Observable<BannerInfosBean> shopBannerInfo(@Field("shopId") String shopId);

    @Multipart
    @POST("api/yuyue/createProjectNew?")
    Observable<BaseBean> createProject(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("api/yuyue/updateProjectNew?")
    Observable<BaseBean> updateProject(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("api/yuyue/saveTechnicianAttach?")
    Observable<BaseBean> saveTechnicianAttach(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("api/yuyue/updateProjectPic?")
    Observable<BaseBean> updateProjectPic(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("api/yuyue/shopBannerCreateNew?")
    Observable<BaseBean> shopBannerCreate(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("api/yuyue/updateTechnicianAttach?")
    Observable<BaseBean> updateTechnicianAttach(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("api/user/suser/avatar?")
    Observable<BaseBean> avatar(@PartMap Map<String, RequestBody> map);

    @POST("api/yuyue/getNewOrderList?")
    Observable<OrderStateBean> getOrderState(@Body OrderPojo orderPojo);

    @POST("api/yuyue/appointSet?")
    Observable<BaseBean> appointSet(@Body AppointPojo pojo);

    @POST("api/yuyue/addFeedback?")
    Observable<BaseBean> addFeedback(@Body FeedBackPojo pojo);

    @POST("api/yuyue/getOrderList?")
    Observable<NewOrderBean> getOrderList(@Body OrderPojo orderPojo);

    @POST("api/yuyue/saveVideoOrVr?")
    Observable<BaseBean> saveVideoOrVr(@Body VideoVrPojo pojo);

    @FormUrlEncoded
    @POST("api/yuyue/getTechnicianList?")
    Observable<TeachBean> getTechnicianList(@Field("shopId") String shopId);

    @FormUrlEncoded
    @POST("api/yuyue/getTechnicianDetail?")
    Observable<TeachDetailBean> getTechnicianDetail(@Field("technicianId") String technicianId);

    @POST("api/yuyue/updateProjectStatus?")
    Observable<BaseBean> updateProjectStatus(@Body ProjectStatusPojo orderPojo);

    @POST("api/yuyue/updateOrderByShop?")
    Observable<BaseBean> updateOrderByShop(@Body OrderStatusPojo pojo);

    @POST("api/yuyue/updateTechnicianDetail?")
    Observable<BaseBean> updateTechnicianDetail(@Body TeachInfoPojo pojo);

    @POST("api/yuyue/getTodayAmount?")
    Observable<TodayAmountBean> getTodayAmount(@Body TodayAmountPojo pojo);

    @POST("api/yuyue/getOrderDetail?")
    Observable<OrderDetailsBean> getOrderDetails(@Body OrderPojo orderPojo);

    @POST("api/yuyue/getCustomerService?")
    Observable<CustomChatBean> getCustomerService(@Body OrderPojo orderPojo);

    @POST("api/yuyue/updateOrderByShop?")
    Observable<BaseBean> updateOrderByShop(@Body OrderPojo orderPojo);

    @POST("api/yuyue/getTixianRecord?")
    Observable<TixianBean> getTixian(@Body OrderPojo orderPojo);

    @POST("api/yuyue/getShouKuanRecord?")
    Observable<ShoukuanBean> getShouKuanRecord(@Body OrderPojo orderPojo);

    @POST("api/yuyue/getBangDingNumList?")
    Observable<BindListBean> getBangDingNumList(@Body OrderPojo orderPojo);

    @POST("api/yuyue/getBangDingList?")
    Observable<ShoukuanBean> getBangDingList(@Body OrderPojo orderPojo);

    @POST("api/yuyue/getOfflineShouKuanRecord?")
    Observable<ShoukuanBean> getOfflineShouKuanRecord(@Body OrderPojo orderPojo);

    @POST("api/yuyue/getOfflineDetail?")
    Observable<OffLineDetailBean> getOfflineDetail(@Body OrderRecordPojo orderPojo);

    @POST("api/yuyue/getShouKuanDetail?")
    Observable<OnLineDetailBean> getOnLineDetail(@Body OrderRecordPojo orderPojo);

    @POST("api/yuyue/getBangDingDetail?")
    Observable<BindDetailBean> getBangDingDetail(@Body OrderRecordPojo orderPojo);


    @POST("api/yuyue/getTixianList?")
    Observable<TixianMainBean> getTixianMain(@Body OrderPojo orderPojo);

    @POST("api/yuyue/selectCard?")
    Observable<CardListBean> getCard(@Body OrderPojo orderPojo);

    @POST("api/yuyue/getCanNotTixianAmount?")
    Observable<CanNotTixianBean> getCanNotTixianAmount(@Body OrderPojo orderPojo);

    @POST("api/yuyue/addTixianRecord?")
    Observable<BaseBean> addTixianRecord(@Body OrderPojo orderPojo);

    @POST("api/yuyue/selectShopInfo?")
    Observable<CardInforBean> getInfor(@Body OrderPojo orderPojo);

    @POST("api/yuyue/addCard?")
    Observable<BaseBean> addCard(@Body CardPojo pojo);

    @POST("api/user/logout?")
    Observable<BaseBean> logout(@Body OrderPojo pojo);

    @POST("api/yuyue/getMessageList?")
    Observable<MsgListBean> getMessageList(@Body MsgPojo pojo);
}
