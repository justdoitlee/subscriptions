package com.wechat.message.request;

/**
 * Created by 李智 on 2016/10/29.
 * 图片消息
 */
public class ImageMessage extends BaseMessage{
    // 图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
