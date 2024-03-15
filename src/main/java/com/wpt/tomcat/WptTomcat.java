package com.wpt.tomcat;/**
 * @author wpt@onlying.cn
 * @date 2024/3/14 21:57
 */

import com.wpt.tomcat.handler.WptResquesthandler;
import com.wpt.servlet.WptHttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
    //1.存放容器servletMapping
    //-ConcurrentHashMap
    //key                     value
    //ServletName           对应的实例
    public static final ConcurrentHashMap<String, WptHttpServlet> servletMapping =
            new ConcurrentHashMap<String, WptHttpServlet>();

    // 2.存放容器-servletUrlMapping
    //-ConcurrentHashMap
    //key                         value
    //url-pattern          ServletName
    public static final ConcurrentHashMap<String, String> servletUrlMapping =
            new ConcurrentHashMap<String, String>();

    //对两个容器进行初始化
    public void init() {
        String path = WptTomcat.class.getResource("/").getPath();//得到web.xml的路径
        SAXReader saxReader = new SAXReader();
//        System.out.println("path" + path);
        try {
            Document document = saxReader.read(new File(path + "web.xml"));
            System.out.println("document= " + document);
            Element rootElement = document.getRootElement();//取根节点
            List<Element> elements = rootElement.elements();//根元素下的所有元素
            //遍历过滤到servlet  servlet-mapping
            for (Element element : elements) {
                if ("servlet".equals(element.getName())) {
                    //取servlet配置
                    // System.out.println("发现了servlet");
                    // 使用反射将servlet实例返给ServletMapping
                    Element servletName = element.element("servlet-name");
                    Element servletClass = element.element("servlet-class");
                    servletMapping.put(servletName.getText(),
                            (WptHttpServlet) Class.forName(servletClass.getText().trim()).newInstance());
                } else if ("servlet-mapping".equals(element.getName())) {
                   // System.out.println("发现了servlet-mapping");
                    Element servletName = element.element("servlet-name");
                    Element urlPattern = element.element("url-pattern");
                    servletUrlMapping.put(servletName.getText(),urlPattern.getText());

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //验证容器初始化
        System.out.println("servletMapping:"+servletMapping);
        System.out.println("servletUrlMapping:"+servletUrlMapping);
    }
    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("-------------------------------WptTomcat在8080端口监听------------------");
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                WptResquesthandler wptResquesthandler = new WptResquesthandler(socket);
                new Thread(wptResquesthandler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws IOException {

        WptTomcat wptTomcat = new WptTomcat();
        wptTomcat.init();
        wptTomcat.run();


        }
    }
