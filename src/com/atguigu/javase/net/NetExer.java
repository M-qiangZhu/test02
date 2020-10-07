package com.atguigu.javase.net;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class NetExer {
    @Test
    public void server() throws IOException {
        ServerSocket server = new ServerSocket(8888);
        while (true) {
            System.out.println("服务器在端口8888监听......");
            Socket socket1 = server.accept();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
                        bufferedWriter.write("我是服务器,欢迎光临, 当前时间 : " + LocalDateTime.now());
                        bufferedWriter.newLine();
                        bufferedWriter.flush();

                        bufferedWriter.close();
                        socket1.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        //server.close();
    }

    @Test
    public void client() throws IOException {
        Socket socket2 = new Socket("localhost", 8888);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
        String s = bufferedReader.readLine();
        System.out.println(s);

        bufferedReader.close();
        socket2.close();
    }
}
