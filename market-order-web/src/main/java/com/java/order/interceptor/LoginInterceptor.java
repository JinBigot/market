package com.java.order.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.java.dto.MarketResult;
import com.java.sso.service.UserServiceI;
import com.java.utils.CookieUtils;

public class LoginInterceptor implements HandlerInterceptor {

	@Value("${MARKET_CART_TOKEN}")
	private String MARKET_CART_TOKEN;

	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;

	@Resource
	private UserServiceI userServiceI;

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年11月9日 下午6:48:47
	 * @Title: preHandle
	 * @Description:订单页面的预处理，判断用户是否登陆
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从cookie中取出token
		String string = CookieUtils.getCookieValue(request, MARKET_CART_TOKEN, true);
		// string为空 cookie中没有该值的存在
		if (StringUtils.isBlank(string)) {
			// 获取当前请求的url
			String url = request.getRequestURI().toString();
			// 未登录 跳转到登陆页面
			response.sendRedirect(SSO_LOGIN_URL + "?redirectUrl" + url);
			return false;
		}
		// 通过token查询到该用户
		MarketResult result = userServiceI.getUserToken(string);
		// 用户状态不对 即账号密码错误 登陆超时等
		if (result.getStatus() != 200) {
			// 获取当前请求的url
			String url = request.getRequestURI().toString();
			// 未登录 跳转到登陆页面
			response.sendRedirect(SSO_LOGIN_URL + "?redirectUrl" + url);
			return false;
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
