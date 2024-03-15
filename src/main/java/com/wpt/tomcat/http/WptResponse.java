package com.wpt.tomcat.http;/**
 * @author wpt@onlying.cn
 * @date 2024/3/15 0:29
 */

import java.io.OutputStream;

/**
 * @projectName: wpt-tomcat
 * @package: com.wpt.tomcat.http
 * @className: WptResponse
 * @author: wpt
 * @description: 用于封装OutputStream对象，可以通过该类返回http响应给浏览器或者客户端
 * @date: 2024/3/15 0:29
 * @version: 1.0
 */
public class WptResponse {
    private OutputStream outputStream = null;
    public static final String respHeader="HTTP/1.1 200 OK\r\n"+
            "Content-Type:text-html;charset=utf-8\r\n\r\n";
    public WptResponse(OutputStream outputStream){
        this.outputStream = outputStream;
    }
    //需要返回数据时，可以通过WptResponse的输出流完成，outputStream与Socket关联
    public OutputStream getOutputStream() {
        return outputStream;
    }
}
