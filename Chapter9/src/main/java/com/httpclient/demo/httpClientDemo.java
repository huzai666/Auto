package com.httpclient.demo;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class httpClientDemo {

    @Test
    public void getDemo1() throws IOException {

        String result;
        // new一个httpget请求
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        // 这是一个client
        CloseableHttpClient client = HttpClientBuilder.create().build();
        // 执行get请求得到返回值
        CloseableHttpResponse response = client.execute(httpGet);
        // 拿到返回值全部内容转成String
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

    }
}
