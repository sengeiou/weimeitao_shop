package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.amap.api.services.geocoder.RegeocodeResult;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.event.LocalSelEvent;
import com.wmtc.wmtb.util.LocationMapUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MapLocalActivity extends CommonWmtActivity {
    private LocationMapUtil mMapUtil;

    @Override
    public int initAddLayout() {
        return R.layout.activity_map_local;
    }

    @Override
    protected void initSaveInstanceState(@Nullable Bundle savedInstanceState) {
        super.initSaveInstanceState(savedInstanceState);
        mMapUtil.onCreate(savedInstanceState);
    }

    public Map<String, String> mStringMap;

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mStringMap = new HashMap<>();
        mMapUtil = new LocationMapUtil(mActivity, rootView, (latLng, result, poiResult) -> {
            LogUtil.e(poiResult);
            mStringMap.put("location", latLng.longitude + "," + latLng.latitude);
            mStringMap.put("address", poiResult);
            mStringMap.put("area", result.getRegeocodeAddress().getDistrict());
            mStringMap.put("areaCode", result.getRegeocodeAddress().getAdCode());
            mStringMap.put("city", result.getRegeocodeAddress().getCity());
            mStringMap.put("province", result.getRegeocodeAddress().getProvince());
        });
        mMapUtil.setTipShow(View.VISIBLE);
        mMapUtil.initData();

    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        EventBus.getDefault().post(new LocalSelEvent(mStringMap));
        finish();
    }

    protected void onDestroy() {
        this.mMapUtil.onDestroy();
        super.onDestroy();
    }
}
