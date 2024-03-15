package com.wpt.servlet;/**
 * @author wpt@onlying.cn
 * @date 2024/3/15 0:23
 */

import com.wpt.tomcat.http.WptRequest;
import com.wpt.tomcat.http.WptResponse;

import java.io.IOException;

/**
 * @projectName: wpt-tomcat
 * @package: com.wpt.servlet
 * @className: WptHttpServlet
 * @author: wpt
 * @description: TODO
 * @date: 2024/3/15 0:23
 * @version: 1.0
 */
public abstract class WptHttpServlet implements WptServlet{
    public void service(WptRequest request, WptResponse response) throws IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())){
            this.doGet(request,response);
        }else if ("POST".equalsIgnoreCase(request.getMethod())){
            this.doPost(request,response);
        }
    }
    //模板设计模式，，让WptHttpServlet子类 WptCalServlet实现

    public abstract void doGet(WptRequest request,WptResponse response);
    public abstract void doPost(WptRequest request,WptResponse response);

}

