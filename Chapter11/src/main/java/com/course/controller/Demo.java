package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@Api(value = "v1", description = "这是返回用户数")
@RequestMapping(value = "v1")
public class Demo {

    // 首先获取一个执行sql语句的对象
    @Autowired
    // 这个注解是说启动即加载，启动类的时候就会加载这个template了
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount", method = RequestMethod.GET)
    @ApiOperation(value = "这是返回用户数的一个接口", httpMethod = "GET")
    public int getUserCount(){

        return template.selectOne("getUserCount");

    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ApiOperation(value = "这是一组新增用户数据的接口", httpMethod = "POST")
    public int addUser(@RequestBody User user){

        return template.insert("addUser", user);

    }
}
