package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmtb.BuildConfig;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.BannerInfosBean;
import com.wmtc.wmtb.mvp.bean.ProjectDetialBean;
import com.wmtc.wmtb.mvp.event.FileSelListEvent;
import com.wmtc.wmtb.mvp.event.FileUpdateOkEvent;
import com.wmtc.wmtb.mvp.event.ShopTextEditEvent;
import com.wmtc.wmtb.mvp.presenter.ShopBannerPresenter;
import com.wmtc.wmtb.ui.adapter.PicsAdapter;
import com.wmtc.wmtb.ui.adapter.PicsStrAdapter;
import com.wmtc.wmtb.ui.adapter.PicsStrBannerAdapter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.MD5Utils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopBannerActivity extends CommonWmtActivity {
    RecyclerView mPicRecyclerView;
    private PicsAdapter mAdapter;
    private ArrayList<File> mArrayList;
    private DialogLoading mLoading;
    public boolean isZiped = true;
    public int maxPic = 15;
    private LinearLayout mLlHas;
    private PicsStrBannerAdapter mMAdapterStrPics;
    private StringBuilder mBuilder;
    RecyclerView mPicRecyclerViewHas;
    ArrayList<BannerInfosBean.DataBean> mCommonPicBeans;
    private ShopBannerPresenter mPresenter;
    private View mFooter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_shop_banner;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        EventBus.getDefault().register(this);
        mPicRecyclerView = rootView.findViewById(R.id.recyclerViewPics);
        mLlHas = rootView.findViewById(R.id.llHas);
        mPicRecyclerViewHas = rootView.findViewById(R.id.recyclerViewPicsHas);
        mPresenter = new ShopBannerPresenter(this);
        mPicRecyclerViewHas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPicRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mArrayList = new ArrayList<>();
        mCommonPicBeans = new ArrayList<>();
        mAdapter = new PicsAdapter(mArrayList);
        mPicRecyclerView.setAdapter(mAdapter);
        mLlHas = rootView.findViewById(R.id.llHas);
        mPicRecyclerViewHas = rootView.findViewById(R.id.recyclerViewPicsHas);
        mFooter = View.inflate(this, R.layout.adapter_footer_create_pro_pic, null);
        mFooter.findViewById(R.id.ivPicUpload).setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> CameraUtil.getInstance().openCamerWithSize(mActivity, maxPic))
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });

        mMAdapterStrPics = new PicsStrBannerAdapter(mCommonPicBeans);
        mPicRecyclerViewHas.setAdapter(mMAdapterStrPics);

        mAdapter.addFooterView(mFooter);
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            if (view1.getId() == R.id.ivDel) {
                mAdapter.remove(position);
                maxPic = 15 - mAdapter.getData().size() - mMAdapterStrPics.getData().size();
                if ( mAdapter.getData().size() + mMAdapterStrPics.getData().size() < 15 ) {
                    mAdapter.removeAllFooterView();
                    mAdapter.addFooterView(mFooter);
                }
            }
            return false;
        });
        mMAdapterStrPics.setOnItemChildClickListener((adapter, view1, position) -> {
            if (view1.getId() == R.id.ivDel) {
                BannerInfosBean.DataBean dataBean = mMAdapterStrPics.getData().get(position);
                mPresenter.shopBannerDelOne(dataBean.id + "", position);
            }
            return false;
        });
        mPresenter.shopBannerInfo();
        mPresenter.ossToken();
    }

    private DialogLoading mDialogLoading;

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);

        ArrayList<File> data = (ArrayList<File>) mAdapter.getData();
        size = -1;
        size += data.size();
        if (data.size() > 0) {
            mDialogLoading = new DialogLoading(mActivity);
            mDialogLoading.show();
            Observable.fromIterable(data)
                    .subscribeOn(Schedulers.io())
                    .map(file -> {
                        updatePhoto(file, "1", "4");
                        return file;
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }

    @Subscribe
    public void onEvent(FileUpdateOkEvent event) {
        HashMap<String, String> stringMap = new HashMap<>();
        stringMap.put("shopId", WmtApplication.user_shopId);
        String string = mStringBuilder.toString();
        if (StringUtils.isNotBlank(string)) {
            string = string.substring(0, string.length() - 1);
        } else {
            string = "";
        }
        stringMap.put("pics", string);

        mPresenter.shopBannerCreate(new ArrayList<>(), stringMap);

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
                    file = BitmapUtil.compressImage(new File(s));
                    mArrayList.add(file);
                });
                return mArrayList;
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(files -> {
                if (mAdapter.getData().size() >= maxPic) {
                    mAdapter.removeAllFooterView();
                }
                mAdapter.setNewData(files);
                String format = String.format(Locale.CHINA, "%d/%s", files.size(), maxPic);
                LogUtil.e(format);
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                isZiped = true;
                maxPic = 15 - mAdapter.getData().size() - mMAdapterStrPics.getData().size();
            });
        }
    }

    public void delOk(int position) {
        mMAdapterStrPics.remove(position);
        List<BannerInfosBean.DataBean> data = mMAdapterStrPics.getData();
        maxPic = 15 - mAdapter.getData().size() - mMAdapterStrPics.getData().size();
        if (!(data != null && data.size() > 0)) {
            mLlHas.setVisibility(View.INVISIBLE);
        }
    }

    public void netOk() {
        if (mDialogLoading != null && mDialogLoading.isShowing()) {
            mDialogLoading.dismiss();
        }
        EventBus.getDefault().post(new ShopTextEditEvent());
        Disposable subscribe =
                Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                    finish();
                });
    }

    public void info(BannerInfosBean bean) {
        if (bean.data != null && bean.data.size() > 0) {
            mCommonPicBeans = (ArrayList<BannerInfosBean.DataBean>) bean.data;
            mMAdapterStrPics.setNewData(mCommonPicBeans);
            maxPic = 15 - mAdapter.getData().size() - mMAdapterStrPics.getData().size();
        } else {
            mLlHas.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        String fileUrl = "shopbanner/" + md5 + ext;
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
                    mStringBuilder.append(",");
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
}
