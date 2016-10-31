package com.wechat.connect.domain;

/**
 * @author 李智
 * @date 2016/10/29
 *
 * 复杂按钮（父按钮）
 */
public class ComplexMenuButton extends MenuButton {
    private MenuButton[] sub_Menu_button;

    public MenuButton[] getSub_Menu_button() {
        return sub_Menu_button;
    }

    public void setSub_Menu_button(MenuButton[] sub_Menu_button) {
        this.sub_Menu_button = sub_Menu_button;
    }
}
