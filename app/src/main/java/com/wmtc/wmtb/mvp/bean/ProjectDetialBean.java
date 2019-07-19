package com.wmtc.wmtb.mvp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/3/26.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProjectDetialBean extends BaseBean {

    /**
     * data : {"detailAttaches":[{"id":317318640904962048,"businessId":317318595774251008,"type":1,"url":"uploadAttach/308624582166708224/infofb9949a5-b39f-4007-b142-da02cc5e601d.png","order":1,"remarks":null,"createBy":308624582166708224,"createTime":"2019-03-26T07:10:59.000+0000","updateBy":null,"updateTime":null,"status":1,"serviceType":5},{"id":317318641374724096,"businessId":317318595774251008,"type":1,"url":"uploadAttach/308624582166708224/infoa2b93912-c5f1-404e-9142-7722f30b37bd.png","order":2,"remarks":null,"createBy":308624582166708224,"createTime":"2019-03-26T07:11:00.000+0000","updateBy":null,"updateTime":null,"status":1,"serviceType":5}],"shopProject":{"id":317318595774251008,"pTitle":"wewwqe","shopId":308624582166708224,"pPrice":null,"pOldPrice":10,"pPriceFirst":40,"pPriceEnd":160,"pPayPercent":20,"pNums":null,"pSold":null,"pPlace":"172","banner":null,"skinS":null,"infos":null,"pPlaceValue":"头部","pEffect":"rr","pProduct":"tt","pInstrument":"rr","pProcess":"rr","pIncrement":"rr","pNeed":null,"pContent":null,"pId":242,"pIdValue":"黄金焕肤","pValueHufuxuqiu":null,"pPrpoTime":null,"pPrpoPrice":null,"remarks":null,"createBy":308624582166708224,"createTime":"2019-03-26T07:10:49.000+0000","updateBy":null,"updateTime":null,"status":2},"bannerAttaches":[{"id":317318640003186688,"businessId":317318595774251008,"type":1,"url":"banner/308624582166708224/banner17c41994-1a29-4741-8788-5a49c71a4f33.png","order":0,"remarks":null,"createBy":308624582166708224,"createTime":"2019-03-26T07:10:59.000+0000","updateBy":null,"updateTime":null,"status":1,"serviceType":4}]}
     * curson : null
     * erros : null
     */

    public DataBean data;

    public static class DataBean {
        /**
         * detailAttaches : [{"id":317318640904962048,"businessId":317318595774251008,"type":1,"url":"uploadAttach/308624582166708224/infofb9949a5-b39f-4007-b142-da02cc5e601d.png","order":1,"remarks":null,"createBy":308624582166708224,"createTime":"2019-03-26T07:10:59.000+0000","updateBy":null,"updateTime":null,"status":1,"serviceType":5},{"id":317318641374724096,"businessId":317318595774251008,"type":1,"url":"uploadAttach/308624582166708224/infoa2b93912-c5f1-404e-9142-7722f30b37bd.png","order":2,"remarks":null,"createBy":308624582166708224,"createTime":"2019-03-26T07:11:00.000+0000","updateBy":null,"updateTime":null,"status":1,"serviceType":5}]
         * shopProject : {"id":317318595774251008,"pTitle":"wewwqe","shopId":308624582166708224,"pPrice":null,"pOldPrice":10,"pPriceFirst":40,"pPriceEnd":160,"pPayPercent":20,"pNums":null,"pSold":null,"pPlace":"172","banner":null,"skinS":null,"infos":null,"pPlaceValue":"头部","pEffect":"rr","pProduct":"tt","pInstrument":"rr","pProcess":"rr","pIncrement":"rr","pNeed":null,"pContent":null,"pId":242,"pIdValue":"黄金焕肤","pValueHufuxuqiu":null,"pPrpoTime":null,"pPrpoPrice":null,"remarks":null,"createBy":308624582166708224,"createTime":"2019-03-26T07:10:49.000+0000","updateBy":null,"updateTime":null,"status":2}
         * bannerAttaches : [{"id":317318640003186688,"businessId":317318595774251008,"type":1,"url":"banner/308624582166708224/banner17c41994-1a29-4741-8788-5a49c71a4f33.png","order":0,"remarks":null,"createBy":308624582166708224,"createTime":"2019-03-26T07:10:59.000+0000","updateBy":null,"updateTime":null,"status":1,"serviceType":4}]
         */

        public ShopProjectBean shopProject;
        public List<DetailAttachesBean> detailAttaches;
        public List<BannerAttachesBean> bannerAttaches;

        public static class ShopProjectBean {
            /**
             * id : 317318595774251008
             * pTitle : wewwqe
             * shopId : 308624582166708224
             * pPrice : null
             * pOldPrice : 10
             * pPriceFirst : 40
             * pPriceEnd : 160
             * pPayPercent : 20
             * pNums : null
             * pSold : null
             * pPlace : 172
             * banner : null
             * skinS : null
             * infos : null
             * pPlaceValue : 头部
             * pEffect : rr
             * pProduct : tt
             * pInstrument : rr
             * pProcess : rr
             * pIncrement : rr
             * pNeed : null
             * pContent : null
             * pId : 242
             * pIdValue : 黄金焕肤
             * pValueHufuxuqiu : null
             * pPrpoTime : null
             * pPrpoPrice : null
             * remarks : null
             * createBy : 308624582166708224
             * createTime : 2019-03-26T07:10:49.000+0000
             * updateBy : null
             * updateTime : null
             * status : 2
             */

            public long id;
            public String pTitle;
            public long shopId;
            public int pPrice;
            public int pOldPrice;
            public int pPriceFirst;
            public int pPriceEnd;
            public int pPayPercent;
            public String pPlace;
            public String pPlaceValue;
            public String pEffect;
            public String pProduct;
            public String pInstrument;
            public String pProcess;
            public String pIncrement;
            public int pId;
            public String pIdValue;
            public String pIdRate;
            public String pPrpoTime;
            public String pValueHufuxuqiu;
            public int status;
        }

        public static class CommonPicBean implements Parcelable {
            /**
             * id : 317318640003186688
             * businessId : 317318595774251008
             * type : 1
             * url : banner/308624582166708224/banner17c41994-1a29-4741-8788-5a49c71a4f33.png
             * order : 0
             * remarks : null
             * createBy : 308624582166708224
             * createTime : 2019-03-26T07:10:59.000+0000
             * updateBy : null
             * updateTime : null
             * status : 1
             * serviceType : 4
             */

            public long id;
            public long businessId;
            public int type;
            public String url;
            public int order;
            public int status;
            public int serviceType;

            protected CommonPicBean(Parcel in) {
                id = in.readLong();
                businessId = in.readLong();
                type = in.readInt();
                url = in.readString();
                order = in.readInt();
                status = in.readInt();
                serviceType = in.readInt();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeLong(id);
                dest.writeLong(businessId);
                dest.writeInt(type);
                dest.writeString(url);
                dest.writeInt(order);
                dest.writeInt(status);
                dest.writeInt(serviceType);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<CommonPicBean> CREATOR = new Creator<CommonPicBean>() {
                @Override
                public CommonPicBean createFromParcel(Parcel in) {
                    return new CommonPicBean(in);
                }

                @Override
                public CommonPicBean[] newArray(int size) {
                    return new CommonPicBean[size];
                }
            };
        }

        public static class DetailAttachesBean implements Parcelable {
            /**
             * id : 317318640904962048
             * businessId : 317318595774251008
             * type : 1
             * url : uploadAttach/308624582166708224/infofb9949a5-b39f-4007-b142-da02cc5e601d.png
             * order : 1
             * remarks : null
             * createBy : 308624582166708224
             * createTime : 2019-03-26T07:10:59.000+0000
             * updateBy : null
             * updateTime : null
             * status : 1
             * serviceType : 5
             */

            public long id;
            public long businessId;
            public int type;
            public String url;
            public int order;
            public int status;
            public int serviceType;

            protected DetailAttachesBean(Parcel in) {
                id = in.readLong();
                businessId = in.readLong();
                type = in.readInt();
                url = in.readString();
                order = in.readInt();
                status = in.readInt();
                serviceType = in.readInt();
            }

            public static final Creator<DetailAttachesBean> CREATOR = new Creator<DetailAttachesBean>() {
                @Override
                public DetailAttachesBean createFromParcel(Parcel in) {
                    return new DetailAttachesBean(in);
                }

                @Override
                public DetailAttachesBean[] newArray(int size) {
                    return new DetailAttachesBean[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeLong(id);
                dest.writeLong(businessId);
                dest.writeInt(type);
                dest.writeString(url);
                dest.writeInt(order);
                dest.writeInt(status);
                dest.writeInt(serviceType);
            }
        }

        public static class BannerAttachesBean implements Parcelable {
            /**
             * id : 317318640003186688
             * businessId : 317318595774251008
             * type : 1
             * url : banner/308624582166708224/banner17c41994-1a29-4741-8788-5a49c71a4f33.png
             * order : 0
             * remarks : null
             * createBy : 308624582166708224
             * createTime : 2019-03-26T07:10:59.000+0000
             * updateBy : null
             * updateTime : null
             * status : 1
             * serviceType : 4
             */

            public long id;
            public long businessId;
            public int type;
            public String url;
            public int order;
            public int status;
            public int serviceType;

            protected BannerAttachesBean(Parcel in) {
                id = in.readLong();
                businessId = in.readLong();
                type = in.readInt();
                url = in.readString();
                order = in.readInt();
                status = in.readInt();
                serviceType = in.readInt();
            }

            public static final Creator<BannerAttachesBean> CREATOR = new Creator<BannerAttachesBean>() {
                @Override
                public BannerAttachesBean createFromParcel(Parcel in) {
                    return new BannerAttachesBean(in);
                }

                @Override
                public BannerAttachesBean[] newArray(int size) {
                    return new BannerAttachesBean[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeLong(id);
                dest.writeLong(businessId);
                dest.writeInt(type);
                dest.writeString(url);
                dest.writeInt(order);
                dest.writeInt(status);
                dest.writeInt(serviceType);
            }
        }
    }
}
