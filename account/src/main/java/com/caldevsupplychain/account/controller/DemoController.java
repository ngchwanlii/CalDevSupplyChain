package com.caldevsupplychain.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @GetMapping("demo")
    @ResponseBody
    public String home(){
        return "INSIDE DEMO";
    }

}
