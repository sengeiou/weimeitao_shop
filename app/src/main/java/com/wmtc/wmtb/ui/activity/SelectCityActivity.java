package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonLoginActivity;
import com.wmtc.wmtb.base.WmtApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.mvp.model.LocalModel;
import top.jplayer.baseprolibrary.mvp.model.LocalServer;
import top.jplayer.baseprolibrary.mvp.model.bean.LocalBean;
import top.jplayer.baseprolibrary.net.retrofit.NetCallBackObserver;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/3/23.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SelectCityActivity extends CommonLoginActivity {
    private PickerUtils mPickerUtils;
    private List<PickerUtils.SelectLocalBean> localList = new ArrayList<>();
    Map<String, String> mStringMap = new HashMap<>();

    @Override

    public void initRootData(View view) {
        super.initRootData(view);
        mPickerUtils = new PickerUtils();
        mTvTitleTip.setText("选择所在城市");
        mTvSubTitleTip.setVisibility(View.INVISIBLE);
        mSelectCity.setVisibility(View.VISIBLE);
        mEditView.setVisibility(View.INVISIBLE);
        llShopTip.setVisibility(View.VISIBLE);
        int color = getResources().getColor(R.color.colorBlackGold);
        tvDoorT.setTextColor(color);
        line1.setBackgroundColor(color);
        mStringMap.put("shopUserId", BaseInitApplication.mHeardMap.get("id"));
        mStringMap.put("shopPhone", WmtApplication.user_phone);
        mSelectCity.setOnClickListener(v -> {
            mPickerUtils.initLocalPicker(this, list -> {
                localList = list;
                mStringMap.put("province", localList.get(0).name);
                mStringMap.put("city", localList.get(1).name);
                mStringMap.put("area", localList.get(2).name);
                mStringMap.put("area_code", localList.get(2).code);
                StringBuilder stringBuilder = new StringBuilder();
                for (PickerUtils.SelectLocalBean bean : list) {
                    stringBuilder.append(bean.name);
                }
                mSelectCity.setText(stringBuilder.toString());
            });
            mPickerUtils.localShow(this);
        });
        if (mBundle != null) {
            mStringMap.put("shopId", mBundle.getString("shopId"));
        }
        LogUtil.e(mStringMap);
        ArrayList<String> list = new ArrayList<>();
        list.add("北京市");
        list.add("天津市");
        list.add("上海市");
        list.add("重庆市");
        btnNext.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mSelectCity)) {
                ToastUtils.init().showInfoToast(mActivity, "请选择所在城市");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("city", mStringMap.get("city"));
            String province = mStringMap.get("province");
            if (list.contains(province)) {
                bundle.putString("city", province);
            }
            bundle.putString("json", new Gson().toJson(mStringMap));
            ActivityUtils.init().start(mActivity, SearchShopActivity.class, "", bundle);
            finish();
        });
    }
}
