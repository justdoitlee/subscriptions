package com.wechat.connect.controller;

import com.wechat.base.Utils.wechat.SignUtil;
import com.wechat.connect.service.ConnectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lizhi on 2016/10/16.
 */
@Controller
@RequestMapping("/")
public class ConnectWeChat {
    private String Token = "123456789abcdefg";

    @RequestMapping(value = "weChatReply", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void chat(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet) {
            String signature = request.getParameter("signature");   // 微信加密签名
            String timestamp = request.getParameter("timestamp");   // 时间戳
            String nonce = request.getParameter("nonce");           // 随机数
            String echostr = request.getParameter("echostr");       // 随机字符串

            PrintWriter out = response.getWriter();
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
            }
            out.close();
            out = null;
        } else {
            // 进入POST聊天处理
            try {
                // 接收消息并返回消息
                acceptMessage(request, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理微信服务器发来的消息
     */
    public void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 调用核心业务类接收消息、处理消息
        String respMessage = ConnectService.processRequest(request);

        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
    }
}

