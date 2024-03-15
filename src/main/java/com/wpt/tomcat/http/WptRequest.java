package com.wpt.tomcat.http;/**
 * @author wpt@onlying.cn
 * @date 2024/3/15 0:29
 */

import jdk.internal.util.xml.impl.Input;
import org.omg.PortableInterceptor.ServerRequestInfo;

import java.io.*;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @projectName: wpt-tomcat
 * @package: com.wpt.tomcat.http
 * @className: WptRequest
 * @author: wpt
 * @description: 用于封装http请求数据  例如：method uri 参数列表
 * @date: 2024/3/15 0:29
 * @version: 1.0
 */
public class WptRequest {
    private String method;
    private String uri;
    private HashMap<String, String> parametersMapping = new HashMap<String, String>();//参数列表不定


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    public String getParameter(String name) {
        if (parametersMapping.containsKey(name)) {
            return parametersMapping.get(name);
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return "WptRequest{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", parametersMapping=" + parametersMapping +
                '}';
    }

    // inputStream和 对应的http请求的socket关联
    public WptRequest(InputStream inputStream) throws UnsupportedEncodingException {
        init(inputStream);
    }

    private void init(InputStream inputStream) {
        System.out.println("init被调用");
        //字节流转换
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            /**
             * GET /wpt_tomcat/calServlet?num1=10&num2=30 HTTP/1.1
             * Host: localhost:8080
             * User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:123.0) Gecko/20100101 Firefox/123.0
             */
            String requestLine = bufferedReader.readLine();
            //POST  - /wpt_tomcat/calServlet?num1=10&num2=30 - HTTP/1.1
            String[] requestLineArr = requestLine.split(" ");
            // 得到请求方式
            method = requestLineArr[0];//POST
            // 取出uri
            int index = requestLineArr[1].indexOf("?");
            if (index == -1) {//没有参数列表
                uri = requestLineArr[1];
            } else {
                uri = requestLineArr[1].substring(0, index);//取[0,index)
                //获取参数列表
                //parameters=num1=10&num2=30
                String parameters = requestLineArr[1].substring(index + 1);
                //parametersPair=["num1=10","num2=20"]
                String[] parametersPair = parameters.split("&");
                if (null != parametersPair && !"".equals(parametersPair)) {//校验，防止传入空参数
                    //分割处理参数列表
                    for (String parameterPair : parametersPair) {
                        //parameterVal=["num1","10"]
                        String[] parameterVal = parameterPair.split("=");
                        if (parameterVal.length == 2) {
                            //放入parametersMapping
                            parametersMapping.put(parameterVal[0], parameterVal[2]);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
