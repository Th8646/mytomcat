package com.wpt.utils;/**
 * @author wpt@onlying.cn
 * @date 2024/3/14 20:23
 */

/**
 * @projectName: wpt-tomcat
 * @package: com.wpt.utils
 * @className: WebUtils
 * @author: wpt
 * @description: TODO
 * @date: 2024/3/14 20:23
 * @version: 1.0
 */
public class WebUtils {
    /**
     * 将字符串转换成int，如果数据不合法返回默认值
     * @param strNum
     * @param defalutVal
     * @return
     */
    public static int parseInt(String strNum,int defalutVal){
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println("转换失败，格式不正确");
        }
        return defalutVal;
    }
}
