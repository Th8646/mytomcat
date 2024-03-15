package com.wpt.servlet;

import com.wpt.tomcat.http.WptRequest;
import com.wpt.tomcat.http.WptResponse;

import javax.servlet.ServletConfig;
import java.io.IOException;

/**
 * @author wpt@onlying.cn
 * @date 2024/3/15 0:22
 */
public interface WptServlet {
    void init() throws Exception;

    ServletConfig getServletConfig();

    void service(WptRequest request, WptResponse response) throws IOException;

    String getServletInfo();

    void destroy();
}
