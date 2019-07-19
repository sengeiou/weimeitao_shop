package com.wmtc.wmtb.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wmtc.wmtb.R;

import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialogFragment;

/**
 * Created by Obl on 2019/3/23.
 * com.wmtc.wmtb.ui.dialog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FixEndPriceDialog extends BaseCustomDialog {

    private EditText mEditDel;
    private EditText mEditDelEnd;
    private int fenMoney;
    private TextView mTvRealMoney;

    public FixEndPriceDialog(Context context) {
        super(context);
    }

    public FixEndPriceDialog setMoney(String fenMoney) {
        this.fenMoney = Integer.parseInt(fenMoney);
        mTvRealMoney.setText(StringUtils.init().fixNullStrMoney(fenMoney + "", "待实收尾款：￥"));
        setRealMoney(mEditDelEnd, this.fenMoney);
        return this;
    }

    private void setRealMoney(EditText editText, int fenMoney) {
        editText.setText(StringUtils.init().fixNullStrMoney(fenMoney + ""));
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> dismiss());
        mEditDel = view.findViewById(R.id.editDel);
        mTvRealMoney = view.findViewById(R.id.tvRealMoney);
        mEditDelEnd = view.findViewById(R.id.editDelEnd);

        mEditDel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isNotBlank(s) && s.length() > 0 && mEditDel.isFocused()) {
                    if (s.toString().lastIndexOf(".") >= s.length() - 1) {
                        s = s.subSequence(0, s.length() - 1);
                    }
                    float parseFloat = 0;
                    try {
                        parseFloat = Float.parseFloat(s.toString());
                    } catch (NumberFormatException e) {
                        ToastUtils.init().showInfoToast(mContentView.getContext(), "金额输入错误");
                        mEditDel.setText("");
                        e.printStackTrace();
                    }
                    if (parseFloat < 1) {
                        ToastUtils.init().showInfoToast(mContentView.getContext(), "优惠金额不能小于1元");
                        mEditDel.setText("");
                        return;
                    }
                    int fixMoney = (int) (parseFloat * 100);
                    if (fenMoney <= fixMoney) {
                        ToastUtils.init().showInfoToast(mContentView.getContext(), "优惠金额不能大于待收尾款");
                        mEditDel.setText("");
                        return;
                    }
                    setRealMoney(mEditDelEnd, fenMoney - fixMoney);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditDelEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isNotBlank(s) && s.length() > 0 && mEditDelEnd.isFocused()) {
                    if (s.toString().lastIndexOf(".") >= s.length() - 1) {
                        s = s.subSequence(0, s.length() - 1);
                    }
                    float parseFloat = 0;
                    try {
                        parseFloat = Float.parseFloat(s.toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        ToastUtils.init().showInfoToast(mContentView.getContext(), "金额输入错误");
                        mEditDelEnd.setText("");
                    }
                    if (parseFloat < 1) {
                        ToastUtils.init().showInfoToast(mContentView.getContext(), "优惠金额不能小于1元");
                        mEditDelEnd.setText("");
                        return;
                    }
                    int fixMoney = (int) (parseFloat * 100);
                    if (fenMoney <= fixMoney) {
                        ToastUtils.init().showInfoToast(mContentView.getContext(), "优惠金额不能大于待收尾款");
                        mEditDelEnd.setText("");
                        return;
                    }
                    setRealMoney(mEditDel, fenMoney - fixMoney);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void setSureListener(SureListener listener) {
        listener.onSure(mEditDelEnd);
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(9);
    }

    @Override
    public void dismiss() {
        BGAKeyboardUtil.closeKeyboard(this);
        super.dismiss();
    }

    @Override
    public int setHeight() {
        return super.setHeight();
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_fix_end;
    }
}
