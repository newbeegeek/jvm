package com.newbee.jvm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: HomePage
 * @Title: JvmController
 * @Package com.newbee.jvm.controller
 * @Author: NewBee.Geek
 * @CreateTime: 2023/3/25 11:43 AM
 */
@RestController
public class JvmController {

    /**
     * @author [NewBee.Geek]
     * @description
     * @date 2023/3/25 12:14 PM
     */
    @GetMapping("/jvm")
    public String homePage() {
        return "Hello! JVM Home Page!";
    }


}
