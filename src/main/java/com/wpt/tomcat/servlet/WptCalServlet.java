package com.wpt.servlet;/**
 * @author wpt@onlying.cn
 * @date 2024/3/15 0:23
 */

import com.wpt.tomcat.http.WptRequest;
import com.wpt.tomcat.http.WptResponse;
import com.wpt.utils.WebUtils;

import javax.servlet.ServletConfig;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @projectName: wpt-tomcat
 * @package: com.wpt.servlet
 * @className: WptCalServlet
 * @author: wpt
 * @description: TODO
 * @date: 2024/3/15 0:23
 * @version: 1.0
 */
public class WptCalServlet extends WptHttpServlet {

    public void doGet(WptRequest request, WptResponse response) {
        //业务代码，完成计算任务
        int num1 = WebUtils.parseInt(request.getParameter("num1"), 0);
        int num2 = WebUtils.parseInt(request.getParameter("num2"), 0);
        int sum = num1 + num2;

        // 返回数据
        OutputStream outputStream = response.getOutputStream();//outputStream 和 当前连接的socket关联
        String respMes = WptResponse.respHeader +
                "<h1>" + num1 + "+" + num2 + "=" + sum + "WptTomcatV3</h1>";
        try {
            outputStream.write(respMes.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void doPost(WptRequest request, WptResponse response) {
        this.doGet(request, response);
    }

    public void init() throws Exception {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
