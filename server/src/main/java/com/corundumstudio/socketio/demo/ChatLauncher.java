package com.corundumstudio.socketio.demo;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

public class ChatLauncher {

    public static void main(String[] args) throws InterruptedException {
    	
    	// 1. 创建配置
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        

        // 2. 创建SocketIOServer(基于Netty)
        final SocketIOServer server = new SocketIOServer(config);
        
        // 3. 添加事件处理.
        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        // 4. 启动(创建Netty并设置:Pipeline)
        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
