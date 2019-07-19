package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.MsgListBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.MsgPojo;
import com.wmtc.wmtb.ui.fragment.NoticeMsgListFragment;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/3/30.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MsgPresenter extends BasePresenter<NoticeMsgListFragment> {

    private final CommonModel mCommonModel;

    public MsgPresenter(NoticeMsgListFragment iView) {
        super(iView);
        mCommonModel = new CommonModel(WmtServer.class);
    }

    public void refreshData(MsgPojo pojo) {
        mCommonModel.getMessageList(pojo).subscribe(new DefaultCallBackObserver<MsgListBean>() {
            @Override
            public void responseSuccess(MsgListBean msgListBean) {
                mIView.refreshDate(msgListBean);
            }

            @Override
            public void responseFail(MsgListBean msgListBean) {

            }
        });
    }
}
