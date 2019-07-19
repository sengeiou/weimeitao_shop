package com.wmtc.wmtb.mvp.event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Obl on 2019/3/26.
 * com.wmtc.wmtb.mvp.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FileSelListEvent {
    public ArrayList<File> data;
    public String type;
    public int max;
    public String delIds;

    public FileSelListEvent(ArrayList<File> data, String type, int max, String delIds) {
        this.data = data;
        this.type = type;
        this.max = max;
        this.delIds = delIds;
    }
}
