package jiguang.chat.activity.ui.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.ui.activity.OrderDetailsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import jiguang.chat.activity.adapter.ChatAdapter;
import jiguang.chat.activity.adapter.CommonFragmentPagerAdapter;
import jiguang.chat.activity.enity.FullImageInfo;
import jiguang.chat.activity.enity.MessageInfo;
import jiguang.chat.activity.ui.fragment.ChatEmotionFragment;
import jiguang.chat.activity.ui.fragment.ChatFunctionFragment;
import jiguang.chat.activity.util.Constants;
import jiguang.chat.activity.util.GlobalOnItemClickManagerUtils;
import jiguang.chat.activity.util.MediaManager;
import jiguang.chat.activity.widget.EmotionInputDetector;
import jiguang.chat.activity.widget.NoScrollViewPager;
import jiguang.chat.activity.widget.StateButton;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;

import static com.wmtc.wmtb.base.WmtApplication.C_JMKEY;

/**
 * Created by Obl on 2019/3/30.
 * com.wmtc.myapplication
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CustomChatActivity extends CommonWmtActivity {
    EasyRecyclerView mEasyRecyclerView;
    ImageView emotionVoice;
    EditText editText;
    TextView voiceText;
    ImageView emotionButton;
    ImageView emotionAdd;
    StateButton emotionSend;
    NoScrollViewPager viewpager;
    RelativeLayout emotionLayout;

    private EmotionInputDetector mDetector;
    private ArrayList<Fragment> fragments;
    private ChatEmotionFragment chatEmotionFragment;
    private ChatFunctionFragment chatFunctionFragment;
    private CommonFragmentPagerAdapter adapter;

    private ChatAdapter chatAdapter;
    private LinearLayoutManager layoutManager;
    private List<MessageInfo> messageInfos;
    //录音相关
    int animationRes = 0;
    int res = 0;
    AnimationDrawable animationDrawable = null;
    private ImageView animView;
    private String mIdName;

    @Override
    public int initAddLayout() {
        return R.layout.activity_main1;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        JMessageClient.registerEventReceiver(this);
        EventBus.getDefault().register(this);
        mIdName = mBundle.getString("name");
        LogUtil.e(mIdName + "==============================");
        mEasyRecyclerView = findViewById(R.id.chat_list);
        emotionVoice = findViewById(R.id.emotion_voice);
        editText = findViewById(R.id.edit_text);
        voiceText = findViewById(R.id.voice_text);
        emotionButton = findViewById(R.id.emotion_button);
        emotionAdd = findViewById(R.id.emotion_add);
        emotionSend = findViewById(R.id.emotion_send);
        viewpager = findViewById(R.id.viewpager);
        emotionLayout = findViewById(R.id.emotion_layout);
        initWidget();
        mTvToolRight.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    private void initWidget() {
        fragments = new ArrayList<>();
        chatEmotionFragment = new ChatEmotionFragment();
        fragments.add(chatEmotionFragment);
        chatFunctionFragment = new ChatFunctionFragment();
        fragments.add(chatFunctionFragment);
        adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);

        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .setViewPager(viewpager)
                .bindToContent(mEasyRecyclerView)
                .bindToEditText(editText)
                .bindToEmotionButton(emotionButton)
                .bindToAddButton(emotionAdd)
                .bindToSendButton(emotionSend)
                .bindToVoiceButton(emotionVoice)
                .bindToVoiceText(voiceText)
                .build();

        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(editText);

        chatAdapter = new ChatAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mEasyRecyclerView.setLayoutManager(layoutManager);
        mEasyRecyclerView.setAdapter(chatAdapter);
        mEasyRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        chatAdapter.notifyDataSetChanged();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        mDetector.hideEmotionLayout(false);
                        mDetector.hideSoftInput();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        chatAdapter.addItemClickListener(itemClickListener);
        Conversation conversation = JMessageClient.getSingleConversation(mIdName, C_JMKEY);
        messageInfos = new ArrayList<>();
        if (conversation != null) {
            List<Message> allMessage = conversation.getAllMessage();
            if (allMessage != null) {
                loadData(allMessage);
            } else {
                createTextMsg();
            }
        } else {
            createTextMsg();
        }
    }

    private void loadData(List<Message> allMessage) {

        for (Message message : allMessage) {
            MessageInfo messageInfo = new MessageInfo();
            MessageContent content = message.getContent();
            if (content.getContentType().equals(ContentType.text)) {
                if (MessageDirect.receive.equals(message.getDirect())) {
                    messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
                    messageInfo.setHeader(message.getFromUser().getAvatar());
                } else {
                    messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
                    messageInfo.setHeader(WmtApplication.user_avatar);
                }
                TextContent textContent = (TextContent) content;
                messageInfo.setContent(textContent.getText());
                messageInfos.add(messageInfo);
            } else if (content.getContentType().equals(ContentType.custom)) {
                CustomContent customContent = (CustomContent) message.getContent();
                Map stringValues = customContent.getAllStringValues();
                loadCustomData(stringValues);
            }
        }
        LogUtil.e(allMessage);
        chatAdapter.addAll(messageInfos);
    }

    public void createTextMsg() {
        if ("admin".equals(mBundle.getString("name"))) {
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setContent("您好，有什么需要帮您");
            messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
            messageInfo.setHeader("");
            chatAdapter.add(messageInfo);
        }
    }

    public void createGoodsMsg() {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", "商品标题");
        map.put("url", "oss/asdasda.png");
        map.put("price", "1");
        map.put("orderId", "12313123123123");
        map.put("type", "jm_goods");
        Message customMessage = JMessageClient.createSingleCustomMessage("admin", WmtApplication.C_JMKEY, map);
        LogUtil.e(customMessage);
        JMessageClient.sendMessage(customMessage);
        loadCustomData(map);

    }

    private void loadCustomData(Map map) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setContent((String) map.get("title"));
        messageInfo.setHeader((String) map.get("price"));
        messageInfo.setMsgId((String) map.get("orderId"));
        messageInfo.setImageUrl(WmtApplication.url_host + map.get("url"));
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_GOODS);
        messageInfos.add(messageInfo);
    }

    /**
     * 构造聊天数据
     */
    private void LoadData() {
        messageInfos = new ArrayList<>();

        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setContent("你好，欢迎使用Rance的聊天界面框架");
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
        messageInfo.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpgg");
        messageInfos.add(messageInfo);

        MessageInfo messageInfo1 = new MessageInfo();
        messageInfo1.setFilepath("http://www.trueme.net/bb_midi/welcome.wav");
        messageInfo1.setVoiceTime(3000);
        messageInfo1.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo1.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
        messageInfo1.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfos.add(messageInfo1);

        MessageInfo messageInfo2 = new MessageInfo();
        messageInfo2.setImageUrl("http://img4.imgtn.bdimg.com/it/u=1800788429,176707229&fm=21&gp=0.jpg");
        messageInfo2.setType(Constants.CHAT_ITEM_TYPE_LEFT);
        messageInfo2.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfos.add(messageInfo2);

        MessageInfo messageInfo3 = new MessageInfo();
        messageInfo3.setContent("[微笑][色][色][色]");
        messageInfo3.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo3.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
        messageInfo3.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfos.add(messageInfo3);

        chatAdapter.addAll(messageInfos);
    }

    /**
     * item点击事件
     */
    private ChatAdapter.onItemClickListener itemClickListener = new ChatAdapter.onItemClickListener() {
        @Override
        public void onHeaderClick(int position) {
        }

        @Override
        public void onImageClick(View view, int position) {
            MessageInfo messageInfo = chatAdapter.getAllData().get(position);
            if (messageInfo.getType() == Constants.CHAT_ITEM_TYPE_GOODS) {
                Bundle bundle = new Bundle();
                bundle.putString("id", messageInfo.getMsgId());
                ActivityUtils.init().start(mActivity, OrderDetailsActivity.class, "", bundle);
            } else {
                int location[] = new int[2];
                view.getLocationOnScreen(location);
                FullImageInfo fullImageInfo = new FullImageInfo();
                fullImageInfo.setLocationX(location[0]);
                fullImageInfo.setLocationY(location[1]);
                fullImageInfo.setWidth(view.getWidth());
                fullImageInfo.setHeight(view.getHeight());
                fullImageInfo.setImageUrl(messageInfos.get(position).getImageUrl());
                EventBus.getDefault().postSticky(fullImageInfo);
                startActivity(new Intent(mActivity, FullImageActivity.class));
                overridePendingTransition(0, 0);
            }
        }

        @Override
        public void onVoiceClick(final ImageView imageView, final int position) {
            if (animView != null) {
                animView.setImageResource(res);
                animView = null;
            }
            switch (messageInfos.get(position).getType()) {
                case 1:
                    animationRes = R.drawable.voice_left;
                    res = R.mipmap.icon_voice_left3;
                    break;
                case 2:
                    animationRes = R.drawable.voice_right;
                    res = R.mipmap.icon_voice_right3;
                    break;
            }
            animView = imageView;
            animView.setImageResource(animationRes);
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
            MediaManager.playSound(messageInfos.get(position).getFilepath(), mp -> animView.setImageResource(res));
        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(final MessageInfo messageInfo) {
        Message message = JMessageClient.createSingleTextMessage(mIdName, C_JMKEY, messageInfo.getContent());
        JMessageClient.sendMessage(message);
        messageInfo.setHeader(WmtApplication.user_avatar);
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
        messageInfos.add(messageInfo);
        chatAdapter.add(messageInfo);
        mEasyRecyclerView.scrollToPosition(chatAdapter.getCount() - 1);
    }

    public void onEventMainThread(MessageEvent event) {
        LogUtil.e("-----");
        Conversation conversation = JMessageClient.getSingleConversation(mIdName, C_JMKEY);
        messageInfos = new ArrayList<>();
        if (conversation != null) {
            List<Message> allMessage = conversation.getAllMessage();
            if (allMessage != null) {
                chatAdapter.clear();
                loadData(allMessage);
                chatAdapter.notifyDataSetChanged();
                mEasyRecyclerView.scrollToPosition(chatAdapter.getCount() - 1);
            } else {
                createTextMsg();
            }
        } else {
            createTextMsg();
        }
    }

    @Override
    public void onBackPressed() {
        if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BGAKeyboardUtil.closeKeyboard(mActivity);
        KeyboardUtils.init().hideSoftInput(this);
        EventBus.getDefault().removeStickyEvent(this);
        EventBus.getDefault().unregister(this);
        JMessageClient.unRegisterEventReceiver(this);
    }

}
