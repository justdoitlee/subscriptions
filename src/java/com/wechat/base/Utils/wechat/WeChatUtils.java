package com.wechat.base.Utils.wechat;

import com.sun.deploy.net.HttpResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wechat.connect.domain.Menu;
import com.wechat.connect.domain.MsgType;
import com.wechat.message.response.Article;
import com.wechat.message.response.MusicMessage;
import com.wechat.message.response.NewsMessage;
import com.wechat.message.response.TextMessage;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lizhi on 2016/10/29.
 */
public class WeChatUtils {
    private static Logger log = LoggerFactory.getLogger(WeChatUtils.class);

    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    // 菜单创建（POST） 限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /**
     * 判断是否是QQ表情
     *
     * @param content
     * @return
     */
    public static boolean isQqFace(String content) {
        boolean result = false;

        // 判断QQ表情的正则表达式
        String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
        Pattern p = Pattern.compile(qqfaceRegex);
        Matcher m = p.matcher(content);
        if (m.matches()) {
            result = true;
        }
        return result;
    }

    /**
     * emoji表情转换(hex -> utf-16)
     *
     * @param hexEmoji
     * @return
     */
    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 音乐消息对象转换成xml
     *
     * @param musicMessage 音乐消息对象
     * @return xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    /**
     * 图文消息对象转换成xml
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * 扩展xstream，使其支持CDATA块
     *
     * @date 2013-05-19
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });


    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }


    /**
     * 获取access_token
     *
     * @param appid     凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;

        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    /**
     * 创建菜单
     *
     * @param menu        菜单实例
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;

        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.fromObject(menu).toString();
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }

    public static boolean sendTemplateMsg(String token, Template template) {
        boolean flag = false;
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", token);
        JSONObject jsonResult = httpRequest(requestUrl, "POST", template.toJSON());
        if (jsonResult != null) {
            int errorCode = jsonResult.getInt("errcode");
            String errorMessage = jsonResult.getString("errmsg");
            if (errorCode == 0) {
                flag = true;
            } else {
                System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
                flag = false;
            }
        }
        return flag;
    }

    public static boolean sendPic(String token, String jsonMsg) {
        boolean result = false;
        //请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", token);
        //发送客服消息
        JSONObject jsonObject = httpRequest(requestUrl, "POST", jsonMsg);
        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
                System.out.println("客服消息发送成功errorCode:{" + errorCode + "},errmsg:{" + errorMsg + "}");
            } else {
                System.out.println("客服消息发送失败errorCode:{" + errorCode + "},errmsg:{" + errorMsg + "}");
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        Template tem = new Template();
//        tem.setTemplateId("RVisRPuvBUGayWKlyDaAr11nkLlozsJe2NSLAdnO3m4");
//        tem.setTopColor("#00DD00");
////                tem.setToUser("o8j_OwdMD7X-RtfhUb5ig4lwuSos");
//        tem.setToUser("o-xNQv9nuFsGbph-fDZKwyNyeYEg");
//        tem.setUrl("https://zhidao.baidu.com/question/1304159949319689899.html");
//        List<TemplateParam> paras = new ArrayList<TemplateParam>();
//        paras.add(new TemplateParam("first", "我们已收到您的货款，开始为您打包商品，请耐心等待: )", "#FF3333"));
//        paras.add(new TemplateParam("keyword1", "¥20.00", "#0044BB"));
//        paras.add(new TemplateParam("keyword2", "火烧牛干巴", "#0044BB"));
//        paras.add(new TemplateParam("keyword3", "感谢你对我们商城的支持!!!!", "#AAAAAA"));
//        paras.add(new TemplateParam("keyword4", "感谢你对我们商城的支持!!!!", "#AAAAAA"));
//        paras.add(new TemplateParam("remark", "感谢你对我们商城的支持!!!!", "#AAAAAA"));
//        tem.setTemplateParamList(paras);
        AccessToken token = WeChatUtils.getAccessToken("wxa0ab22c43c40d7c4", "0416abf483fa1387b54f8b6c662ec2a9");
//        boolean result = WeChatUtils.sendTemplateMsg(token.getToken(), tem);

        String strJson = "{\"touser\" :\"oe7rSjlz1flhx7HP3-DnlgrpobqM\",";
        strJson += "\"msgtype\":\"news\",";
        strJson += "\"news\":{";
        strJson += "\"articles\":\"Hello World\"";
        strJson += "}}";


        List list = new ArrayList();
        list.add("o-xNQv9nuFsGbph-fDZKwyNyeYEg");
        list.add("o-xNQv5uHxpTyGlAcwqIu7E0hYbw");
        list.add("o-xNQv7eZyopCuUS-xZURnaKkMo0");
        list.add("o-xNQv0lUWbPQPgc-GbUmx-B2V7M");
        list.add("o-xNQv6gRAHQJT6o8TCid8A4kBRc");
        list.add("o-xNQv-Ho13yRxGfYtHjJXxAyWz0");
        for(int i = 0 ;i<list.size();i++){
            String a = "{\n" +
                    "    \"touser\":\""+list.get(i)+"\",\n" +
                    "    \"msgtype\":\"news\",\n" +
                    "    \"news\":{\n" +
                    "        \"articles\": [\n" +
                    "         {\n" +
                    "             \"title\":\"Happy Day\",\n" +
                    "             \"description\":\"Is Really A Happy Day\",\n" +
                    "             \"url\":\"http://ruanwen.cishangongde.cn/index.php?g=&m=Wei&a=index&id=1\",\n" +
                    "             \"picurl\":\"http://cdn.duitang.com/uploads/item/201408/24/20140824235002_wP4Br.thumb.224_0.jpeg\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "             \"title\":\"Happy Day\",\n" +
                    "             \"description\":\"Is Really A Happy Day\",\n" +
                    "             \"url\":\"http://ruanwen.cishangongde.cn/index.php?g=&m=Wei&a=index&id=1\",\n" +
                    "             \"picurl\":\"http://cdn.duitang.com/uploads/item/201408/24/20140824235002_wP4Br.thumb.224_0.jpeg\"\n" +
                    "         }\n" +
                    "         ]\n" +
                    "    }\n" +
                    "}";
            WeChatUtils.sendPic(token.getToken(), a);
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }
    }

}
