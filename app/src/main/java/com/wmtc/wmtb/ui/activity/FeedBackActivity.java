package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmtb.BuildConfig;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.bean.OSSBean;
import com.wmtc.wmtb.mvp.event.FileUpdateOkEvent;
import com.wmtc.wmtb.mvp.pojo.FeedBackPojo;
import com.wmtc.wmtb.mvp.presenter.FeedBackPresenter;
import com.wmtc.wmtb.ui.adapter.PicsAdapter;
import com.wmtc.wmtb.ui.adapter.PicsStrAdapter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.AndroidBug5497Workaround;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.MD5Utils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

import static com.wmtc.wmtb.base.WmtApplication.ENDPOINT;

/**
 * Created by Obl on 2019/4/3.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FeedBackActivity extends CommonWmtActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ivAddPic)
    ImageView mIvAddPic;
    @BindView(R.id.editNum)
    EditText mEditNum;
    @BindView(R.id.editContent)
    EditText mEditContent;
    private ArrayList<File> mArrayList;
    private Unbinder mBind;
    private PicsAdapter mAdapter;
    private DialogLoading mLoading;
    public boolean isZiped = true;
    private FeedBackPresenter mPresenter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        AndroidBug5497Workaround.assistActivity(this);
        mBind = ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mArrayList = new ArrayList<>();
        mAdapter = new PicsAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new FeedBackPresenter(this);
        EventBus.getDefault().register(this);
        mIvAddPic.setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> CameraUtil.getInstance().openSingalCamerNoCrop(mActivity))
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            if (view1.getId() == R.id.ivDel) {
                mAdapter.remove(position);
                mIvAddPic.setVisibility(View.VISIBLE);
            }
            return false;
        });
        mTvToolRight.setText("提交");
        mPresenter.ossToken();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        if (StringUtils.init().isEmpty(mEditNum)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入联系方式");
            return;
        }
        if (StringUtils.init().isEmpty(mEditContent)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入反馈内容");
            return;
        }
        if (mLoading == null) {
            mLoading = new DialogLoading(this);
        }
        mLoading.show();
        if (mArrayList != null && mArrayList.size() > 0) {
            updatePhoto(mArrayList.get(0), "", "");
        } else {
            FeedBackPojo pojo = new FeedBackPojo();
            pojo.setContact(StringUtils.init().fixNullStr(mEditNum.getText()));
            pojo.setContent(StringUtils.init().fixNullStr(mEditContent.getText()));
            mPresenter.addFeedback(pojo);
        }

    }

    @Subscribe
    public void onEvent(FileUpdateOkEvent event) {
        FeedBackPojo pojo = new FeedBackPojo();
        pojo.setContact(StringUtils.init().fixNullStr(mEditNum.getText()));
        pojo.setContent(StringUtils.init().fixNullStr(mEditContent.getText()));
        pojo.setPicUrl(mStringBuilder.toString());
        mPresenter.addFeedback(pojo);
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
                    File file = BitmapUtil.compressImage(new File(s));
                    mArrayList.add(file);
                });
                return mArrayList;
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(files -> {

                mAdapter.setNewData(files);
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                isZiped = true;
                mIvAddPic.setVisibility(View.GONE);
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        KeyboardUtils.init().hideSoftInput(this);
        EventBus.getDefault().unregister(this);
    }

    private int curNum = 0;
    private Bitmap mBitmap;
    private int size;
    private OSSClient mOss;
    private StringBuilder mStringBuilder;

    public void ossClient(OSSClient mOss) {
        this.mOss = mOss;
    }

    /**
     * OSS图片上传
     *
     * @param file
     */
    public void updatePhoto(File file, String type, String serviceType) {
        String md5 = MD5Utils.getMD5(file.getName());
        String ext = file.getPath().substring(file.getPath().lastIndexOf("."));
        mBitmap = BitmapUtil.adjustBitmap(file.getAbsolutePath());
        byte[] bytes = BitmapUtil.compressByQuality(mBitmap, 250 * 1024, true);
        String fileUrl = "feedback/" + md5 + ext;
        PutObjectRequest put = new PutObjectRequest(BuildConfig.DEBUG ? "dev-bucket-wmtc" : "prod-bucket-wmtc", fileUrl, bytes);
        put.setCallbackParam(new HashMap<String, String>() {
            {
                // 图片 callbackUrl
                // put("callbackUrl", BaseVar.ADDPHOTO + "?fid=" + CommonInit.fid + "&uid=" + CommonInit.uid);
                // put("callbackBody", "object=${object}");
            }
        });
        ObjectMetadata metadata = new ObjectMetadata();
        HashMap<String, String> userMetadata = new HashMap<>();
        userMetadata.put("type", type);
        userMetadata.put("serviceType", serviceType);
        userMetadata.put("fileUrl", fileUrl);
        metadata.setUserMetadata(userMetadata);
        put.setMetadata(metadata);
        //   异步上传时可以设置进度回调
        put.setProgressCallback((request, currentSize, totalSize) -> LogUtil.e("currentSize: " + currentSize + " totalSize: " + totalSize));
        if (mOss != null) {
            OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    if (mStringBuilder == null) {
                        mStringBuilder = new StringBuilder();
                    }
                    Map<String, String> stringMap = request.getMetadata().getUserMetadata();
                    mStringBuilder.append(stringMap.get("fileUrl"));
                    ++curNum;
                    LogUtil.e(curNum + "---------------" + size);
                    if (curNum > size) {
                        EventBus.getDefault().post(new FileUpdateOkEvent());
                    }
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    // 请求异常
                    if (clientExcepion != null) {
                        // 本地异常如网络异常等
                        clientExcepion.printStackTrace();
                    }
                    if (serviceException != null) {

                    }
                }
            });
        }
    }

    @SuppressLint("CheckResult")
    public void netOk() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
        finish();
    }
}
