package com.wmtc.wmtb.mvp.event;

import java.util.Map;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.mvp.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LocalSelEvent {
    public Map<String, String> stringMap;

    public LocalSelEvent(Map<String, String> stringMap) {
        this.stringMap = stringMap;
    }
}
