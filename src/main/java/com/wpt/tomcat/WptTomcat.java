package com.wpt.tomcat;/**
 * @author wpt@onlying.cn
 * @date 2024/3/14 21:57
 */

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
            // 等待浏览器/客户端的连接,有链接过来就创建socket（浏览器客户端的数据通道）
            Socket socket = serverSocket.accept();
            // 接受浏览器发送的数据
            InputStream inputStream = socket.getInputStream();//字节流
            // 转换字符流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String mes = null;
            System.out.println("接收到浏览器发送数据:");
            while ((mes = bufferedReader.readLine()) != null) {
                if (mes.length() == 0) {
                    break;
                }
                System.out.println(mes);
            }

            // Tomcat回送数据
            OutputStream outputStream = socket.getOutputStream();
            //构建http响应消息头
            String respHeader = "HTTP/1.1 200 OK\n" +
                    "Content-Type: text/html;charset=utf-8\r\n\r\n";
            String resp = respHeader + "hello,world";

            System.out.println("Tomcat给浏览器回送的数据：");
            System.out.println(resp);
            outputStream.write(resp.getBytes());//等resp字符串以byte[]方式返回

            outputStream.flush();
            outputStream.close();
            inputStream.close();
            socket.close();


        }
    }

}
