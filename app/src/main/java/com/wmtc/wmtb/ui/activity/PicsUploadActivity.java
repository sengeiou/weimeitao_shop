package com.wmtc.wmtb.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.bean.ProjectDetialBean;
import com.wmtc.wmtb.mvp.event.FileSelListEvent;
import com.wmtc.wmtb.ui.adapter.PicsAdapter;
import com.wmtc.wmtb.ui.adapter.PicsStrAdapter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/3/26.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PicsUploadActivity extends CommonWmtActivity {

    RecyclerView mPicRecyclerView;
    RecyclerView mPicRecyclerViewHas;
    private PicsAdapter mAdapter;
    private ArrayList<File> mArrayList;
    private DialogLoading mLoading;
    public boolean isZiped = true;
    public int maxPic = 15;
    public int maxPicCamera = 15;
    ArrayList<ProjectDetialBean.DataBean.CommonPicBean> mCommonPicBeans;
    private LinearLayout mLlHas;
    private PicsStrAdapter mMAdapterStrPics;
    private StringBuilder mBuilder;
    private TextView mTvWillUpload;
    private TextView mTvHasUpload;

    @Override
    public int initAddLayout() {
        return R.layout.activity_picsupload;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mTvToolRight.setVisibility(View.INVISIBLE);
        mPicRecyclerView = rootView.findViewById(R.id.recyclerViewPics);
        mTvWillUpload = rootView.findViewById(R.id.tvWillUpload);
        mTvHasUpload = rootView.findViewById(R.id.tvHasUpload);
        mLlHas = rootView.findViewById(R.id.llHas);
        mPicRecyclerViewHas = rootView.findViewById(R.id.recyclerViewPicsHas);
        maxPic = mBundle.getInt("max");
        mPicRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPicRecyclerViewHas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mArrayList = (ArrayList<File>) mBundle.getSerializable("files");
        mCommonPicBeans = mBundle.getParcelableArrayList("strFiles");
        boolean isEdit = mBundle.getBoolean("isEdit");
        if (isEdit && mCommonPicBeans != null && mCommonPicBeans.size() > 0) {
            mLlHas.setVisibility(View.VISIBLE);
        } else {
            mLlHas.setVisibility(View.INVISIBLE);
        }
        if (mCommonPicBeans != null) {
            maxPic = 15 - mCommonPicBeans.size();
        }

        maxPicCamera = maxPic;
        mTvWillUpload.setText(String.format(Locale.CHINA, "待上传图片（0/%d）", maxPic));
        mTvHasUpload.setText(String.format(Locale.CHINA, "已上传图片（%d/15）", 15 - maxPic));
        mAdapter = new PicsAdapter(mArrayList);
        mPicRecyclerView.setAdapter(mAdapter);
        mMAdapterStrPics = new PicsStrAdapter(mCommonPicBeans);
        mPicRecyclerViewHas.setAdapter(mMAdapterStrPics);

        View view = View.inflate(this, R.layout.adapter_footer_create_pro_pic, null);
        view.findViewById(R.id.ivPicUpload).setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> CameraUtil.getInstance().openCamerWithSize(this.mActivity,
                            maxPicCamera))
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
        mAdapter.addFooterView(view);
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            if (view1.getId() == R.id.ivDel) {
                mAdapter.remove(position);
                if (mAdapter.getFooterLayoutCount() < 1 && mAdapter.getData().size() < maxPic) {
                    mAdapter.addFooterView(view);
                }
            }
            return false;
        });
        mBuilder = new StringBuilder();
        mMAdapterStrPics.setOnItemChildClickListener((adapter, view1, position) -> {
            if (view1.getId() == R.id.ivDel) {
                ProjectDetialBean.DataBean.CommonPicBean picBean = mMAdapterStrPics.getData().get(position);
                mMAdapterStrPics.remove(position);
                mBuilder.append(picBean.id);
                mBuilder.append(",");
                mTvHasUpload.setText(String.format(Locale.CHINA, "已上传图片（%d/15）", mMAdapterStrPics.getData().size()));
                int i = 15 - mMAdapterStrPics.getData().size();
                mTvWillUpload.setText(String.format(Locale.CHINA, "待上传图片（%d/%d）", mAdapter.getData().size(), i));
                maxPicCamera = i;
                maxPic = i;
                if (mAdapter.getFooterLayoutCount() < 1 && mAdapter.getData().size() < maxPic) {
                    mAdapter.addFooterView(view);
                }
            }
            return false;
        });
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
                    if (mIvToolLeft.getText().toString().equals("头图")) {
                        file = BitmapUtil.compressImage(new File(s), "banner" + UUID.randomUUID());
                    } else {
                        file = BitmapUtil.compressImage(new File(s), "info" + UUID.randomUUID());
                    }
                    mArrayList.add(file);
                });
                return mArrayList;
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(files -> {
                if (mAdapter.getData().size() >= maxPic) {
                    mAdapter.removeAllFooterView();
                }
                mAdapter.setNewData(files);
                maxPicCamera = maxPic - mAdapter.getData().size();
                String format = String.format(Locale.CHINA, "%d/%d", mAdapter.getData().size(), maxPic);
                LogUtil.e(format);
                mTvWillUpload.setText(String.format(Locale.CHINA, "待上传图片（%d/%d）", mAdapter.getData().size(), maxPic));
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                isZiped = true;
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArrayList<File> data = (ArrayList<File>) mAdapter.getData();
        String string = mBuilder.toString();
        if (StringUtils.isNotBlank(string)) {
            string = string.substring(0, string.length() - 1);
        } else {
            string = "";
        }
        FileSelListEvent listEvent = new FileSelListEvent(data, mIvToolLeft.getText().toString(), maxPic, string);
        EventBus.getDefault().post(listEvent);

    }

}
