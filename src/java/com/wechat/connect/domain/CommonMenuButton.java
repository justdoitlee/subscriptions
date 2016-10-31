package com.wechat.connect.domain;

/**
 * @author 李智
 * @date 2016/10/29
 *
 * 普通按钮（子按钮）
 */
public class CommonMenuButton extends MenuButton {
    private String type;
    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
