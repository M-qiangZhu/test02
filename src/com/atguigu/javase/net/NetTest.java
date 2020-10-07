package com.atguigu.javase.net;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class NetTest {

    @Test
    public void exerServer() throws IOException {
        ServerSocket server = new ServerSocket(8888);
        while (true) {
            System.out.println("服务器在8888端口监听......");
            Socket socket1 = server.accept();// 监听端口
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OutputStream outputStream = socket1.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                        bufferedWriter.write("你好，客户端，我是服务器，当前时间为 : " + LocalDateTime.now());
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

    }

    @Test
    public void exerClient() throws IOException {
        Socket socket2 = new Socket("127.0.0.1", 8888);
        System.out.println("socket2 : " + socket2);
        InputStream inputStream = socket2.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        System.out.println("s = " + s);

        bufferedReader.close();
        socket2.close();
    }



    @Test
    public void server() throws IOException {
        ServerSocket server = new ServerSocket(9999); // 服务器绑定了本机的9999端口，其他程序不能抢
        Socket socket1 = server.accept();// 监听端口
        System.out.println("socket1 : " + socket1);
        InputStream inputStream = socket1.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        System.out.println(s);

        // 关闭资源的顺序是从后向前
        bufferedReader.close();
        socket1.close();
    }

    @Test
    public void client() throws IOException {
        Socket socket2 = new Socket("127.0.0.1", 9999);
        System.out.println("socket2 : " + socket2);
        OutputStream outputStream = socket2.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("你好，服务器，我是客户端，今天天气不错...");
        bufferedWriter.newLine();
        bufferedWriter.flush();

        // 关闭资源的顺序是从后向前
        bufferedWriter.close();
        socket2.close();
    }

}
