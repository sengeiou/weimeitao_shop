package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmtb.MainActivity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonLoginActivity;
import com.wmtc.wmtb.base.CommonPresenterActivity;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.event.CreateShopEvent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.AndroidBug5497Workaround;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

import static com.wmtc.wmtb.base.WmtApplication.mActivityArrayList;
import static com.wmtc.wmtb.base.WmtApplication.mActivityLogin;

/**
 * Created by Obl on 2019/3/23.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CreateShopActivity extends CommonPresenterActivity {
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.ivLoginBack)
    ImageView mIvLoginBack;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvLocal)
    TextView mTvLocal;
    @BindView(R.id.rbOk)
    RadioButton mRbOk;
    @BindView(R.id.rbNot)
    RadioButton mRbNot;
    @BindView(R.id.rbCompany)
    RadioButton mRbCompany;
    @BindView(R.id.rbOne)
    RadioButton mRbOne;
    @BindView(R.id.editLicenseId)
    EditText mEditLicenseId;
    @BindView(R.id.tvPushLicenseName)
    EditText mTvPushLicenseName;
    @BindView(R.id.tvPushLicensePic)
    TextView mTvPushLicensePic;
    @BindView(R.id.tvSelectType)
    TextView mTvSelectType;
    @BindView(R.id.editName)
    EditText mEditName;
    @BindView(R.id.editIdNum)
    EditText mEditIdNum;
    @BindView(R.id.rbTimeEnd)
    RadioButton mRbTimeEnd;
    @BindView(R.id.rbTimeAll)
    RadioButton mRbTimeAll;
    @BindView(R.id.tvPushIdPic)
    TextView mTvPushIdPic;
    @BindView(R.id.tvToReSelect)
    TextView tvToReSelect;
    @BindView(R.id.rbReadIt)
    RadioButton mRbReadIt;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    @BindView(R.id.clTipType)
    ConstraintLayout clTipType;
    private Unbinder mBind;
    private Map<String, String> mStringMap;
    private PickerUtils mPickerUtils;
    File mFile;
    private DialogLoading mLoading;
    public boolean isZiped = true;
    private ArrayList<File> mArrayList;
    private volatile boolean clickLicense = true;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_create_shop;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        AndroidBug5497Workaround.assistActivity(this);
        isCheckKeyboard = false;
        mActivityArrayList.add(this);
        mPickerUtils = new PickerUtils();
        mArrayList = new ArrayList<>();
        mBind = ButterKnife.bind(this, view);
        String json = mBundle.getString("json");
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        mStringMap = new Gson().fromJson(json, type);
        if (mStringMap != null) {
            mTvName.setText(mStringMap.get("shopName"));
            mTvLocal.setText(mStringMap.get("address"));
        }
        tvToReSelect.setOnClickListener(v -> {
            finish();
        });
        mRbNot.setOnCheckedChangeListener((buttonView, isChecked) -> {
            clTipType.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        });
        mIvLoginBack.setOnClickListener(v -> finish());
        ArrayList<String> optionsItems = new ArrayList<>();
        optionsItems.add("居民身份证");
        optionsItems.add("护照");
        optionsItems.add("港澳通行证");
        optionsItems.add("台湾通行证");
        mPickerUtils.initStringPicker(optionsItems, 0, mActivity, (position, string) -> {
            mTvSelectType.setText(string);
        });
        mTvSelectType.setOnClickListener(v -> {
            mPickerUtils.stringShow();
        });
        mPickerUtils.initTimePicker(mActivity, (date, patternDate) -> {
            mStringMap.put("idcardtime", patternDate);
            LogUtil.e(patternDate);
        });
        mRbTimeEnd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mPickerUtils.timeShow();
            }
        });
        mTvPushLicensePic.setOnClickListener(v -> {
            clickLicense = true;
            openCamera();
        });
        mTvPushIdPic.setOnClickListener(v -> {
            clickLicense = false;
            openCamera();
        });
        mBtnNext.setOnClickListener(v -> {

            if (mRbOk.isChecked()) {
                if (StringUtils.init().isEmpty(mTvPushLicenseName)) {
                    ToastUtils.init().showInfoToast(mActivity, "请输入营业执照名称");
                    return;
                }
                if (StringUtils.init().isEmpty(mEditLicenseId)) {
                    ToastUtils.init().showInfoToast(mActivity, "请输入营业执照号");
                    return;
                }
                mStringMap.put("licensestatus", "已办理好");
                mStringMap.put("licensetype", mRbCompany.isChecked() ? "企业法人" : "个人工商户");
                mStringMap.put("licensename", StringUtils.init().fixNullStr(mTvPushLicenseName.getText()));
                mStringMap.put("licenseid", StringUtils.init().fixNullStr(mEditLicenseId.getText()));
            } else {
                mStringMap.put("licensestatus", "暂时无法提供");
            }

            if (StringUtils.init().isEmpty(mEditIdNum)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入证件号");
                return;
            }
            if (StringUtils.init().isEmpty(mTvSelectType)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入证件类型");
                return;
            }
            if (StringUtils.init().isEmpty(mEditName)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入证件名称");
                return;
            }

            mStringMap.put("idcard", StringUtils.init().fixNullStr(mEditIdNum.getText()));
            mStringMap.put("idcardtype", StringUtils.init().fixNullStr(mTvSelectType.getText()));
            mStringMap.put("idcardname", StringUtils.init().fixNullStr(mEditName.getText()));
            if (mRbTimeAll.isChecked()) {
                mStringMap.put("idcardtime", "永久有效");
            }
            LogUtil.e(mStringMap);
            if (!mTvPushLicensePic.getText().equals("已上传")) {
                ToastUtils.init().showInfoToast(mActivity, "请上传营业执照");
                return;
            }
            if (!mTvPushIdPic.getText().equals("已上传")) {
                ToastUtils.init().showInfoToast(mActivity, "请上传证件照");
                return;
            }
            if (!mRbReadIt.isChecked()) {
                ToastUtils.init().showInfoToast(mActivity, "请阅读并同意协议");
                return;
            }
            mCommonPresenter.createShop(mArrayList, mStringMap);
        });
    }

    private void openCamera() {
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                .onGranted(permissions -> CameraUtil.getInstance().openSingalCamerNoCrop(this.mActivity))
                .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                .start();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (mLoading == null) {
                mLoading = new DialogLoading(mActivity);
            }
            mLoading.show();
            isZiped = false;
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Observable.just(pathList).subscribeOn(Schedulers.io()).map(strings -> {
                LogUtil.e(clickLicense);
                Observable.fromIterable(strings).subscribe(s -> {
                    String fileName = clickLicense ? "license" : "idcard";
                    LogUtil.e(fileName);
                    File file = BitmapUtil.compressImage(new File(s), fileName);
                    LogUtil.e(file.getName());
                    mArrayList.add(file);
                });
                return mArrayList;
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(files -> {

                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                isZiped = true;
                if (clickLicense) {
                    mTvPushLicensePic.setText("已上传");
                } else {
                    mTvPushIdPic.setText("已上传");
                }
            });
        }
    }

    @Override
    public void createOk() {
        super.createOk();
        ToastUtils.init().showInfoToast(mActivity, "店铺创建成功，请耐心等待审核");
        mCommonPresenter.shopEnter();

    }

    @Override
    public void shopEnter(ShopsBean bean) {
        super.shopEnter(bean);
        Bundle bundle = new Bundle();
        List<ShopsBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            ShopsBean.DataBean shop = data.get(0);
            bundle.putString("shopId", shop.id + "");
            bundle.putParcelable("shop", shop);
            ActivityUtils.init().start(mActivity, MainActivity.class, "", bundle);
            for (CommonPresenterActivity activity : mActivityArrayList) {
                activity.finish();
            }
            for (CommonLoginActivity activity : mActivityLogin) {
                activity.finish();
            }
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.ivLoginBack)
                .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mActivityArrayList.remove(this);
    }
}
