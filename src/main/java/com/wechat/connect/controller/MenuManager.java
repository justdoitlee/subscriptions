package com.wechat.connect.controller;

import com.wechat.base.Utils.wechat.AccessToken;
import com.wechat.base.Utils.wechat.WeChatUtils;
import com.wechat.connect.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 李智
 * @date 2016/10/29
 * <p>
 * 菜单管理器类
 */
public class MenuManager {
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);

    public static void main(String[] args) {
        // 第三方用户唯一凭证
        String appId = "wx2c3ac21c0d4a0cbc";
        // 第三方用户唯一凭证密钥
        String appSecret = "dcfb6738f2a6a714b61433a5d7df0e63";

        // 调用接口获取access_token
        AccessToken at = WeChatUtils.getAccessToken(appId, appSecret);

        if (null != at) {
            // 调用接口创建菜单
            int result = WeChatUtils.createMenu(getMenu(), at.getToken());

            // 判断菜单创建结果
            if (0 == result)
                log.info("菜单创建成功！");
            else
                log.info("菜单创建失败，错误码：" + result);
        }
    }

    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getMenu() {
        CommonMenuButton btn11 = new CommonMenuButton();
        btn11.setName("天气预报");
        btn11.setType("click");
        btn11.setKey("11");

        CommonMenuButton btn12 = new CommonMenuButton();
        btn12.setName("公交查询");
        btn12.setType("click");
        btn12.setKey("12");

        CommonMenuButton btn13 = new CommonMenuButton();
        btn13.setName("周边搜索");
        btn13.setType("click");
        btn13.setKey("13");

        CommonMenuButton btn14 = new CommonMenuButton();
        btn14.setName("历史上的今天");
        btn14.setType("click");
        btn14.setKey("14");

        CommonMenuButton btn21 = new CommonMenuButton();
        btn21.setName("歌曲点播");
        btn21.setType("click");
        btn21.setKey("21");

        CommonMenuButton btn22 = new CommonMenuButton();
        btn22.setName("经典游戏");
        btn22.setType("click");
        btn22.setKey("22");

        CommonMenuButton btn23 = new CommonMenuButton();
        btn23.setName("美女电台");
        btn23.setType("click");
        btn23.setKey("23");

        CommonMenuButton btn24 = new CommonMenuButton();
        btn24.setName("人脸识别");
        btn24.setType("click");
        btn24.setKey("24");

        CommonMenuButton btn25 = new CommonMenuButton();
        btn25.setName("聊天唠嗑");
        btn25.setType("click");
        btn25.setKey("25");

        CommonMenuButton btn31 = new CommonMenuButton();
        btn31.setName("Q友圈");
        btn31.setType("click");
        btn31.setKey("31");

        CommonMenuButton btn32 = new CommonMenuButton();
        btn32.setName("电影排行榜");
        btn32.setType("click");
        btn32.setKey("32");

        CommonMenuButton btn33 = new CommonMenuButton();
        btn33.setName("幽默笑话");
        btn33.setType("click");
        btn33.setKey("33");

        ViewMenuButton btn34 = new ViewMenuButton();
        btn34.setName("使用帮助");
        btn34.setType("view");
        btn34.setUrl("http://blog.csdn.net/melod_bc");

        ComplexMenuButton mainBtn1 = new ComplexMenuButton();
        mainBtn1.setName("生活助手");
        mainBtn1.setSub_Menu_button(new MenuButton[]{btn11, btn12, btn13, btn14});

        ComplexMenuButton mainBtn2 = new ComplexMenuButton();
        mainBtn2.setName("休闲驿站");
        mainBtn2.setSub_Menu_button(new MenuButton[]{btn21, btn22, btn23, btn24, btn25});

        ComplexMenuButton mainBtn3 = new ComplexMenuButton();
        mainBtn3.setName("更多体验");
        mainBtn3.setSub_Menu_button(new MenuButton[]{btn31, btn32, btn33, btn34});

        /**
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new MenuButton[]{mainBtn1, mainBtn2, mainBtn3});

        return menu;
    }
}
