package com.wmtc.wmtb.mvp.presenter;


import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.DictListBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.DictCodePojo;
import com.wmtc.wmtb.ui.dialog.DialogCancelPush;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/6/26.
 * com.wmtc.wmt.appoint.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class DialogCancelPushPresenter extends BasePresenter<DialogCancelPush> {

    private final CommonModel mModel;

    public DialogCancelPushPresenter(DialogCancelPush iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getDict(String dict) {
        DictCodePojo dictCodePojo = new DictCodePojo();
        dictCodePojo.setCode(dict);
        mModel.getxingqu(dictCodePojo).subscribe(new DefaultCallBackObserver<DictListBean>(this) {
            @Override
            public void responseSuccess(DictListBean bean) {
                mIView.getList(bean);
            }

            @Override
            public void responseFail(DictListBean bean) {

            }
        });
    }

}
