package com.controller;

import com.bean.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/", description = "这是一堆post方法")
@RequestMapping("/v1")
public class MyPostMethon {

    private Cookie cookie;

    // 登录接口，返回cookie
    @ApiOperation(value = "/login", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName", required = true) String userName,
                        @RequestParam(value = "qqq", required = true) String passWorld){
        if (userName.equals("littlebai") && passWorld.equals("123qwe")){
            cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return "恭喜你登录成功";
        }

        return "用户名或密码错误";

    }


    // 现在是写一个返回用户列表
    @ApiOperation(value = "", httpMethod = "POST")
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public String getUserList(HttpServletRequest request,
                             @RequestBody Users users){
        Users backUsers;
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies){
            if ( c.getName().equals("login")
                    && c.getValue().equals("true")
                    && users.getUserName().equals("littlebai")
                    && users.getPassword().equals("123qwe")){
                backUsers = new Users();
                backUsers.setUser("白敬亭");
                backUsers.setAge("25");
                backUsers.setSex("man");
                return backUsers.toString();
            }
        }

        return "用户名或密码错误";
    }

}
