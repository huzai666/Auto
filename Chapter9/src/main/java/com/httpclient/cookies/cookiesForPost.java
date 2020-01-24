package com.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class cookiesForPost {

    // ResourceBundle是用来读取application文件的
    private ResourceBundle bundle;
    // 定义String类型的url，存储测试地址（域名）
    private String url;
    // 定义一个store来存储cookies
    private CookieStore store = new BasicCookieStore();


    // 拼接一下测试url
    @BeforeTest
    public void beforeTest(){

        bundle = ResourceBundle.getBundle("application",Locale.CHINA);
        url = bundle.getString("test.url");

    }

    // 这是个可以返回cookies的post请求
    @Test
    public void testPostGetCookies() throws IOException {

        // 先拼一个完整的接口地址
        String uri = bundle.getString("post.response.cookies");
        String testUrl = this.url + uri;
        System.out.println(testUrl);

        // 定义一个client
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(this.store).build();

        // 定义一个post方法，把url放进去
        HttpPost post = new HttpPost(testUrl);

        // 现在需要+一下post请求的参数
        // 1）先看一下接口中的请求参数是json类型的，那么此时就需要弄一个jsonObject存储一下请求参  {"userName":"wanglu","pwd":"123"}
        // 2）先定义一个map，把参数放进去，再把map传给jsonObject
        // 这个不对，这个是传json数组的时候用的
//        Map<String,String> map = new HashMap();
//        map.put("userName","wanglu");
//        map.put("pwd","123");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName","wanglu");
        jsonObject.put("pwd","123");

        // 再用Entity给包裹一下
        StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");
        post.setEntity(entity);

        // 由于参数是json格式的，需要设置一下请求头（Header）的信息
        post.setHeader("Content-Type","application/json");

        //定义一个返回值，接受请求的返回
        HttpResponse response = client.execute(post);
        // 拆开返回值的实体并toString
        String result = EntityUtils.toString(response.getEntity());
        System.out.println(result);

        // 如果有cookie是存在store中的，从store中取一下cookies的值，定义一个list，遍历一下
        List<Cookie> cookies = store.getCookies();
        for (Cookie myCookies : cookies){
            String cookieName = myCookies.getName();
            String cookieValue =myCookies.getValue();
            System.out.println("返回的cookies是：" + cookieName + "=" + cookieValue);
        }

         System.out.println("------------------------------");
           
    }

    // 这是一个需要cookies的post请求mock
    @Test(dependsOnMethods = "testPostGetCookies")
    public void testSendPostCookies() throws IOException {

        // 常规拼一下请求的url
        String uri = bundle.getString("post.with.cookies");
        String textUrl = this.url + uri;
        System.out.println(textUrl);

        // 定义client
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(store).build();

        // 定义post请求
        HttpPost post = new HttpPost(textUrl);

        // 设置一下请求参数 "json":{"name":"xiaoming", "sex":"woman"}
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","xiaoming");
        jsonObject.put("sex","woman");

        StringEntity entity = new StringEntity(jsonObject.toString());
        post.setEntity(entity);

        // 设置一下header说明一下参数请求类型为json
        post.setHeader("Content-Type","application/json");

        // 设置一下需要请求时携带的cookies
        // 不需要设置，这client类型就是会默认带上cookiestore中的cookie去请求

        // 执行一下请求吧
        HttpResponse response = client.execute(post);

        // entity接收一下response，并转成String
        String result = EntityUtils.toString(response.getEntity());
        System.out.println(result);

        // 返回的结果是json类似的，需要用jsonObject解一下
        JSONObject resultJson = new JSONObject(result);
        int status = (Integer) resultJson.get("status");
        String string = resultJson.getString("xiaoming");

        Assert.assertEquals(status,200);
        Assert.assertEquals(string,"success");

    }
}
