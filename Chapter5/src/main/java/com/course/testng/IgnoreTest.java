package com.course.testng;

import org.testng.annotations.Test;


/**
 * 忽略测试
 * @Test(enabled = false) 忽略
 * @Test(enabled = true) 不忽略
 */
public class IgnoreTest {

    @Test
    public void ignore1(){
        System.out.println("ignore1执行！！！！！！");
    }

    // 忽略测试
    @Test(enabled = false)
    public void ignore2(){
        System.out.println("ignore2执行！！！！！！");
    }

    @Test(enabled = true)
    public void ignore3(){
        System.out.println("ignore3执行！！！！！！");
    }
}
