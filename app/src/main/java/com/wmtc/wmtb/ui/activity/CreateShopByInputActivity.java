package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonPresenterActivity;
import com.wmtc.wmtb.util.LocationMapUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

import static com.wmtc.wmtb.base.WmtApplication.mActivityArrayList;

/**
 * Created by Obl on 2019/3/24.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CreateShopByInputActivity extends CommonPresenterActivity {

    @BindView(R.id.viewToolBar)
    View mViewToolBar;
    @BindView(R.id.ivLoginBack)
    ImageView mIvLoginBack;
    @BindView(R.id.editName)
    EditText mEditName;
    @BindView(R.id.editPhone)
    EditText mEditPhone;
    @BindView(R.id.editCity)
    TextView mEditCity;
    @BindView(R.id.editAddress)
    TextView mEditAddress;
    @BindView(R.id.editTime)
    TextView mEditTime;
    @BindView(R.id.rbOpen)
    RadioButton mRbOpen;
    @BindView(R.id.rbClose)
    RadioButton mRbClose;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    private LocationMapUtil mMapUtil;
    private Unbinder mBind;
    private PickerUtils mPickerUtils;
    private List<PickerUtils.SelectLocalBean> localList = new ArrayList<>();
    Map<String, String> mStringMap = new HashMap<>();

    @Override
    protected int initRootLayout() {
        return R.layout.activity_create_input;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).statusBarView(R.id.viewToolBar).init();
    }

    @Override
    protected void initSaveInstanceState(@Nullable Bundle savedInstanceState) {
        super.initSaveInstanceState(savedInstanceState);
        mMapUtil.onCreate(savedInstanceState);
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mActivityArrayList.add(this);
        mBind = ButterKnife.bind(this, view);
        String json = mBundle.getString("json");
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        mStringMap = new Gson().fromJson(json, type);
        mMapUtil = new LocationMapUtil(mActivity, view, (latLng, result, poiResult) -> {
            LogUtil.e(poiResult);
            mEditAddress.setText(poiResult);
            mStringMap.put("location", latLng.longitude + "," + latLng.latitude);
        });
        mMapUtil.initData();
        mStringMap.put("shopUserId", BaseInitApplication.mHeardMap.get("id"));
        mPickerUtils = new PickerUtils();
        mEditCity.setOnClickListener(v -> {
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
                mEditCity.setText(stringBuilder.toString());
            });
            mPickerUtils.localShow(this);
        });
        mPickerUtils.initDoublePicker(mPickerUtils.getTime(), mPickerUtils.getTime2(), 48, mActivity, (start, end) -> {
            String text = start + "-" + end;
            mEditTime.setText(text);
            mStringMap.put("openTime", start);
            mStringMap.put("endTime", end);
        });
        mEditTime.setOnClickListener(v -> {
            mPickerUtils.doubleTimeShow();
        });
        mIvLoginBack.setOnClickListener(v -> finish());
        mBtnNext.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mEditName)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入门店名称");
                return;
            }
            if (StringUtils.init().isEmpty(mEditTime)) {
                ToastUtils.init().showInfoToast(mActivity, "请选择营业时间");
                return;
            }
            mStringMap.put("shopName", StringUtils.init().fixNullStr(mEditName.getText()));
            if (StringUtils.init().isEmpty(mEditPhone)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入门店电话");
                return;
            }
            mStringMap.put("shopPhone", StringUtils.init().fixNullStr(mEditPhone.getText()));
            mStringMap.put("address", StringUtils.init().fixNullStr(mEditAddress.getText()));
            mStringMap.put("shopstatus", mRbOpen.isChecked() ? "在营业" : "未营业");
            Bundle bundle = new Bundle();
            bundle.putString("json", new Gson().toJson(mStringMap));
            ActivityUtils.init().start(mActivity, CreateShopActivity.class, "", bundle);
        });
    }

    protected void onDestroy() {
        this.mMapUtil.onDestroy();
        mBind.unbind();
        super.onDestroy();
        mActivityArrayList.remove(this);
    }
}
