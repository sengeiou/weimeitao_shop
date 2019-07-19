package com.wmtc.wmtb.mvp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopsBean extends BaseBean {


    /**
     * data : [{"id":315200092707487744,"shopName":"龙门水都风景区","shopUserId":"1221122211","shopPhone":"155555555","level":null,"banner":null,"mainPros":null,"lastPros":null,"secondPros":null,"locationLong":"108.252233","locationLat":"22.900902","levelName":null,"average":"","status":2,"openTime":"","endTime":"","bed":"","vipBed":null,"province":"广西壮族自治区","city":"南宁市","area":"市辖区","areaCode":"370105","priceMin":null,"priceMax":null,"address":"G80广昆高速西","location":"108.252233,22.900902","createBy":316637857261289472,"createTime":"2019-03-24T10:05:49.000+0000","updateBy":316637857261289472,"updateTime":"2019-03-24T10:05:49.000+0000","attentionStatus":null,"remarks":"啥啥都没有","sendphone":"","closetime":"","shopstatus":"在营业","licensepic":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/shop_info/316525678000340992/license.png","licensename":"222222","licensestatus":"暂时无法提供","licensetype":"企业法人","licenseid":"111111","idcard":"22222","idcardpic":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/shop_info/316637857261289472/idcard.png","idcardname":"11111","idcardtime":"永久有效","attaches":[{"id":308648797481730048,"businessId":315200092707487744,"type":1,"url":"uploadAttach/308648677465915392/2.png","order":1,"remarks":null,"createBy":1,"createTime":"2019-03-02T09:00:08.000+0000","updateBy":null,"updateTime":null,"status":1,"serviceType":null}],"idcardtype":"台湾通行证"}]
     * curson : null
     * erros : null
     */

    public List<DataBean> data;

    public static class DataBean implements Parcelable {
        /**
         * id : 315200092707487744
         * shopName : 龙门水都风景区
         * shopUserId : 1221122211
         * shopPhone : 155555555
         * level : null
         * banner : null
         * mainPros : null
         * lastPros : null
         * secondPros : null
         * locationLong : 108.252233
         * locationLat : 22.900902
         * levelName : null
         * average :
         * status : 2
         * openTime :
         * endTime :
         * bed :
         * vipBed : null
         * province : 广西壮族自治区
         * city : 南宁市
         * area : 市辖区
         * areaCode : 370105
         * priceMin : null
         * priceMax : null
         * address : G80广昆高速西
         * location : 108.252233,22.900902
         * createBy : 316637857261289472
         * createTime : 2019-03-24T10:05:49.000+0000
         * updateBy : 316637857261289472
         * updateTime : 2019-03-24T10:05:49.000+0000
         * attentionStatus : null
         * remarks : 啥啥都没有
         * sendphone :
         * closetime :
         * shopstatus : 在营业
         * licensepic : http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/shop_info/316525678000340992/license.png
         * licensename : 222222
         * licensestatus : 暂时无法提供
         * licensetype : 企业法人
         * licenseid : 111111
         * idcard : 22222
         * idcardpic : http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/shop_info/316637857261289472/idcard.png
         * idcardname : 11111
         * idcardtime : 永久有效
         * attaches : [{"id":308648797481730048,"businessId":315200092707487744,"type":1,"url":"uploadAttach/308648677465915392/2.png","order":1,"remarks":null,"createBy":1,"createTime":"2019-03-02T09:00:08.000+0000","updateBy":null,"updateTime":null,"status":1,"serviceType":null}]
         * idcardtype : 台湾通行证
         */

        public long id;
        public String shopName;
        public int level;
        public String levelName;
        public String levelUrl;
        public String levelDateStr;
        public String shopUserId;
        public String shopPhone;
        public String locationLong;
        public String locationLat;
        public String average;
        public int status;
        public String openTime;
        public String endTime;
        public String bed;
        public String vipBed;
        public String province;
        public String city;
        public String area;
        public String areaCode;
        public String priceMin;
        public String priceMax;
        public String address;
        public String location;
        public String remarks;
        public String sendphone;
        public String closetime;
        public String shopstatus;
        public String licensepic;
        public String licensename;
        public String licensestatus;
        public String licensetype;
        public String licenseid;
        public String idcard;
        public String idcardpic;
        public String idcardname;
        public String idcardtime;
        public String idcardtype;
        public int teachNum;
        public String userAvatar;
        public String videoUrl;
        public String vrUrl;
        public List<AttachesBean> attaches;

        protected DataBean(Parcel in) {
            id = in.readLong();
            shopName = in.readString();
            shopUserId = in.readString();
            shopPhone = in.readString();
            locationLong = in.readString();
            locationLat = in.readString();
            average = in.readString();
            status = in.readInt();
            openTime = in.readString();
            endTime = in.readString();
            bed = in.readString();
            vipBed = in.readString();
            province = in.readString();
            city = in.readString();
            area = in.readString();
            areaCode = in.readString();
            priceMin = in.readString();
            priceMax = in.readString();
            address = in.readString();
            location = in.readString();
            remarks = in.readString();
            sendphone = in.readString();
            closetime = in.readString();
            shopstatus = in.readString();
            licensepic = in.readString();
            licensename = in.readString();
            licensestatus = in.readString();
            licensetype = in.readString();
            licenseid = in.readString();
            idcard = in.readString();
            idcardpic = in.readString();
            idcardname = in.readString();
            idcardtime = in.readString();
            idcardtype = in.readString();
            videoUrl = in.readString();
            vrUrl = in.readString();
            attaches = in.createTypedArrayList(AttachesBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(id);
            dest.writeString(shopName);
            dest.writeString(shopUserId);
            dest.writeString(shopPhone);
            dest.writeString(locationLong);
            dest.writeString(locationLat);
            dest.writeString(average);
            dest.writeInt(status);
            dest.writeString(openTime);
            dest.writeString(endTime);
            dest.writeString(bed);
            dest.writeString(vipBed);
            dest.writeString(province);
            dest.writeString(city);
            dest.writeString(area);
            dest.writeString(areaCode);
            dest.writeString(priceMin);
            dest.writeString(priceMax);
            dest.writeString(address);
            dest.writeString(location);
            dest.writeString(remarks);
            dest.writeString(sendphone);
            dest.writeString(closetime);
            dest.writeString(shopstatus);
            dest.writeString(licensepic);
            dest.writeString(licensename);
            dest.writeString(licensestatus);
            dest.writeString(licensetype);
            dest.writeString(licenseid);
            dest.writeString(idcard);
            dest.writeString(idcardpic);
            dest.writeString(idcardname);
            dest.writeString(idcardtime);
            dest.writeString(idcardtype);
            dest.writeString(videoUrl);
            dest.writeString(vrUrl);
            dest.writeTypedList(attaches);
        }

        public static class AttachesBean implements Parcelable {
            /**
             * id : 308648797481730048
             * businessId : 315200092707487744
             * type : 1
             * url : uploadAttach/308648677465915392/2.png
             * order : 1
             * remarks : null
             * createBy : 1
             * createTime : 2019-03-02T09:00:08.000+0000
             * updateBy : null
             * updateTime : null
             * status : 1
             * serviceType : null
             */

            public long id;
            public long businessId;
            public int type;
            public String url;

            protected AttachesBean(Parcel in) {
                id = in.readLong();
                businessId = in.readLong();
                type = in.readInt();
                url = in.readString();
            }

            public static final Creator<AttachesBean> CREATOR = new Creator<AttachesBean>() {
                @Override
                public AttachesBean createFromParcel(Parcel in) {
                    return new AttachesBean(in);
                }

                @Override
                public AttachesBean[] newArray(int size) {
                    return new AttachesBean[size];
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
            }
        }
    }
}
