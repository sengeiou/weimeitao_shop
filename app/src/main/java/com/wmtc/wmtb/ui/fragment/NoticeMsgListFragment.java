package com.wmtc.wmtb.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.MsgListBean;
import com.wmtc.wmtb.mvp.pojo.MsgPojo;
import com.wmtc.wmtb.mvp.presenter.MsgPresenter;
import com.wmtc.wmtb.ui.adapter.NoticeMsgAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import jiguang.chat.activity.ui.activity.CustomChatActivity;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/3/30.
 * com.wmtc.wmtb.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class NoticeMsgListFragment extends SuperBaseFragment {

    private NoticeMsgAdapter mAdapter;
    private MsgPresenter mMsgPresenter;
    private MsgPojo mPojo;
    private String type;
    private int position;

    @Override
    public int initLayout() {
        return R.layout.fragment_notice_list;
    }

    @Override
    protected void initData(View rootView) {
        initRefreshStatusView(rootView);
        mAdapter = new NoticeMsgAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mPojo = new MsgPojo();
        mPojo.setUserId(WmtApplication.user_shopId);
        mMsgPresenter = new MsgPresenter(this);
        showLoading();
        mAdapter.setOnItemClickListener((adapter, view, position1) -> {
            if (position == 1 || position == 2) {
                MsgListBean.DataBean bean = mAdapter.getData().get(position1);
                Bundle bundle = new Bundle();
                bundle.putString("name", bean.chatId);
                ActivityUtils.init().start(mActivity, CustomChatActivity.class, bean.messageTitle, bundle);
            }
        });
    }


    @Override
    public void refreshStart() {
        super.refreshStart();
        if (position == 2) {
            getAdmin();
        } else if (position == 1) {
            getChatList();
        } else {
            mPojo.setMessageType(type);
            mMsgPresenter.refreshData(mPojo);
        }
    }

    public void getAdmin() {

        JMessageClient.getUserInfo("admin", WmtApplication.C_JMKEY, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                responseSuccess();
                if (userInfo != null) {
                    ArrayList<MsgListBean.DataBean> data = new ArrayList<>();
                    MsgListBean.DataBean dataBean = new MsgListBean.DataBean();
                    Conversation adminCon = JMessageClient.getSingleConversation("admin", WmtApplication.C_JMKEY);
                    Message latestMessage = adminCon.getLatestMessage();
                    if (latestMessage != null && latestMessage.getContentType().equals(ContentType.text)) {
                        TextContent textContent = (TextContent) latestMessage.getContent();
                        dataBean.createTime = "";
                        dataBean.messageTitle = adminCon.getTitle();
                        dataBean.messageContent = textContent.getText();
                        dataBean.chatId = adminCon.getTargetId();
                        data.add(dataBean);
                    }
                    mAdapter.setNewData(data);
                } else {
                    showEmpty();
                }
                LogUtil.e(userInfo);
            }
        });

    }

    public void refreshDate(int position, String type) {
        this.type = type;
        this.position = position;
        if (position == 2) {
            getAdmin();
        } else if (position == 1) {
            getChatList();
        } else {
            mPojo.setMessageType(type);
            mMsgPresenter.refreshData(mPojo);
        }
    }

    private void getChatList() {
        List<Conversation> conversationList = JMessageClient.getConversationList();
        if (conversationList != null) {
            ArrayList<MsgListBean.DataBean> data = new ArrayList<>();
            for (Conversation conversation : conversationList) {
                MsgListBean.DataBean dataBean = new MsgListBean.DataBean();
                Message latestMessage = conversation.getLatestMessage();
                if ("admin".equals(conversation.getTargetId())) {
                    continue;
                }
                if (latestMessage != null && latestMessage.getContentType().equals(ContentType.text)) {
                    TextContent textContent = (TextContent) latestMessage.getContent();
                    dataBean.createTime = "";
                    dataBean.messageTitle = conversation.getTitle();
                    dataBean.messageContent = textContent.getText();
                    dataBean.chatId = conversation.getTargetId();
                }
                data.add(dataBean);
            }
            mAdapter.setNewData(data);
            LogUtil.e("---------x----");
        } else {
            showEmpty();
        }
        LogUtil.e(conversationList);
        responseSuccess();

    }

    public void refreshDate(MsgListBean msgListBean) {
        responseSuccess();
        if (msgListBean != null && msgListBean.data != null && msgListBean.data.size() > 0) {
            mAdapter.setNewData(msgListBean.data);
        } else {
            showEmpty();
        }
    }
}
