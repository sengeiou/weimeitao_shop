package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmtb.BuildConfig;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.event.FileUpdateOkEvent;
import com.wmtc.wmtb.mvp.event.VideoVrUpdateEvent;
import com.wmtc.wmtb.mvp.presenter.ShopVideoOrVrPresenter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.ui.activity.WebViewActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.MD5Utils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.utils.UriUtils;

/**
 * Created by Obl on 2019/6/19.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopVideoOrVrActivity extends CommonWmtActivity {
    private static final int REQUEST_VIDEO_CODE = 0x9999;
    @BindView(R.id.flAddUrl)
    FrameLayout mFlAddUrl;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.ivAddUrl)
    ImageView mIvAddUrl;
    @BindView(R.id.tvSubTitle)
    TextView mTvSubTitle;
    @BindView(R.id.tvType)
    TextView mTvType;
    @BindView(R.id.tvSureAdd)
    TextView mTvSureAdd;
    private Unbinder mBind;
    private ShopVideoOrVrPresenter mPresenter;
    private boolean mIsVideo;
    private String netVrUrl = "";
    private String netVideoUrl = "";
    private boolean isPreView = false;
    private boolean isLocalSel = false;

    @Override
    public int initAddLayout() {
        return R.layout.activity_shop_video_vr;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mPresenter = new ShopVideoOrVrPresenter(this);
        mPresenter.ossToken();
        mTvToolRight.setText("预览");
        mFlAddUrl.setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> {
                        if (mIsVideo) {
                            Intent intent = new Intent();
                            intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, REQUEST_VIDEO_CODE);
                        } else {
                            CameraUtil.getInstance().openSingalCamerNoCrop(this.mActivity);
                        }

                    })
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();

        });
        mTvSureAdd.setOnClickListener(v -> {
            if (StringUtils.isBlank(fileSelPath)) {
                ToastUtils.init().showInfoToast(mActivity, "请选择需要上传的全景图或视频");
                return;
            }
            if (!isLocalSel) {
                ToastUtils.init().showInfoToast(mActivity, "请选择需要上传的全景图或视频。");
                return;
            }
            mLoading = new DialogLoading(mActivity);
            mLoading.bindText("上传中，请勿退出！", R.id.name);
            mLoading.show();
            if (mIsVideo) {
                updateVideo(fileSelPath, "1", "shopVideo");
            } else {
                isPreView = false;
                updatePhoto(fileSelPath, "1", "shopVR");
            }
        });
        String title = mBundle.getString("title");
        mIsVideo = title != null && title.contains("视频");
        mTvTitle.setText(title);
        mTvType.setText(mBundle.getString("type"));
        mTvSubTitle.setText(mBundle.getString("subTitle"));
        String url = WmtApplication.url_host + mBundle.getString("url");
        fileSelPath = url;
        if (mIsVideo) {
            Observable.just(url).subscribeOn(Schedulers.io())
                    .map(s -> {
                        if (mIsVideo && StringUtils.isNotBlank(url)) {
                            LogUtil.e(url);
                            Bitmap videoThumb = BitmapUtil.getVideoThumb(url);
                            return videoThumb;
                        }
                        return null;
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bitmap -> {
                        mIvAddUrl.setImageBitmap(bitmap);
                    }, throwable -> {
                    });
        } else {
            Glide.with(mActivity)
                    .load(fileSelPath)
                    .into(mIvAddUrl);
        }

    }

    @SuppressLint("CheckResult")
    @Subscribe
    public void onEvent(FileUpdateOkEvent event) {

        Observable.just(1).compose(new IoMainSchedule<>())
                .subscribe(integer -> {
                    if ("shopVR".equals(event.type)) {
                        netVrUrl = event.fileUrl;
                        if (isPreView) {
                            if (mLoading != null && mLoading.isShowing()) {
                                mLoading.dismiss();
                            }
                            Bundle bundle = new Bundle();
                            String url = String.format(Locale.CHINA, "http://yinsi.weimeitao.com/h5wmt/app/vr/VRShow.html?VRImg=%s&imgHost=%d",
                                    netVrUrl, BuildConfig.DEBUG ? 0 : 1);
                            bundle.putString("url", url);
                            ActivityUtils.init().start(mActivity, WebViewActivity.class, "", bundle);
                        } else {
                            mPresenter.saveVideoOrVr(netVrUrl, "1");
                        }
                    } else {
                        netVideoUrl = event.fileUrl;
                        mPresenter.saveVideoOrVr(event.fileUrl, "1");
                    }
                }, throwable -> {

                });
    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        if (StringUtils.isBlank(fileSelPath)) {
            ToastUtils.init().showInfoToast(mActivity, "请选择需要预览的全景图或视频");
            return;
        }
        LogUtil.e(fileSelPath);
        if (mIsVideo) {
            Bundle bundle = new Bundle();
            bundle.putString("url", fileSelPath);
            bundle.putBoolean("isLocal", isLocalSel);
            ActivityUtils.init().start(mActivity, VideoPlayActivity.class, "", bundle);
        } else {
            isPreView = true;
            if (isLocalSel) {
                mLoading = new DialogLoading(mActivity);
                mLoading.bindText("正在生成VR预览图，请勿退出！", R.id.name);
                mLoading.show();
                updatePhoto(fileSelPath, "1", "shopVR");
            } else {
                Bundle bundle = new Bundle();
//                String url = String.format(Locale.CHINA, "http://yinsi.weimeitao.com/h5wmt/app/vr/VRShow.html?VRImg=%s&imgHost=%d",
//                        fileSelPath, 2);
                String url = String.format(Locale.CHINA, "http://yinsi.weimeitao.com/h5wmt/app/vr/VRShow.html?VRImg=%s&imgHost=%d",
                        fileSelPath, 2);
                bundle.putString("url", url);
                ActivityUtils.init().start(mActivity, WebViewActivity.class, "", bundle);
            }
        }
    }

    public void ossClient(OSSClient mOss) {
        this.mOss = mOss;
    }

    public void saveOk() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
        ToastUtils.init().showSuccessToast(mActivity, "上传成功");
        EventBus.getDefault().post(new VideoVrUpdateEvent(netVrUrl, netVideoUrl));
        finish();
    }

    private int curNum = 0;
    private Bitmap mBitmap;
    private int size;
    private OSSClient mOss;
    private StringBuilder mStringBuilder;

    /**
     * OSS图片上传
     *
     * @param file
     */
    public void updatePhoto(String file, String type, String serviceType) {
        String md5 = MD5Utils.getMD5(file);
        String ext = file.substring(file.lastIndexOf("."));
        mBitmap = BitmapUtil.adjustBitmap(file);
//        byte[] bytes = BitmapUtil.compressByQuality(mBitmap, 250 * 1024, true);
        String fileUrl = serviceType + "/" + md5 + ext;
        PutObjectRequest put = new PutObjectRequest(BuildConfig.DEBUG ? "dev-bucket-wmtc" : "prod-bucket-wmtc",
                fileUrl, file);
        put.setCallbackParam(new HashMap<String, String>() {
            {

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
                        EventBus.getDefault().post(new FileUpdateOkEvent(fileUrl, serviceType));
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

    public void updateVideo(String filePath, String type, String serviceType) {
        String md5 = MD5Utils.getMD5(filePath);
        String ext = filePath.substring(filePath.lastIndexOf("."));
        String fileUrl = serviceType + "/" + md5 + ext;
        PutObjectRequest put = new PutObjectRequest(BuildConfig.DEBUG ? "dev-bucket-wmtc" : "prod-bucket-wmtc", fileUrl, filePath);
        put.setCallbackParam(new HashMap<String, String>() {
            {

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
                        EventBus.getDefault().post(new FileUpdateOkEvent(fileUrl, serviceType));
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

    public String fileSelPath;
    private DialogLoading mLoading;
    public boolean isZiped = true;

    @SuppressLint("CheckResult")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            isLocalSel = true;
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (mLoading == null) {
                mLoading = new DialogLoading(this);
            }
            mLoading.show();
            isZiped = false;
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Observable.just(pathList).subscribeOn(Schedulers.io()).map(strings -> {
                fileSelPath = strings.get(0);
                return fileSelPath;
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                isZiped = true;
                LogUtil.e(fileSelPath);
                Glide.with(mActivity).load(s).apply(GlideUtils.init().options(R.drawable.wmt_default)).into(mIvAddUrl);
            });
        } else if (requestCode == REQUEST_VIDEO_CODE && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            LogUtil.e(uri);
            LogUtil.e("================");
            String filePathByUri = UriUtils.getPath(mActivity, uri);
            fileSelPath = filePathByUri;
            Glide.with(mActivity).load(filePathByUri).apply(GlideUtils.init().options(R.drawable.wmt_default)).into(mIvAddUrl);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
        mPresenter.detachView();
    }

}
