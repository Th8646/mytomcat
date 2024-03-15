package com.wpt.tomcat.handler;/**
 * @author wpt@onlying.cn
 * @date 2024/3/14 22:36
 */

import com.wpt.servlet.WptHttpServlet;
import com.wpt.tomcat.WptTomcat;
import com.wpt.tomcat.http.WptRequest;
import com.wpt.tomcat.http.WptResponse;
import com.wpt.servlet.WptCalServlet;

import java.io.*;
import java.net.Socket;

/**
 * @projectName: wpt-tomcat
 * @package: com.wpt.tomcat.handler
 * @className: WptResquesthandler
 * @author: wpt
 * @description: TODO
 * @date: 2024/3/14 22:36
 * @version: 1.0
 */
public class WptResquesthandler implements Runnable {

    private Socket socket = null;

    public WptResquesthandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        // 客户端--浏览器进行IO编程/交互

        try {
            InputStream inputStream = socket.getInputStream();
            WptRequest wptRequest = new WptRequest(inputStream);
//            String num1 = wptRequest.getParameter("num1");
//            String num2 = wptRequest.getParameter("num2");
//            System.out.println("请求的参数num1= " + num1);
//            System.out.println("uri=" + wptRequest.getUri());
//            System.out.println(wptRequest.getMethod());
//            // 字符流转换,进行数据接受
//            BufferedReader bufferedReader =
//                    new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
//            // 不同的线程在和浏览器交互
//            System.out.println("当前线程= "  +  Thread.currentThread().getName());
//            System.out.println("Tomcat接受的数据如下：");
//            String mes = null;
//            while ((mes = bufferedReader.readLine()) != null) {
//                // 空串退出
//                if (mes.length() == 0) {
//                    break;
//                }
//                System.out.println(mes);
//            }
            WptResponse wptResponse = new WptResponse(socket.getOutputStream());
//            String resp = wptResponse.respHeader+"<h1>hello world</h1>";
//            OutputStream outputStream = wptResponse.getOutputStream();
//            outputStream.write(resp.getBytes());

//         // 创建WptCalServlet对象
//            WptCalServlet wptCalServlet = new WptCalServlet();
//            wptCalServlet.doGet(wptRequest,wptResponse);

            //使用反射构建对象
            //1.得到uri,servletUrlMapping 的 url-pattern
            String uri = wptRequest.getUri();
            String servletName = WptTomcat.servletUrlMapping.get(uri);
            //2.通过uri---servletName---servlet实例
            WptHttpServlet wptHttpServlet =
                    WptTomcat.servletMapping.get(servletName);
            if (wptHttpServlet!=null){
                wptHttpServlet.service(wptRequest, wptResponse);
            }else {
                   //没有servlet返回404
                String resp = WptResponse.respHeader+"<h1>404 Not Found</h1>";
                OutputStream outputStream = wptResponse.getOutputStream();
                outputStream.write(resp.getBytes());
                outputStream.flush();
                outputStream.close();
            }
//            outputStream.flush();
//            outputStream.close();
//            inputStream.close();
            socket.close();

//            // 构建http响应头
//            String respHeader = "HTTP/1.1 200 OK\n" +
//                    "Content-Type: text/html;charset=utf-8\r\n\r\n";
//            String resp = respHeader + "<h1>Hello,World</h1>";
//            System.out.println("Tomcat 返回的数据是：");
//            System.out.println(resp);
//            //返回数据给浏览器--》封装成HTTP响应
//            OutputStream outputStream = socket.getOutputStream();
//            outputStream.write(resp.getBytes());
//            // 关闭流
//            outputStream.flush();
//            outputStream.close();
//            inputStream.close();
//            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
