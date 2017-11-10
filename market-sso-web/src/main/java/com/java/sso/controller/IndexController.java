package com.java.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("page")
public class IndexController {
	
	@RequestMapping("login")
	public String showLogin(String redirectUrl,Model model) {
		model.addAttribute("redirect",redirectUrl);
		return "login";
	}
	
	@RequestMapping("register")
	public String showIndex() {
		return "register";
	}
	
}
