package com.wpt.tomcat;/**
 * @author wpt@onlying.cn
 * @date 2024/3/14 21:57
 */

import com.wpt.tomcat.handler.WptResquesthandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @projectName: wpt-tomcat
 * @package: com.wpt.tomcat
 * @className: WptTomcat
 * @author: wpt
 * @description: v1-完成客户端浏览器的通信
 * @date: 2024/3/14 21:57
 * @version: 1.0
 */
public class WptTomcat {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8080);

        System.out.println("==========mytomcat在8080端口监听==============");

        while (!serverSocket.isClosed()) {
            // 等待浏览器/客户端的连接,连接成功就创建socket（浏览器客户端的数据通道）
            Socket socket = serverSocket.accept();

            // 创建一个线程对象，并把socket给该线程
            new Thread(new WptResquesthandler(socket)).start();



        }
    }

}
