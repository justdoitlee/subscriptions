package com.wechat.connect.service;

import com.alibaba.fastjson.JSON;
import com.wechat.base.Utils.wechat.*;
import com.wechat.connect.domain.MsgType;
import com.wechat.message.response.Article;
import com.wechat.message.response.NewsMessage;
import com.wechat.message.response.TextMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author 李智
 * @date 2016/10/29
 */
public class ConnectService {
    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // xml请求解析
            Map<String, String> requestMap = WeChatUtils.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            //发送来的消息
            String reqContent = requestMap.get("Content");

            // 文本消息
            if (msgType.equals(MsgType.REQ_MESSAGE_TYPE_TEXT)) {
//                String resultStr = JuheRobor.getAnswerRequest(requestMap.get("Content"));
//                //解析json字符串
//                Map maps = (Map) JSON.parse(resultStr);
//                if (maps.get("url") != null && maps.get("url") != "") {
//                    respMessage = newsMsg(requestMap, maps, "1");
//                } else {
//                    respMessage = textMsg(requestMap, maps);
//                }
                Map maps = new HashMap();
                maps.put("text", "精彩内容！");
                maps.put("url", "http://newruanwen.cishangongde.cn/index.php?g=Admin&m=Index&a=index");
//                respMessage = textMsg(requestMap, maps);
                respMessage = newsMsg(requestMap,maps,"1");
            }
            // 图片消息
            else if (msgType.equals(MsgType.REQ_MESSAGE_TYPE_IMAGE)) {
                Map maps = new HashMap();
                maps.put("text", "您发送的是图片消息！");
                respMessage = textMsg(requestMap,maps);

            }
            // 地理位置消息
            else if (msgType.equals(MsgType.REQ_MESSAGE_TYPE_LOCATION)) {
                Map maps = new HashMap();
                maps.put("text", "您发送的是地理位置消息！");
                respMessage = textMsg(requestMap, maps);
            }
            // 链接消息
            else if (msgType.equals(MsgType.REQ_MESSAGE_TYPE_LINK)) {
                Map maps = new HashMap();
                maps.put("text", "您发送的是链接消息！");
                respMessage = textMsg(requestMap, maps);
            }
            // 音频消息
            else if (msgType.equals(MsgType.REQ_MESSAGE_TYPE_VOICE)) {
                Map maps = new HashMap();
                maps.put("text", "您发送的是音频消息！");
                respMessage = textMsg(requestMap, maps);
            }
            // 事件推送
            else if (msgType.equals(MsgType.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MsgType.EVENT_TYPE_SUBSCRIBE)) {
                    Map maps = new HashMap();
                    maps.put("text", "谢谢您的关注！我会更加努力的发车");
                    respMessage = textMsg(requestMap, maps);
                }
                // 取消订阅
                else if (eventType.equals(MsgType.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MsgType.EVENT_TYPE_CLICK)) {
                    respMessage = clickMsg(requestMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }

    //主菜单
    public static String getMainMenu() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("您好，我是你爸爸，请回复数字选择服务：").append("\n\n");
        buffer.append("1  天气预报").append("\n");
        buffer.append("2  公交查询").append("\n");
        buffer.append("3  周边搜索").append("\n");
        buffer.append("4  歌曲点播").append("\n");
        buffer.append("5  经典游戏").append("\n");
        buffer.append("6  美女电台").append("\n");
        buffer.append("7  人脸识别").append("\n");
        buffer.append("8  聊天唠嗑").append("\n\n");
        buffer.append("回复“?”显示此帮助菜单");
        return buffer.toString();
    }

    //图文信息
    private static String newsMsg(Map<String, String> requestMap, Map maps, String level) {
        String respMessage = "";
        // 创建图文消息
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(requestMap.get("FromUserName"));
        newsMessage.setFromUserName(requestMap.get("ToUserName"));
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MsgType.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);

