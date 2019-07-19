package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wmtc.wmtb.BuildConfig;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.DictListBean;
import com.wmtc.wmtb.mvp.bean.OSSBean;
import com.wmtc.wmtb.mvp.bean.ProjectDetialBean;
import com.wmtc.wmtb.mvp.event.CreateProjectEvent;
import com.wmtc.wmtb.mvp.event.FileSelListEvent;
import com.wmtc.wmtb.mvp.event.FileUpdateOkEvent;
import com.wmtc.wmtb.mvp.event.ProSelEvent;
import com.wmtc.wmtb.mvp.pojo.DictPojo;
import com.wmtc.wmtb.mvp.pojo.ProjectDetailPojo;
import com.wmtc.wmtb.mvp.presenter.ProjectCreatePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.net.tip.DialogShortNetTip;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.AndroidBug5497Workaround;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.MD5Utils;
import top.jplayer.baseprolibrary.utils.MoneyUtils;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

import static com.wmtc.wmtb.base.WmtApplication.ENDPOINT;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProjectActivity extends CommonWmtActivity {

    @BindView(R.id.tvName)
    EditText mTvName;
    @BindView(R.id.flBannerPics)
    FrameLayout mFlBannerPics;
    @BindView(R.id.tvPlace)
    TextView mTvPlace;
    @BindView(R.id.tvSelType)
    TextView tvSelType;
    @BindView(R.id.tvEffect)
    EditText mTvEffect;
    @BindView(R.id.tvUse)
    EditText mTvUse;
    @BindView(R.id.tvIns)
    EditText mTvIns;
    @BindView(R.id.tvProgress)
    EditText mTvProgress;
    @BindView(R.id.rbHave)
    RadioButton mRbHave;
    @BindView(R.id.rbNone)
    RadioButton mRbNone;
    @BindView(R.id.tvServerContent)
    EditText mTvServerContent;
    @BindView(R.id.tvOld)
    EditText mTvOld;
    @BindView(R.id.tvNew)
    EditText mTvNew;
    @BindView(R.id.flInfoPics)
    FrameLayout mFlInfoPics;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    @BindView(R.id.llHasGone)
    LinearLayout llHasGone;
    @BindView(R.id.tvBannerUploadTip)
    TextView tvBannerUploadTip;
    @BindView(R.id.tvInfoPics)
    TextView tvInfoPics;
    @BindView(R.id.tvFixPrice)
    TextView tvFixPrice;
    @BindView(R.id.tvRate)
    TextView tvRate;
    private ProjectCreatePresenter mPresenter;
    private Unbinder mBind;
    private PickerUtils mPickerUtils;
    private DictPojo mPojo;
    private HashMap<String, String> mMap;
    private boolean isEdit;
    private ArrayList<ProjectDetialBean.DataBean.BannerAttachesBean> mBannerAttaches;
    private ArrayList<ProjectDetialBean.DataBean.DetailAttachesBean> mDetailAttaches;
    private ArrayList<ProjectDetialBean.DataBean.CommonPicBean> mCommonPicBeans;
    private DialogLoading mLoading;
    private DialogLoading mDialogLoading;
    private DialogLoading mDialogLoadingCreate;
    private OSSClient mOss;

    @Override
    public int initAddLayout() {
        return R.layout.activity_project;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        AndroidBug5497Workaround.assistActivity(this);
        EventBus.getDefault().register(this);
        mMap = new HashMap<>();
        isCheckKeyboard = false;
        isEdit = "1".equals(mBundle.getString("edit"));
        mPresenter = new ProjectCreatePresenter(this);
        mPojo = new DictPojo();
        mPresenter.ossToken();
        mPickerUtils = new PickerUtils();
        mBind = ButterKnife.bind(this, rootView);
        tvSelType.setOnClickListener(v -> {
            mPojo.setCode("sys_project");
//            mPresenter.dictList(mPojo);
            KeyboardUtils.init().hideSoftInput(this);
            ActivityUtils.init().start(mActivity, ProSelActivity.class, "选择预约项目");
        });
        mTvPlace.setOnClickListener(v -> {
            mPojo.setCode("sys_buwei");
            mPresenter.dictList(mPojo);
            KeyboardUtils.init().hideSoftInput(this);
        });
        mFlBannerPics.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("max", 15);
            bundle.putSerializable("files", bannerPics);
            bundle.putBoolean("isEdit", isEdit);
            Gson gson = new Gson();
            Type type = new TypeToken<List<ProjectDetialBean.DataBean.CommonPicBean>>() {
            }.getType();
            ArrayList<ProjectDetialBean.DataBean.CommonPicBean> commonPicBeans =
                    gson.fromJson(gson.toJson(mBannerAttaches), type);
            bundle.putParcelableArrayList("strFiles", commonPicBeans);
            ActivityUtils.init().start(mActivity, PicsUploadActivity.class, "上传头图", bundle);
            KeyboardUtils.init().hideSoftInput(this);

        });
        mFlInfoPics.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("max", 15);
            bundle.putSerializable("files", infoPics);
            bundle.putBoolean("isEdit", isEdit);
            Gson gson = new Gson();
            Type type = new TypeToken<List<ProjectDetialBean.DataBean.CommonPicBean>>() {
            }.getType();
            ArrayList<ProjectDetialBean.DataBean.CommonPicBean> commonPicBeans =
                    gson.fromJson(gson.toJson(mDetailAttaches), type);
            bundle.putParcelableArrayList("strFiles", commonPicBeans);
            ActivityUtils.init().start(mActivity, PicsUploadActivity.class, "上传详情图", bundle);
            KeyboardUtils.init().hideSoftInput(this);

        });
        mRbNone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            llHasGone.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        });
        mBtnNext.setOnClickListener(v -> {
            status = "3";
            pushProject("3");
        });
        if (isEdit) {
            String proId = mBundle.getString("proId");
            mMap.put("projectId", proId);
            ProjectDetailPojo pojo = new ProjectDetailPojo();
            pojo.setProjectId(proId);
            mPresenter.projectDetail(pojo);
        } else {
            LogUtil.e("-----");
        }
        mTvNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pIdRate = mMap.get("pIdRate");
                if (!isEdit && StringUtils.isBlank(pIdRate)) {
                    ToastUtils.init().showInfoToast(mActivity, "请先选择项目");
                    return;
                }
                if (StringUtils.isBlank(pIdRate)) {
                    pIdRate = "0";
                }
                String newPrice = StringUtils.init().fixNullStr(s);
                String oldPrice = StringUtils.init().fixNullStr(mTvOld.getText());
                if (StringUtils.isNotBlank(oldPrice) && StringUtils.isNotBlank(newPrice)) {
                    float oldP = Float.parseFloat(oldPrice);
                    float newP = Float.parseFloat(newPrice);
                    if (oldP < newP) {
                        ToastUtils.init().showInfoToast(mActivity, "平台价不得大于售价");
                    }
                    float rate = Float.parseFloat(pIdRate);
                    float v = newP * (1 - rate);
                    tvFixPrice.setText(String.format(Locale.CHINA, "结算价：￥%.2f", v));
                }
            }
        });
    }

    public void proDetail(ProjectDetialBean bean) {
        ProjectDetialBean.DataBean data = bean.data;
        if (data != null) {
            ProjectDetialBean.DataBean.ShopProjectBean shopProject = data.shopProject;
            if (shopProject != null) {
                mMap.put("shopId", WmtApplication.user_shopId);

                String pProduct = shopProject.pProduct;
                if (StringUtils.isNotBlank(pProduct)) {
                    mTvUse.setText(pProduct);
                    mMap.put("pProduct", pProduct);
                }

                String pEffect = shopProject.pEffect;
                if (StringUtils.isNotBlank(pEffect)) {
                    mTvEffect.setText(pEffect);
                    mMap.put("pEffect", pEffect);
                }
                String pIncrement = shopProject.pIncrement;
                if (StringUtils.isNotBlank(pIncrement)) {
                    mTvServerContent.setText(pIncrement);
                    mMap.put("pIncrement", pIncrement);
                }
                String pInstrument = shopProject.pInstrument;
                if (StringUtils.isNotBlank(pInstrument)) {
                    mTvIns.setText(pInstrument);
                    mMap.put("pInstrument", pInstrument);
                }
                String pProcess = shopProject.pProcess;
                if (StringUtils.isNotBlank(pProcess)) {
                    mTvProgress.setText(pProcess);
                    mMap.put("pProcess", pProcess);
                }
                tvSelType.setText(shopProject.pIdValue);
                mMap.put("pId", shopProject.pId + "");
                mMap.put("pIdValue", shopProject.pIdValue);
                mMap.put("pIdRate", shopProject.pIdRate);
                mMap.put("pPrpoTime", shopProject.pPrpoTime);
                tvRate.setText(String.format(Locale.CHINA, "费率 %s", shopProject.pIdRate));
                mTvName.setText(shopProject.pTitle);
                mMap.put("pTitle", shopProject.pTitle);

                mTvPlace.setText(shopProject.pPlaceValue);
                mMap.put("pPlaceValue", shopProject.pPlaceValue);
                mMap.put("pPlace", shopProject.pPlace);

                String format = String.format(Locale.CHINA, "%.2f", shopProject.pOldPrice * 0.01);
                mMap.put("pOldPrice", format);
                mTvOld.setText(format);

                String formatNew = String.format(Locale.CHINA, "%.2f", shopProject.pPrice * 0.01);
                mMap.put("pPrice", formatNew);
                mTvNew.setText(formatNew);
            }
            mBannerAttaches = (ArrayList<ProjectDetialBean.DataBean.BannerAttachesBean>) data.bannerAttaches;
            if (mBannerAttaches != null) {
                String formatB = String.format(Locale.CHINA, "已上传%d/%d张", mBannerAttaches.size(), 15);
                tvBannerUploadTip.setText(formatB);
            }
            mDetailAttaches = (ArrayList<ProjectDetialBean.DataBean.DetailAttachesBean>) data.detailAttaches;
            if (mDetailAttaches != null) {
                String formatI = String.format(Locale.CHINA, "已上传%d/%d张", mDetailAttaches.size(), 15);
                tvInfoPics.setText(formatI);
            }
        }
    }

    private int netUp = 0;

    private void pushProject(String status) {
        if (StringUtils.init().isEmpty(mTvName)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入项目名称");
            return;
        }
        if (StringUtils.init().isEmpty(tvSelType)) {
            ToastUtils.init().showInfoToast(mActivity, "请选择项目分类");
            return;
        }
        if (StringUtils.init().isEmpty(mTvPlace)) {
            ToastUtils.init().showInfoToast(mActivity, "请选择项目针对部位");
            return;
        }
        if (StringUtils.init().isEmpty(mTvEffect)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入项目功效");
            return;
        }
        if (StringUtils.init().isEmpty(mTvUse)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入项目使用产品");
            return;
        }
        if (StringUtils.init().isEmpty(mTvIns)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入项目配合仪器");
            return;
        }
        if (StringUtils.init().isEmpty(mTvProgress)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入项目服务流程");
            return;
        }
        if (mRbHave.isChecked()) {
            if (StringUtils.init().isEmpty(mTvServerContent)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入项目增值服务");
                return;
            }
        }
        if (StringUtils.init().isEmpty(mTvOld)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入项目售价");
            return;
        }

        if (StringUtils.init().isEmpty(mTvNew)) {
            ToastUtils.init().showInfoToast(mActivity, "请输入项目平台价");
            return;
        }
        String oldPrice = StringUtils.init().fixNullStr(mTvOld.getText());
        String newPrice = StringUtils.init().fixNullStr(mTvNew.getText());
        if (Float.parseFloat(oldPrice) < Float.parseFloat(newPrice)) {
            ToastUtils.init().showInfoToast(mActivity, "平台价不得大于售价");
            return;
        }
        mJsons = new ArrayList<>();
        mMap.put("shopId", WmtApplication.user_shopId);
        mMap.put("pTitle", StringUtils.init().fixNullStr(mTvName.getText()));
        mMap.put("pEffect", StringUtils.init().fixNullStr(mTvEffect.getText()));
        mMap.put("pInstrument", StringUtils.init().fixNullStr(mTvIns.getText()));
        mMap.put("pIncrement", StringUtils.init().fixNullStr(mTvServerContent.getText()));
        mMap.put("pOldPrice", oldPrice);
        mMap.put("pPrice", newPrice);
        mMap.put("pProcess", StringUtils.init().fixNullStr(mTvProgress.getText()));
        mMap.put("pProduct", StringUtils.init().fixNullStr(mTvUse.getText()));
        mMap.put("createBy", WmtApplication.user_shopId);
        mMap.put("status", status);
        mMap.put("updateBy ", WmtApplication.user_shopId);

        if (isEdit) {
            mDialogLoading = new DialogLoading(mActivity);
            mDialogLoading.show();
            size = -1;
            if (bannerPics != null) {
                size += bannerPics.size();
                Observable.fromIterable(bannerPics)
                        .subscribeOn(Schedulers.io())
                        .map(file -> {
                            updatePhoto(file, "1", "4");
                            return file;
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
            }
            if (infoPics != null) {

                size += infoPics.size();
                Observable.fromIterable(infoPics)
                        .subscribeOn(Schedulers.io())
                        .map(file -> {
                            updatePhoto(file, "1", "5");
                            return file;
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
            }
            StringBuilder builder = new StringBuilder();
            if (StringUtils.isNotBlank(delBanner)) {
                builder.append(delBanner);
                builder.append(",");
            }
            if (StringUtils.isNotBlank(delInfo)) {
                builder.append(delInfo);
            }
            String string = builder.toString();
            if (StringUtils.isNotBlank(string)) {
                string = string.substring(0, string.length() - 1);
            } else {
                string = "";
            }
            mMap.put("delAttachments", string);
            if ((infoPics == null || infoPics.size() < 1) && (bannerPics == null || bannerPics.size() < 1)) {
                mPresenter.updateProject(mMap, status);
            }
        } else {
            mDialogLoadingCreate = new DialogLoading(mActivity);
            mDialogLoadingCreate.show();
            size = -1;
            if (bannerPics != null && bannerPics.size() > 0) {
                size += bannerPics.size();
                Observable.fromIterable(bannerPics)
                        .subscribeOn(Schedulers.io())
                        .map(file -> {
                            updatePhoto(file, "1", "4");
                            return file;
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
            }
            if (infoPics != null && infoPics.size() > 0) {
                size += infoPics.size();
                Observable.fromIterable(infoPics)
                        .subscribeOn(Schedulers.io())
                        .map(file -> {
                            updatePhoto(file, "1", "5");
                            return file;
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
            }
            if ((infoPics == null || infoPics.size() < 1) && (bannerPics == null || bannerPics.size() < 1)) {
                mPresenter.createProject(new ArrayList<>(), mMap, status);
            }
        }
    }

    private ArrayList<File> bannerPics = new ArrayList<>();
    private ArrayList<File> infoPics = new ArrayList<>();
    private String delBanner = "";
    private String delInfo = "";
    private String status = "3";

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        status = "2";
        pushProject("2");
    }

    @Subscribe
    public void onEvent(FileUpdateOkEvent event) {
        mMap.put("attachmentJson", new Gson().toJson(mJsons));
        LogUtil.e("--------执行创建-------");
        if (isEdit) {
            mPresenter.updateProject(mMap, status);
        } else {
            mPresenter.createProject(new ArrayList<>(), mMap, status);
        }
    }

    @Subscribe
    public void onEvent(ProSelEvent event) {
        tvSelType.setText(StringUtils.init().fixNullStr(event.projectName));
        mMap.put("pIdValue", StringUtils.init().fixNullStr(event.projectName));
        mMap.put("pId", StringUtils.init().fixNullStr(event.projectId));
        mMap.put("pIdRate", StringUtils.init().fixNullStr(event.rate));
        mMap.put("pPrpoTime", StringUtils.init().fixNullStr(event.propTime));
        tvRate.setText(String.format(Locale.CHINA, "费率 %s", event.rate));
    }

    @Subscribe
    public void onEvent(FileSelListEvent event) {
        int size = event.data.size();
        int length = 0;
        if (StringUtils.isNotBlank(event.delIds)) {
            length = event.delIds.split(",").length;
        }
        if (event.type.contains("头图")) {
            size += mBannerAttaches == null ? 0 : mBannerAttaches.size() - length;
        } else {
            size += mDetailAttaches == null ? 0 : mDetailAttaches.size() - length;
        }
        String format = String.format(Locale.CHINA, "已上传%d/%d张", size, 15);
        if (event.type.contains("头图")) {
            tvBannerUploadTip.setText(format);
            bannerPics = event.data;
            delBanner = event.delIds;
        } else {
            infoPics = event.data;
            delInfo = event.delIds;
            tvInfoPics.setText(format);
        }
    }

    @SuppressLint("CheckResult")
    public void dictList(DictListBean bean, String code) {
        ArrayList<String> objects = new ArrayList<>();
        if (bean.data != null) {
            Observable.fromIterable(bean.data).subscribe(dataBean -> {
                objects.add(dataBean.name);
            });
            mPickerUtils.initStringPicker(objects, 0, mActivity, (position, string) -> {
                if (code.equals("sys_project")) {
                    tvSelType.setText(string);
                    mMap.put("pIdValue", string);
                    mMap.put("pId", bean.data.get(position).id + "");
                } else {
                    mTvPlace.setText(string);
                    mMap.put("pPlaceValue", string);
                    mMap.put("pPlace", bean.data.get(position).id + "");
                }
            });
            mPickerUtils.stringShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
    }

    public void createProject(String status) {
        if (mDialogLoadingCreate != null && mDialogLoadingCreate.isShowing()) {
            mDialogLoadingCreate.dismiss();
        }
        if (status.equals("2")) {
            ToastUtils.init().showInfoToast(mActivity, "已保存项目，请前往提交");
        } else if (status.equals("3")) {
            ToastUtils.init().showInfoToast(mActivity, "已提交项目，请等待审核");
        }
        EventBus.getDefault().post(new CreateProjectEvent());
        Disposable subscribe =
                Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                    finish();
                });
    }


    private ArrayList<Object> mJsons;

    public void updateProject(String status) {
        EventBus.getDefault().post(new CreateProjectEvent());
        --netUp;
        if (netUp <= 0) {
            if (mDialogLoading != null && mDialogLoading.isShowing()) {
                mDialogLoading.dismiss();
            }
            if (status.equals("2")) {
                ToastUtils.init().showInfoToast(mActivity, "已保存项目，请前往提交");
            } else if (status.equals("3")) {
                ToastUtils.init().showInfoToast(mActivity, "已提交项目，请等待审核");
            }
            Disposable subscribe =
                    Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                        finish();
                    });
        }
    }

    public void netOk(String status) {
        --netUp;
        if (netUp <= 0) {
            if (mDialogLoading != null && mDialogLoading.isShowing()) {
                mDialogLoading.dismiss();
            }
            if (status.equals("2")) {
                ToastUtils.init().showInfoToast(mActivity, "已保存项目，请前往提交");
            } else if (status.equals("3")) {
                ToastUtils.init().showInfoToast(mActivity, "已提交项目，请等待审核");
            }
            Disposable subscribe =
                    Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                        finish();
                    });
        }
    }

    private OSSBean.DataBean mBean;
    private int curNum = 0;
    private Bitmap mBitmap;
    private int size;

    public void ossToken(OSSBean bean) {
        mBean = bean.data;
        OSSCredentialProvider credentialProvider = new OSSFederationCredentialProvider() {

            @Override
            public OSSFederationToken getFederationToken() {
                return new OSSFederationToken(mBean.AccessKeyId, mBean.AccessKeySecret, mBean.SecurityToken, mBean.Expiration);
            }
        };
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(9); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        mOss = new OSSClient(getApplicationContext(), ENDPOINT, credentialProvider, conf);
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
        String fileUrl = "project/" + md5 + ext;
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

        OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Map<String, String> stringMap = request.getMetadata().getUserMetadata();
                mJsons.add(stringMap);
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

        // task.cancel(); // 可以取消任务

        // task.waitUntilFinished(); // 可以等待直到任务完成
    }

}
