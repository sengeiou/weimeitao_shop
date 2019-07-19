package com.wmtc.wmtb.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.util.DisplayUtils;


public class CustomDialog extends Dialog implements OnClickListener {

	private TextView mTvTitle;
	private TextView mTvContent;
	private TextView mTvLine;
	private View mVLine;
	private Button mBtnLeft;
	private Button mBtnRight;
	private EditText mEtContent;
	private OnCustomDialogClickListener mListener;
	private ContentType mContentType = ContentType.TEXTVIEW;
	public CustomDialog(Context context) {
		super(context, R.style.dialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_custom);
		Window window = getWindow();
		LayoutParams layoutParams = window.getAttributes();
		layoutParams.width = context.getResources().getDisplayMetrics().widthPixels
				- DisplayUtils.dip2px(context, 80);
		window.setAttributes(layoutParams);
		mTvTitle = (TextView) findViewById(R.id.dialog_custom_tv_title);
		mTvContent = (TextView) findViewById(R.id.dialog_custom_tv_content);
		mTvLine = (TextView) findViewById(R.id.tv_cus_line);
		mVLine = findViewById(R.id.dialog_custom_v_line);
		mBtnLeft = (Button) findViewById(R.id.dialog_custom_btn_left);
		mBtnRight = (Button) findViewById(R.id.dialog_custom_btn_right);
		mBtnLeft.setOnClickListener(this);
		mBtnRight.setOnClickListener(this);

	}
	public CustomDialog(Context context, ContentType type) {
		super(context, R.style.dialog);
		Window window = getWindow();
		LayoutParams layoutParams = window.getAttributes();
		layoutParams.width = context.getResources().getDisplayMetrics().widthPixels
				- DisplayUtils.dip2px(context, 80);
		window.setAttributes(layoutParams);
		switch (type) {
		case EDITTEXT:
//			this.mContentType = ContentType.EDITTEXT;
//			setContentView(R.layout.dialog_custom_edittext);
////			Window window = getWindow();
////			LayoutParams layoutParams = window.getAttributes();
////			layoutParams.width = context.getResources().getDisplayMetrics().widthPixels
////					- DisplayUtils.dip2px(context, 80);
////			window.setAttributes(layoutParams);
//			mTvTitle = (TextView) findViewById(R.id.dialog_custom_tv_title);
//			mEtContent = (EditText) findViewById(R.id.dialog_custom_et_content);
//			mBtnLeft = (Button) findViewById(R.id.dialog_custom_btn_left);
//			mBtnRight = (Button) findViewById(R.id.dialog_custom_btn_right);
//			mBtnLeft.setOnClickListener(this);
//			mBtnRight.setOnClickListener(this);
			break;
			case NONE:
				this.mContentType = ContentType.NONE;
				setContentView(R.layout.dialog_content);
				mTvTitle = (TextView)findViewById(R.id.dialog_custom_tv_title);

//				layoutParams.width = context.getResources().getDisplayMetrics().widthPixels
//						- DisplayUtils.dip2px(context, 80);
//				window.setAttributes(layoutParams);

				break;
		}
	}

	@Override
	public void onClick(View v) {
		if (mListener == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.dialog_custom_btn_left:
			mListener.onClick(this, Type.LEFT);
			break;

		case R.id.dialog_custom_btn_right:
			mListener.onClick(this, Type.RIGHT);
			break;
		}
	}

	public CustomDialog init(CharSequence title, CharSequence content,
			CharSequence left, CharSequence right,
			OnCustomDialogClickListener listener) {
		switch (mContentType) {
		case TEXTVIEW:
			mTvTitle.setText(title);
			mTvContent.setText(content);
			mBtnLeft.setText(left);
			mBtnRight.setText(right);
			mListener = listener;
			break;

		case EDITTEXT:
			mTvTitle.setText(title);
			mEtContent.setHint(content);
			mBtnLeft.setText(left);
			mBtnRight.setText(right);
			mListener = listener;
			break;
			case NONE:
				mTvTitle.setText(title);
//				mEtContent.setHint(content);
//				mBtnLeft.setText(left);
//				mBtnRight.setText(right);
//				mListener = listener;
				break;

		}

		return this;
	}

	public View getContentView() {
		if (mContentType == ContentType.EDITTEXT) {
			return mEtContent;
		} else {
			return mTvContent;
		}
	}

	public String getEtContent() {
		if (mContentType == ContentType.EDITTEXT) {
			return mEtContent.getText().toString().trim();
		}
		return null;
	}

	public void setDismissButton(Type type) {
		switch (type){
			case  LEFT:
				mVLine.setVisibility(View.GONE);
				mBtnLeft.setVisibility(type == Type.LEFT ? View.GONE : View.VISIBLE);
				break;
			case  RIGHT:
				mVLine.setVisibility(View.GONE);
				mBtnRight.setVisibility(type == Type.RIGHT ? View.GONE : View.VISIBLE);
				break;
			case TEXTVIEW:
				mTvLine.setVisibility(View.GONE);
				mTvContent.setVisibility(type== Type.TEXTVIEW?View.GONE:View.VISIBLE);
				break;

		}
	}

	public interface OnCustomDialogClickListener {
		public abstract void onClick(CustomDialog dialog, Type type);
	}

	public enum Type {
		LEFT, RIGHT,TEXTVIEW,NONE
	}

	public enum ContentType {
		EDITTEXT, TEXTVIEW,NONE
	}
}
