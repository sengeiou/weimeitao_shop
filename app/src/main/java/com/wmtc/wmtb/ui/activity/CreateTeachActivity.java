package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.TeachDetailBean;
import com.wmtc.wmtb.mvp.event.TeachUpdateEvent;
import com.wmtc.wmtb.mvp.pojo.TeachInfoPojo;
import com.wmtc.wmtb.mvp.presenter.CreateTeachPresenter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.AndroidBug5497Workaround;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.polygon.PolygonImageView;

/**
 * Created by Obl on 2019/3/27.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CreateTeachActivity extends CommonWmtActivity {
    @BindView(R.id.ivAvatar)
    PolygonImageView mIvAvatar;
    @BindView(R.id.llAvatar)
    LinearLayout mLlAvatar;
    @BindView(R.id.tvName)
    EditText mTvName;
    @BindView(R.id.tvTitle)
    EditText mTvTitle;
    @BindView(R.id.tvGoodAt)
    EditText mTvGoodAt;
    @BindView(R.id.tvInfo)
    EditText mTvInfo;
    @BindView(R.id.tvIDName)
    EditText mTvIDName;
    @BindView(R.id.tvIDNum)
    EditText mTvIDNum;
    @BindView(R.id.tvIdPic)
    TextView mTvIdPic;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.llIDPic)
    LinearLayout mLlIDPic;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    private Unbinder mBind;
    private DialogLoading mLoading;
    public boolean isZiped = true;
    private ArrayList<File> mArrayList;
    private boolean isAvatar = true;
    private CreateTeachPresenter mPresenter;
    private HashMap<String, String> mStringHashMap;
    private boolean mIsEdit;
    private TeachInfoPojo mPojo;
    private HashMap<String, String> mHashMap;
    private File avatarFile;
    private File idFile;
    private int netUp = 0;
    private DialogLoading mDialogLoading;
    private String[] mSplit = new String[]{};
    private PickerUtils mPickerUtils;

    @Override
    public int initAddLayout() {
        return R.layout.activity_create_teach;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        AndroidBug5497Workaround.assistActivity(this);
        isCheckKeyboard = false;
        mBind = ButterKnife.bind(this, rootView);
        mStringHashMap = new HashMap<>();
        mTvToolRight.setVisibility(View.INVISIBLE);
        mPresenter = new CreateTeachPresenter(this);
        mArrayList = new ArrayList<>();
        mLlAvatar.setOnClickListener(v -> {
            isAvatar = true;
            openCamera();
        });
        mTvIdPic.setOnClickListener(v -> {
            isAvatar = false;
            openNoCropCamera();
        });
        mBtnNext.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mTvName)) {
                ToastUtils.init().showInfoToast(mActivity, "请填写技师姓名");
                return;
            }
            if (StringUtils.init().isEmpty(mTvIDName)) {
                ToastUtils.init().showInfoToast(mActivity, "请填写技师身份信息");
                return;
            }
            if (StringUtils.init().isEmpty(mTvIDNum)) {
                ToastUtils.init().showInfoToast(mActivity, "请填写技师身份信息");
                return;
            }
            if (StringUtils.init().isEmpty(tvStartTime)) {
                ToastUtils.init().showInfoToast(mActivity, "请选择资历");
                return;
            }
            String goodAt = StringUtils.init().fixNullStr(mTvGoodAt.getText());
            String name = StringUtils.init().fixNullStr(mTvName.getText());
            String idName = StringUtils.init().fixNullStr(mTvIDName.getText());
            String idNo = StringUtils.init().fixNullStr(mTvIDNum.getText());
            String title = StringUtils.init().fixNullStr(mTvTitle.getText());
            String info = StringUtils.init().fixNullStr(mTvInfo.getText());
            String startTime = StringUtils.init().fixNullStr(tvStartTime.getText());
            mStringHashMap.put("title ", title);
            mStringHashMap.put("goodAt", goodAt);
            mStringHashMap.put("technicianName ", name);
            mStringHashMap.put("introduce", info);
            mStringHashMap.put("technicianIdcardName", idName);
            mStringHashMap.put("technicianIdcardNo", idNo);
            mStringHashMap.put("shopId", WmtApplication.user_shopId);
            mStringHashMap.put("shopName", mBundle.getString("shopName"));
            mStringHashMap.put("createBy", WmtApplication.user_shopId);
            mStringHashMap.put("workTime", startTime);
            if (mIsEdit) {
                mDialogLoading = new DialogLoading(mActivity);
                mDialogLoading.show();
                mPojo.setTechnicianIdcardName(idName);
                mPojo.setTechnicianIdcardNo(idNo);
                mPojo.setTechnicianName(name);
                mPojo.setTitle(title);
                mPojo.setGoodAt(goodAt);
                mPojo.setIntroduce(info);
                mPojo.setWorkTime(startTime);
                mHashMap = new HashMap<>();
                mHashMap.put("technicianId", mPojo.getId());
                mHashMap.put("updateBy", WmtApplication.user_shopId);
                if (avatarFile != null) {
                    mHashMap.put("fileType", "avatar");
                    ++netUp;
                    mPresenter.updateTechnicianAttach(avatarFile, mHashMap, "3");
                }
                if (idFile != null) {
                    ++netUp;
                    mHashMap.put("fileType", "idcardFront");
                    mPresenter.updateTechnicianAttach(idFile, mHashMap, "3");
                }
                ++netUp;
                mPresenter.updateTechnicianDetail(mPojo);
            } else {
                if (avatarFile != null) {
                    mArrayList.add(avatarFile);
                }
                if (idFile != null) {
                    mArrayList.add(idFile);
                }
                mPresenter.saveTechnicianAttach(mArrayList, mStringHashMap, "1");
            }
        });
        mPickerUtils = new PickerUtils();
        tvStartTime.setOnClickListener(v -> {
            KeyboardUtils.init().hideSoftInput(mActivity);
            mPickerUtils.initTimePicker(mActivity, mSplit, (date, patternDate) -> {
                LogUtil.e(patternDate);
                tvStartTime.setText(patternDate);
                KeyboardUtils.init().hideSoftInput(mActivity);
            });
            mPickerUtils.timeShow();
        });
        mIsEdit = mBundle.getBoolean("isEdit");
        if (mIsEdit) {
            String id = mBundle.getString("id");
            mPresenter.getTechnicianDetail(id);
//
//            String workTime = mBundle.getString("workTime");
//            if (StringUtils.isNotBlank(workTime)) {
//                mSplit = workTime.split("");
//                tvStartTime.setText(workTime);
//            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

    public void saveTeach() {
        EventBus.getDefault().post(new TeachUpdateEvent());
        Disposable subscribe =
                Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                    finish();
                });
    }

    public void teachInfo(TeachDetailBean bean) {
        TeachDetailBean.DataBean.SysShopTechnicianBean sysShopTechnician = bean.data.sysShopTechnician;
        if (sysShopTechnician != null) {
            mPojo = new TeachInfoPojo();
            String title = sysShopTechnician.title;
            if (StringUtils.isNotBlank(title)) {
                mTvTitle.setText(StringUtils.init().fixNullStr(title));
                mPojo.setTitle(title);
            }
            String technicianName = sysShopTechnician.technicianName;
            if (StringUtils.isNotBlank(technicianName)) {
                mTvName.setText(StringUtils.init().fixNullStr(technicianName));
                mPojo.setTechnicianName(technicianName);
            }
            String technicianIdcardName = sysShopTechnician.technicianIdcardName;
            if (StringUtils.isNotBlank(technicianIdcardName)) {
                mTvIDName.setText(StringUtils.init().fixNullStr(technicianIdcardName));
                mPojo.setTechnicianIdcardName(technicianIdcardName);
            }
            String technicianIdcardNo = sysShopTechnician.technicianIdcardNo;
            if (StringUtils.isNotBlank(technicianIdcardNo)) {
                mTvIDNum.setText(StringUtils.init().fixNullStr(technicianIdcardNo));
                mPojo.setTechnicianIdcardNo(technicianIdcardNo);
            }
            String goodAt = sysShopTechnician.goodAt;
            if (StringUtils.isNotBlank(goodAt)) {
                mTvGoodAt.setText(StringUtils.init().fixNullStr(goodAt));
                mPojo.setGoodAt(goodAt);
            }
            String workTime = sysShopTechnician.workTime;
            if (StringUtils.isNotBlank(workTime)) {
                tvStartTime.setText(StringUtils.init().fixNullStr(workTime));
                mPojo.setWorkTime(workTime);
            }
            String introduce = sysShopTechnician.introduce;
            if (StringUtils.isNotBlank(introduce)) {
                mTvInfo.setText(StringUtils.init().fixNullStr(introduce));
                mPojo.setIntroduce(introduce);
            }
            String technicianIdcardFront = sysShopTechnician.technicianIdcardFront;
            if (StringUtils.isNotBlank(technicianIdcardFront)) {
                mTvIdPic.setText("已上传");
            }
            String avatar = sysShopTechnician.avatar;
            if (StringUtils.isNotBlank(avatar)) {
                Glide.with(mActivity).load(WmtApplication.url_host + avatar).into(mIvAvatar);
            }
            mPojo.setId(sysShopTechnician.id + "");
            mPojo.setUpdateBy(WmtApplication.user_shopId);
            mPojo.setStatus("3");
        }
    }

    private void openCamera() {
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                .onGranted(permissions -> CameraUtil.getInstance().openSingalCamer(this.mActivity))
                .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                .start();
    }

    private void openNoCropCamera() {
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                .onGranted(permissions -> CameraUtil.getInstance().openSingalCamerNoCrop(this.mActivity))
                .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                .start();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (mLoading == null) {
                mLoading = new DialogLoading(this);
            }
            mLoading.show();
            isZiped = false;
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Observable.just(pathList).subscribeOn(Schedulers.io()).map(strings -> {
                Observable.fromIterable(strings).subscribe(s -> {
                    File file;
                    if (isAvatar) {
                        file = BitmapUtil.compressImage(new File(s), "avatar" + UUID.randomUUID());
                        avatarFile = file;
                    } else {
                        file = BitmapUtil.compressImage(new File(s), "idcardFront" + UUID.randomUUID());
                        idFile = file;
                    }
                });
                return mArrayList;
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(files -> {

                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                if (isAvatar) {
                    Glide.with(mActivity).load(avatarFile).into(mIvAvatar);
                } else {
                    mTvIdPic.setText("已上传");
                }
                isZiped = true;
            });
        }
    }

    public void netOk(String status) {
        --netUp;
        if (netUp <= 0) {
            EventBus.getDefault().post(new TeachUpdateEvent());
            if (mDialogLoading != null && mDialogLoading.isShowing()) {
                mDialogLoading.dismiss();
            }
            ToastUtils.init().showInfoToast(mActivity, "已提交技师，请等待审核");
            Disposable subscribe =
                    Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                        finish();
                    });
        }
    }
}
