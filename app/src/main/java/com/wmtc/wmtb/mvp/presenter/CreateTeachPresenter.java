package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.TeachDetailBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.TeachInfoPojo;
import com.wmtc.wmtb.ui.activity.CreateTeachActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.net.tip.PostImplTip;

/**
 * Created by Obl on 2019/3/27.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CreateTeachPresenter extends BasePresenter<CreateTeachActivity> {

    private final CommonModel mCommonModel;

    public CreateTeachPresenter(CreateTeachActivity iView) {
        super(iView);
        mCommonModel = new CommonModel(WmtServer.class);
    }

    public void saveTechnicianAttach(List<File> fileList, Map<String, String> stringMap, String status) {
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
        mCommonModel.saveTechnicianAttach(mBodyHashMap).subscribe(new DefaultCallBackObserver<BaseBean>(new PostImplTip(mIView)) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.saveTeach();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }

        });
    }

    public void updateTechnicianDetail(TeachInfoPojo pojo) {
        mCommonModel.updateTechnicianDetail(pojo).subscribe(new DefaultCallBackObserver<BaseBean>() {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.netOk("3");
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });

    }

    public void updateTechnicianAttach(File file, Map<String, String> stringMap, String status) {
        HashMap<String, RequestBody> mBodyHashMap = new HashMap<>();
        if (stringMap != null && stringMap.size() > 0) {
            for (String key : stringMap.keySet()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                        stringMap.get(key) == null ? "" : stringMap.get(key));
                mBodyHashMap.put(key, requestBody);
            }
        }
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        mBodyHashMap.put("file\"; filename=\"" + file.getName(), fileBody);

        mCommonModel.updateTechnicianAttach(mBodyHashMap).subscribe(new DefaultCallBackObserver<BaseBean>() {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.netOk(status);
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }

        });
    }

    public void getTechnicianDetail(String id) {
        mCommonModel.getTechnicianDetail(id).subscribe(new DefaultCallBackObserver<TeachDetailBean>() {
            @Override
            public void responseSuccess(TeachDetailBean bean) {
                mIView.teachInfo(bean);
            }

            @Override
            public void responseFail(TeachDetailBean bean) {

            }
        });

    }
}
