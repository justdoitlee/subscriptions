package com.wechat.connect.domain;

/**
 * @author 李智
 * @date 2016/10/30
 *
 * view类型的菜单
 */
public class ViewMenuButton extends MenuButton{
    private String type;
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
