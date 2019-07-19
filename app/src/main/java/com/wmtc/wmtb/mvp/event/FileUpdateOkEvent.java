package com.wmtc.wmtb.mvp.event;

/**
 * Created by Obl on 2019/4/3.
 * com.wmtc.wmtb.mvp.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FileUpdateOkEvent {
    public String fileUrl;
    public String type;

    public FileUpdateOkEvent(String fileUrl,String type) {
        this.fileUrl = fileUrl;
        this.type = type;
    }

    public FileUpdateOkEvent() {
    }
}
