package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.DictListBean;
import com.wmtc.wmtb.mvp.bean.OSSBean;
import com.wmtc.wmtb.mvp.bean.ProListBean;
import com.wmtc.wmtb.mvp.bean.ProjectDetialBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.DictPojo;
import com.wmtc.wmtb.mvp.pojo.ProListPojo;
import com.wmtc.wmtb.mvp.pojo.ProjectDetailPojo;
import com.wmtc.wmtb.ui.activity.ProjectActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProjectCreatePresenter extends BasePresenter<ProjectActivity> {

    private final CommonModel mModel;

    public ProjectCreatePresenter(ProjectActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void createProject(List<File> fileList, Map<String, String> stringMap, String status) {
        HashMap<String, RequestBody> mBodyHashMap = new HashMap<>();
        if (stringMap != null && stringMap.size() > 0) {
            for (String key : stringMap.keySet()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                        stringMap.get(key) == null ? "" : stringMap.get(key));
                mBodyHashMap.put(key, requestBody);
            }
        }
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), fileList.get(i));
                mBodyHashMap.put("files\"; filename=\"" + file.getName(), fileBody);
            }
        }
        mModel.createProject(mBodyHashMap).subscribe(new DefaultCallBackObserver<BaseBean>() {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.createProject(status);
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }

        });
    }

    public void updateProjectPic(List<File> fileList, Map<String, String> stringMap, String status) {
        HashMap<String, RequestBody> mBodyHashMap = new HashMap<>();
        if (stringMap != null && stringMap.size() > 0) {
            for (String key : stringMap.keySet()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                        stringMap.get(key) == null ? "" : stringMap.get(key));
                mBodyHashMap.put(key, requestBody);
            }
        }
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), fileList.get(i));
            mBodyHashMap.put("files\"; filename=\"" + file.getName(), fileBody);
        }

        mModel.updateProjectPic(mBodyHashMap).subscribe(new DefaultCallBackObserver<BaseBean>() {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.netOk(status);
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }

        });
    }

    public void updateProject(Map<String, String> stringMap, String status) {
        HashMap<String, RequestBody> mBodyHashMap = new HashMap<>();
        if (stringMap != null && stringMap.size() > 0) {
            for (String key : stringMap.keySet()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                        stringMap.get(key) == null ? "" : stringMap.get(key));
                mBodyHashMap.put(key, requestBody);
            }
        }
        mModel.updateProject(mBodyHashMap).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView)) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.updateProject(status);
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }

        });
    }

    public void dictList(DictPojo pojo) {
        mModel.dictList(pojo).subscribe(new DefaultCallBackObserver<DictListBean>() {
            @Override
            public void responseSuccess(DictListBean proListBean) {
                mIView.dictList(proListBean, pojo.getCode());
            }

            @Override
            public void responseFail(DictListBean proListBean) {

            }
        });
    }

    public void ossToken() {
        mModel.ossToken().subscribe(new DefaultCallBackObserver<OSSBean>() {
            @Override
            public void responseSuccess(OSSBean bean) {
                mIView.ossToken(bean);
            }

            @Override
            public void responseFail(OSSBean proListBean) {

            }
        });
    }

    public void projectDetail(ProjectDetailPojo pojo) {
        mModel.projectDetail(pojo).subscribe(new DefaultCallBackObserver<ProjectDetialBean>(new LoaddingErrorImplTip(mIView)) {
            @Override
            public void responseSuccess(ProjectDetialBean bean) {
                mIView.proDetail(bean);
            }

            @Override
            public void responseFail(ProjectDetialBean bean) {
                ToastUtils.init().showInfoToast(mIView, "请求出错");
            }
        });
    }
}
