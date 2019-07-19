package jiguang.chat.activity.adapter.holder;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import com.wmtc.wmtb.R;

import jiguang.chat.activity.adapter.ChatAdapter;
import jiguang.chat.activity.enity.MessageInfo;
import jiguang.chat.activity.util.Utils;
import jiguang.chat.activity.widget.BubbleImageView;
import jiguang.chat.activity.widget.GifTextView;
import top.jplayer.baseprolibrary.glide.GlideUtils;


/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 */
public class ChatAcceptViewHolder extends BaseViewHolder<MessageInfo> {

    private TextView chatItemDate;
    private ImageView chatItemHeader;
    private GifTextView chatItemContentText;
    private BubbleImageView chatItemContentImage;
    private ImageView chatItemVoice;
    private LinearLayout chatItemLayoutContent;
    private TextView chatItemVoiceTime;
    private ChatAdapter.onItemClickListener onItemClickListener;
    private Handler handler;

    public ChatAcceptViewHolder(ViewGroup parent, ChatAdapter.onItemClickListener onItemClickListener, Handler handler) {
        super(parent, R.layout.item_chat_accept);
        chatItemDate = (TextView) itemView.findViewById(R.id.chat_item_date);
        chatItemHeader = (ImageView) itemView.findViewById(R.id.chat_item_header);
        chatItemContentText = (GifTextView) itemView.findViewById(R.id.chat_item_content_text);
        chatItemContentImage = (BubbleImageView) itemView.findViewById(R.id.chat_item_content_image);
        chatItemVoice = (ImageView) itemView.findViewById(R.id.chat_item_voice);
        chatItemLayoutContent = (LinearLayout) itemView.findViewById(R.id.chat_item_layout_content);
        chatItemVoiceTime = (TextView) itemView.findViewById(R.id.chat_item_voice_time);
        this.onItemClickListener = onItemClickListener;
        this.handler = handler;
    }

    @Override
    public void setData(MessageInfo data) {
        chatItemDate.setText(data.getTime() != null ? data.getTime() : "");
        Glide.with(getContext()).load(data.getHeader()).apply(GlideUtils.init().options(R.drawable.a1)).into(chatItemHeader);
        chatItemHeader.setOnClickListener(v -> onItemClickListener.onHeaderClick(getDataPosition()));
        if (data.getContent() != null) {
            chatItemContentText.setSpanText(handler, data.getContent(), true);
            chatItemVoice.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.VISIBLE);
            chatItemLayoutContent.setVisibility(View.VISIBLE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemContentImage.setVisibility(View.GONE);
        } else if (data.getImageUrl() != null) {
            chatItemVoice.setVisibility(View.GONE);
            chatItemLayoutContent.setVisibility(View.GONE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.GONE);
            chatItemContentImage.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(data.getImageUrl()).apply(GlideUtils.init().options(R.drawable.a1)).into(chatItemContentImage);
            chatItemContentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onImageClick(chatItemContentImage, getDataPosition());
                }
            });
        } else if (data.getFilepath() != null) {
            chatItemVoice.setVisibility(View.VISIBLE);
            chatItemLayoutContent.setVisibility(View.VISIBLE);
            chatItemContentText.setVisibility(View.GONE);
            chatItemVoiceTime.setVisibility(View.VISIBLE);
            chatItemContentImage.setVisibility(View.GONE);
            chatItemVoiceTime.setText(Utils.formatTime(data.getVoiceTime()));
            chatItemLayoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onVoiceClick(chatItemVoice, getDataPosition());
                }
            });
        }
    }
}