        Template tem = new Template();
        tem.setTemplateId("RVisRPuvBUGayWKlyDaAr11nkLlozsJe2NSLAdnO3m4");
        tem.setTopColor("#00DD00");
//                tem.setToUser("o8j_OwdMD7X-RtfhUb5ig4lwuSos");
        tem.setToUser(requestMap.get("FromUserName"));
        tem.setUrl("https://zhidao.baidu.com/question/1304159949319689899.html");
        List<TemplateParam> paras = new ArrayList<TemplateParam>();
        paras.add(new TemplateParam("first", "我们已收到您的货款，开始为您打包商品，请耐心等待: )", "#FF3333"));
        paras.add(new TemplateParam("keyword1", "¥20.00", "#0044BB"));
        paras.add(new TemplateParam("keyword2", "火烧牛干巴", "#0044BB"));
        paras.add(new TemplateParam("keyword3", "感谢你对我们商城的支持!!!!", "#AAAAAA"));
        paras.add(new TemplateParam("keyword4", "感谢你对我们商城的支持!!!!", "#AAAAAA"));
        paras.add(new TemplateParam("remark", "感谢你对我们商城的支持!!!!", "#AAAAAA"));
        tem.setTemplateParamList(paras);
        AccessToken token = WeChatUtils.getAccessToken("wxa0ab22c43c40d7c4", "0416abf483fa1387b54f8b6c662ec2a9");
        boolean result = WeChatUtils.sendTemplateMsg(token.getToken(), tem);

