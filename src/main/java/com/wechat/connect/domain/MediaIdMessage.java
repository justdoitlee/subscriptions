package com.wechat.connect.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wechat.Utils.XStreamCDATA;

/**
 * Created by lizhi on 2016/10/17.
 */
public class MediaIdMessage {
    @XStreamAlias("MediaId")
    @XStreamCDATA
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

}