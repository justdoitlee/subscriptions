package com.wechat.message.request;

/**
 * Created by 李智 on 2016/10/29.
 * 文本消息
 */
public class TextMessage extends BaseMessage{
    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
