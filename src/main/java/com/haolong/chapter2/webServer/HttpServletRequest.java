package com.haolong.chapter2.webServer;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weihaolong
 * @date 2020-02-05 17:59:00
 * @description request请求， 可以去看一下servlet-api源码， @See Servlet 里面的源码大有益处。
 * 里面我就进行部分实现进行抽取来进行自我实现。
 */
class HttpServletRequest {

    /**
     * 请求的方式 GET、POST、PUT等
     */
    private String method;

    /**
     * 请求的协议版本
     */
    private String version;

    /**
     * 请求的uri
     */
    private String uri;

    private String host;

    /**
     * ？号后面的参数
     */
    private String queryString;

    // query 部分的 K-V
    private Map<String, Object> queryMap;

    /**
     * 头部请求数据
     */
    private Map<String, String> headers = new HashMap<>(16);

    private Enumeration<String> params;

    private Enumeration<String> values;

    /**
     * 读取流信息， 进行数据填充
     * @param inputStream
     */
    public HttpServletRequest(InputStream inputStream){
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        try {
            while ((len = inputStream.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, "UTF-8"));
                if(len < bytes.length){
                    break;
                }
            }
            //进行拆解
            String[] params = sb.toString().split("\n");

            //判断是否是HTTP请求
            if(params.length != 0 && params[0] != ""){
                String[] requesyInfo;
                requesyInfo = params[0].split(" ");
                if(requesyInfo.length != 3 || !requesyInfo[2].contains("HTTP")){
                    System.out.println("报错");
                }
                method = requesyInfo[0];

                //还需要特殊处理
                int queryIndex = requesyInfo[1].indexOf('?');
                if(queryIndex == -1){
                    uri = requesyInfo[1];
                }else{
                    uri = requesyInfo[1].substring(0, queryIndex);

                    //查找后面的数据
                    queryString = requesyInfo[1].substring(queryIndex + 1);
                    //key-value对应的值

                }

                version = requesyInfo[2];
            }
//            System.out.println("方法:" + method);
//            System.out.println("uri:" + uri);
//            System.out.println("版本:" + version);

            for(int i = 1; i < params.length; i++){
                //直接转化为key和value结构
                int index = params[i].indexOf(":");
                if(index != -1){
                    headers.put(params[i].substring(0, index), params[i].substring(index + 1));
                }
            }

            host = headers.get("Host");

            //下面需要进行header的处理
//            inputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取请求的uri地址
     * @return
     */
    public String getRequestUri(){
        return uri;
    }

    public void setRequestUri(String uri){
        this.uri = uri;
    }

    public Object getAttribute(String name){
        if(queryMap != null){
            return queryMap.get("name");
        }
        return null;
    }


}
