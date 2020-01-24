package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


@RestController
public class MyGetMethod {

    @RequestMapping( value = "/get/response/cookies", method = RequestMethod.GET)
    public String getCookies(HttpServletResponse response){

        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "恭喜你获得了返回的cookies";
    }

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
}
