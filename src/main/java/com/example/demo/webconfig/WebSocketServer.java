package com.example.demo.webconfig;
import com.alibaba.fastjson.JSON;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * WebSocket服务
 */
@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    //存放会话对象
    private static Map<String, Session> sessionMap = new HashMap();
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("客户端：" + sid + "建立连接");
        this.session=session;
//        System.out.println("这是session"+session);
//        System.out.println("这是username"+sid);
        sessionMap.put(sid, session);
        System.out.println(sessionMap);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @SneakyThrows
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) throws IOException {
        //json字符串转成map集合
        Map<String,Object> map=JSON.parseObject(message);
        String touer= map.get("to").toString();
        String username= map.get("username").toString();
        String messages = (String)  map.get("message");
        System.out.println(messages);
        System.out.println("发送给"+touer);
        System.out.println("发送信息"+username);
        Session touser = sessionMap.get(touer);
        System.out.println("111"+touser);
        if (touser==null){

        }
        else {
            touser.getBasicRemote().sendText(message);
        }
        System.out.println("收到来自客户端：" + sid + "的信息:" + message);
        System.out.println(sessionMap);
    }

    /**
     * 连接关闭调用的方法
     *
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("连接断开:" + sid);
        sessionMap.remove(sid);
    }

    /**
     * 群发
     *
     * @param message
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                //服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
