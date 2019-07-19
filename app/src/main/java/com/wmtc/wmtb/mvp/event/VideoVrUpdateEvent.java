package com.wmtc.wmtb.mvp.event;

/**
 * Created by Obl on 2019/6/20.
 * com.wmtc.wmtb.mvp.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class VideoVrUpdateEvent {
    public String vrUrl;
    public String videoUrl;

    public VideoVrUpdateEvent(String vrUrl, String videoUrl) {
        this.vrUrl = vrUrl;
        this.videoUrl = videoUrl;
    }

    public VideoVrUpdateEvent() {
    }
}
