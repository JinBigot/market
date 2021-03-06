package com.java.sso.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.dto.MarketResult;
import com.java.model.TbUser;
import com.java.sso.service.UserServiceI;
import com.java.utils.CookieUtils;
import com.java.utils.JsonUtils;

@Controller
@RequestMapping("user")
public class UserController {

	@Resource
	private UserServiceI userServiceI;

	@Value("${COKKIE_TOKEN_KEY}")
	private String COKKIE_TOKEN_KEY;

	@RequestMapping("check/{param}/{type}")
	@ResponseBody
	public MarketResult checkData(@PathVariable String param, @PathVariable Integer type) {
		MarketResult result = userServiceI.checkData(param, type);
		return result;
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public MarketResult registerUser(TbUser tbUser, String pwdRepeat) {
		MarketResult result = userServiceI.createUser(tbUser, pwdRepeat);
		return result;
	}

	@RequestMapping("login")
	@ResponseBody
	public MarketResult userLogin(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		MarketResult result = userServiceI.userLogin(username, password);
		// 从获得的result内得到token
		String token = result.getData().toString();
		// 将token存放至cookie
		CookieUtils.setCookie(request, response, COKKIE_TOKEN_KEY, token);
		return result;
	}

	@RequestMapping(value = "token/{token}", produces = "application/json;utf-8")
	@ResponseBody
	public String checkToken(@PathVariable String token, String callback) {
		MarketResult result = userServiceI.getUserToken(token);
		if (StringUtils.isNotBlank(callback)) {
			String jpresult = callback + "(" + JsonUtils.objectToJson(result) + ");";
			return jpresult;
		}
		return JsonUtils.objectToJson(result);
	}

	@RequestMapping("logout/{token}")
	@ResponseBody
	public MarketResult logout(@PathVariable String token) {
		MarketResult result = userServiceI.deleteToken(token);
		return result;
	}
}
