package com.httpclient.cookies;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class cookiesForGet {


    private String url;
    private ResourceBundle bundle;
    // 定义cookiestore存储cookie
    private CookieStore store = new BasicCookieStore();


    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    // 这是一个无参的get请求，并且返回cookie
    @Test
    public void testGetCookies() throws IOException {

        // 合成请求url
        String uri = bundle.getString("get.response.cookies.uri");
        String testUrl = this.url + uri;
        System.out.println(testUrl);

        // 定义httpclient，并且是包含cookies的
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(store).build();
        // 定义是get方法
        HttpGet httpGet = new HttpGet(testUrl);

        // 定义请求的返回体
        CloseableHttpResponse response = client.execute(httpGet);
        // 返回体中取出来实体，并且转成String格式
        String result =  EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        // 获取cookies
        List<Cookie> cookies = store.getCookies();
        for ( Cookie myCookie : cookies){
            String key = myCookie.getName();
            String value = myCookie.getValue();
            System.out.println("恭喜你成功获取cookies：" + key + "=" + value);
        }

        System.out.println("------------------------------");

    }

    // 写一个有参的get请求吧，并且需要携带参数才能请求
    @Test(dependsOnMethods = "testGetCookies")
    public void testSendCookies() throws IOException, URISyntaxException {

        // 合成请求url
        String uri = bundle.getString("get.with.cookies.uri");
        String testUrl = this.url + uri;

        // 定义一个uriBuilder来拼接带参数的get请求url;name=张曼玉&age=20
        URIBuilder uriBuilder = new URIBuilder(testUrl);
        uriBuilder.addParameter("name","张曼玉");
        uriBuilder.addParameter("age","20");
        System.out.println(uriBuilder.build());

        // 声明一个client对象，用来进行方法的执行,并设置cookies信息
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(this.store).build();
        // 一个get方法
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        // 定义返回体，接受httpclient的get请求
        CloseableHttpResponse response = client.execute(httpGet);
        // 返回体中拆出来实体，并转成String
        String entity = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(entity);



    }
}
