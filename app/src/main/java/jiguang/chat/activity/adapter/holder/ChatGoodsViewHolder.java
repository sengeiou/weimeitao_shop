package jiguang.chat.activity.adapter.holder;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.wmtc.wmtb.R;

import jiguang.chat.activity.adapter.ChatAdapter;
import jiguang.chat.activity.enity.MessageInfo;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/27.
 * jiguang.chat.activity.adapter.holder
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ChatGoodsViewHolder extends BaseViewHolder<MessageInfo> {
    ViewGroup parent;
    ChatAdapter.onItemClickListener onItemClickListener;
    Handler handler;
    public TextView mTvTitle;
    public TextView mTvMoney;
    public ImageView mIvSrc;
    public ImageView mIvDetail;

    public ChatGoodsViewHolder(ViewGroup parent, ChatAdapter.onItemClickListener onItemClickListener, Handler handler) {
        super(parent, R.layout.item_chat_goods);
        this.parent = parent;
        this.onItemClickListener = onItemClickListener;
        this.handler = handler;
        mTvTitle = itemView.findViewById(R.id.tvTitle);
        mTvMoney = itemView.findViewById(R.id.tvMoney);
        mIvSrc = itemView.findViewById(R.id.ivSrc);
        mIvDetail = itemView.findViewById(R.id.ivDetail);
    }

    @Override
    public void setData(MessageInfo data) {
        super.setData(data);
        Glide.with(getContext()).load(data.getImageUrl()).apply(GlideUtils.init().options(R.drawable.wmt_default)).into(mIvSrc);
        mTvTitle.setText(StringUtils.init().fixNullStr(data.getContent()));
        mTvMoney.setText(StringUtils.init().fixNullStrMoney(data.getHeader(), "￥"));
        mIvDetail.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onImageClick(mIvDetail, getDataPosition());
            }
        });
    }
}
