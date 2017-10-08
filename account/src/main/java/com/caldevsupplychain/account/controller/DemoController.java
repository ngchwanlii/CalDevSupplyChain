package com.caldevsupplychain.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caldevsupplychain.common.ws.account.UserWS;

@Controller
public class DemoController {

	@GetMapping("demo")
	@ResponseBody
	public String home(@Validated @RequestBody UserWS userWS) {
		return "INSIDE DEMO";
	}
}
