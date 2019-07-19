package com.wmtc.wmtb.ui.activity;

import android.view.View;
import android.widget.Button;

import com.wmtc.wmtb.R;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.activity.DefaultErrorActivity;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;

/**
 * Created by Obl on 2019/4/16.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ErrorActivity extends SuperBaseActivity {
    @Override
    protected int initRootLayout() {
        return R.layout.error_activity;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        final CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());
        Button restartButton = findViewById(R.id.btnRestart);
        if (config != null) {
            if (config.isShowRestartButton() && config.getRestartActivityClass() != null) {
                restartButton.setText(R.string.customactivityoncrash_error_activity_restart_app);
                restartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomActivityOnCrash.restartApplication(mActivity, config);
                    }
                });
            } else {
                restartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomActivityOnCrash.closeApplication(mActivity, config);
                    }
                });
            }

        }

    }
}
