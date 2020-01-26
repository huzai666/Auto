package com.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
public class MyGetMethod {


    // 可以返回cookie的get接口
    @RequestMapping( value = "/get/response/cookies", method = RequestMethod.GET)
    public String getCookies(HttpServletResponse response){

        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "恭喜你获得了返回的cookies";
    }

    // 需携带cookie才能请求的get接口
    @RequestMapping( value = "/get/with/cookies", method = RequestMethod.GET)
    public String sendCookie(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            return "请携带cookies请求";
        }
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")){
                return "cookie正确，请求成功6666";
            }
        }

        return "请携带cookies请求111";

    }

    // 需要请求参数的get接口，方式一：get/fruit/list?key=value&kay=value
    // 需求：类似商品列表的翻页，传入页码，返回产品列表
    @RequestMapping(value = "get/fruit/list")
    public Map<String, Double> getList(@RequestParam  Integer start, @RequestParam Integer end){

        Map<String, Double> maplist = new HashMap<String, Double>();
        maplist.put("苹果",4.5);
        maplist.put("香蕉",5.8);
        maplist.put("草莓",10.3);

        return maplist;
    }

    // 方式二：get/fruit/list/{start}/{end}
    @RequestMapping(value = "get/fruit/list/{start}/{end}")
    public Map<String, Double> getMyList(@PathVariable Integer start, @PathVariable Integer end) {

        Map<String, Double> maplist = new HashMap<String, Double>();
        maplist.put("苹果",4.5);
        maplist.put("香蕉",5.8);
        maplist.put("草莓",10.3);
        maplist.put("猕猴桃",6.99);

        return maplist;

    }
}
