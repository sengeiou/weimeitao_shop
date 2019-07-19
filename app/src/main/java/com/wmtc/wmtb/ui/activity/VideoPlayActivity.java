package com.wmtc.wmtb.ui.activity;

import android.content.res.Configuration;
import android.view.View;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wmtc.wmtb.R;

import java.io.File;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;

/**
 * Created by Obl on 2019/6/19.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class VideoPlayActivity extends SuperBaseActivity {

    private StandardGSYVideoPlayer mVideoPlayer;
    private OrientationUtils mOrientationUtils;
    public boolean isPlay = false;
    public boolean isPause;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_video_play;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mVideoPlayer = view.findViewById(R.id.player);
        //外部辅助的旋转，帮助全屏

        mOrientationUtils = new OrientationUtils(this, mVideoPlayer);
        //初始化不打开外部的旋转
        mOrientationUtils.setEnable(false);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        String url = mBundle.getString("url");
        boolean isLocal = mBundle.getBoolean("isLocal");
        gsyVideoOption
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(url)
                .setCacheWithPlay(isLocal)
                .setCachePath(new File(url))
                .setVideoTitle("")
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        mOrientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                        if (mOrientationUtils != null) {
                            mOrientationUtils.backToProtVideo();
                        }
                    }
                }).setLockClickListener((view1, lock) -> {
                    if (mOrientationUtils != null) {
                        //配合下方的onConfigurationChanged
                        mOrientationUtils.setEnable(!lock);
                    }
                }).build(mVideoPlayer);

        mVideoPlayer.getFullscreenButton().setOnClickListener(v -> {
            //直接横屏
            mOrientationUtils.resolveByClick();

            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            mVideoPlayer.startWindowFullscreen(mActivity, true, true);
        });
        mVideoPlayer.startPlayLogic();
    }

    @Override
    public void onBackPressed() {
        if (mOrientationUtils != null) {
            mOrientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        mVideoPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        mVideoPlayer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            mVideoPlayer.getCurrentPlayer().release();
        }
        if (mOrientationUtils != null)
            mOrientationUtils.releaseListener();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            mVideoPlayer.onConfigurationChanged(this, newConfig, mOrientationUtils, true, true);
        }
    }
}
