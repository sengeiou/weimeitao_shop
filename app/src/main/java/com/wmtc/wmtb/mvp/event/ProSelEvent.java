package com.wmtc.wmtb.mvp.event;

import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/3.
 * com.wmtc.wmtb.mvp.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProSelEvent {
    public String projectName, rate, projectId,propTime;

    public ProSelEvent(String projectName, String rate, String projectId,String propTime) {
        this.projectName = projectName;
        this.rate = rate;
        this.projectId = projectId;
        this.propTime = propTime;
    }
}