        List<Article> articleList = new ArrayList<Article>();
        // 单图文消息
        if ("1".equals(level)) {
            Article article = new Article();
            article.setTitle("往期精彩");
            article.setDescription(maps.get("text").toString());
            article.setPicUrl("http://cdn.duitang.com/uploads/item/201408/24/20140824235002_wP4Br.thumb.224_0.jpeg");
            article.setUrl(maps.get("url").toString());
            articleList.add(article);
            // 设置图文消息个数
            newsMessage.setArticleCount(articleList.size());
            // 设置图文消息包含的图文集合
            newsMessage.setArticles(articleList);
            // 将图文消息对象转换成xml字符串
            respMessage = WeChatUtils.newsMessageToXml(newsMessage);
        }
        // 单图文消息---不含图片
        else if ("2".equals(level)) {
            Article article = new Article();
            article.setTitle("李智的微信订阅号");
            // 图文消息中可以使用QQ表情、符号表情
            article.setDescription("hello world，" + WeChatUtils.emoji(0x1F6B9)
                    + "李智，一个活泼开朗的男孩~！\n\n目前正在厦门实习。\n\n准备找个女朋友 哈哈哈。");
            // 将图片置为空
            article.setPicUrl("");
            article.setUrl("http://blog.csdn.net/melod_bc");
            articleList.add(article);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = WeChatUtils.newsMessageToXml(newsMessage);
        }
        // 多图文消息
        else if ("3".equals(level)) {
            Article article1 = new Article();
            article1.setTitle("李智的微信订阅号\n猛男心理倾述中心");
            article1.setDescription("");
            article1.setPicUrl("http://cdn.duitang.com/uploads/item/201408/24/20140824235002_wP4Br.thumb.224_0.jpeg");
            article1.setUrl("http://blog.csdn.net/melod_bc");

            Article article2 = new Article();
            article2.setTitle("李智的微博\n欢迎关注");
            article2.setDescription("");
            article2.setPicUrl("http://tva1.sinaimg.cn/crop.0.0.180.180.180/878af494jw1e8qgp5bmzyj2050050aa8.jpg");
            article2.setUrl("http://weibo.com/2274030740/profile?topnav=1&wvr=6&is_all=1");

            Article article3 = new Article();
            article3.setTitle("李智的github\n欢迎star");
            article3.setDescription("");
            article3.setPicUrl("http://img2.imgtn.bdimg.com/it/u=47637596,3356844648&fm=21&gp=0.jpg");
            article3.setUrl("https://github.com/JustDoItLee/subscriptions.git");

            articleList.add(article1);
            articleList.add(article2);
            articleList.add(article3);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = WeChatUtils.newsMessageToXml(newsMessage);
        }
        // 多图文消息---首条消息不含图片
        else if ("4".equals(level)) {
            Article article1 = new Article();
            article1.setTitle("欢迎来到李智的微信订阅号\n猛男心理倾述中心");
            article1.setDescription("");
            // 将图片置为空
            article1.setPicUrl("");
            article1.setUrl("http://blog.csdn.net/melod_bc");

            Article article2 = new Article();
            article2.setTitle("李智的博客\n欢迎观看");
            article2.setDescription("");
            article2.setPicUrl("http://cdn.duitang.com/uploads/item/201408/24/20140824235002_wP4Br.thumb.224_0.jpeg");
            article2.setUrl("http://blog.csdn.net/melod_bc");

            Article article3 = new Article();
            article3.setTitle("李智的微博\n欢迎关注");
            article3.setDescription("");
            article3.setPicUrl("http://tva1.sinaimg.cn/crop.0.0.180.180.180/878af494jw1e8qgp5bmzyj2050050aa8.jpg");
            article3.setUrl("http://weibo.com/2274030740/profile?topnav=1&wvr=6&is_all=1");

            Article article4 = new Article();
            article4.setTitle("李智的github\n欢迎star");
            article4.setDescription("");
            article4.setPicUrl("http://img2.imgtn.bdimg.com/it/u=47637596,3356844648&fm=21&gp=0.jpg");
            article4.setUrl("https://github.com/JustDoItLee/subscriptions.git");

            articleList.add(article1);
            articleList.add(article2);
            articleList.add(article3);
            articleList.add(article4);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = WeChatUtils.newsMessageToXml(newsMessage);
        }
        // 多图文消息---最后一条消息不含图片
        else if ("5".equals(level)) {
            Article article1 = new Article();
            article1.setTitle("李智的博客\n欢迎观看");
            article1.setDescription("");
            article1.setPicUrl("http://cdn.duitang.com/uploads/item/201408/24/20140824235002_wP4Br.thumb.224_0.jpeg");
            article1.setUrl("http://blog.csdn.net/melod_bc");

            Article article2 = new Article();
            article2.setTitle("李智的微博\n欢迎关注");
            article2.setDescription("");
            article2.setPicUrl("http://tva1.sinaimg.cn/crop.0.0.180.180.180/878af494jw1e8qgp5bmzyj2050050aa8.jpg");
            article2.setUrl("http://weibo.com/2274030740/profile?topnav=1&wvr=6&is_all=1");

            Article article3 = new Article();
            article3.setTitle("如果开心你就拍拍手~");
            article3.setDescription("");
            // 将图片置为空
            article3.setPicUrl("");
            article3.setUrl("https://github.com/JustDoItLee/subscriptions.git");

            articleList.add(article1);
            articleList.add(article2);
            articleList.add(article3);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = WeChatUtils.newsMessageToXml(newsMessage);
        }
        return respMessage;
    }

    //文本信息
    private static String textMsg(Map<String, String> requestMap, Map maps) {
        String respMessage = "";
        // 默认回复此文本消息
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(requestMap.get("FromUserName"));
        textMessage.setFromUserName(requestMap.get("ToUserName"));
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MsgType.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);
        // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
        textMessage.setContent(maps.get("text").toString());
        // 将文本消息对象转换成xml字符串
        respMessage = WeChatUtils.textMessageToXml(textMessage);
        return respMessage;
    }

    //点击事件
    private static String clickMsg(Map<String, String> requestMap) {
        // 事件KEY值，与创建自定义菜单时指定的KEY值对应
        String eventKey = requestMap.get("EventKey");
        String respContent = "";
        String respMessage = "";
        if (eventKey.equals("11")) {
            respContent = "天气预报菜单项被点击！";
        } else if (eventKey.equals("12")) {
            respContent = "公交查询菜单项被点击！";
        } else if (eventKey.equals("13")) {
            respContent = "周边搜索菜单项被点击！";
        } else if (eventKey.equals("14")) {
            respContent = "历史上的今天菜单项被点击！";
        } else if (eventKey.equals("21")) {
            respContent = "歌曲点播菜单项被点击！";
        } else if (eventKey.equals("22")) {
            respContent = "经典游戏菜单项被点击！";
        } else if (eventKey.equals("23")) {
            respContent = "美女电台菜单项被点击！";
        } else if (eventKey.equals("24")) {
            respContent = "人脸识别菜单项被点击！";
        } else if (eventKey.equals("25")) {
            respContent = "聊天唠嗑菜单项被点击！";
        } else if (eventKey.equals("31")) {
            respContent = "Q友圈菜单项被点击！";
        } else if (eventKey.equals("32")) {
            respContent = "电影排行榜菜单项被点击！";
        } else if (eventKey.equals("33")) {
            respContent = "幽默笑话菜单项被点击！";
        }
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(requestMap.get("FromUserName"));
        textMessage.setFromUserName(requestMap.get("ToUserName"));
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MsgType.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);
        // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
        textMessage.setContent(respContent);
        // 将文本消息对象转换成xml字符串
        respMessage = WeChatUtils.textMessageToXml(textMessage);
        return respMessage;
    }

}
