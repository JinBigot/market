package com.java.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.dto.MarketResult;
import com.java.search.service.SearchItemServiceI;


/**
 * 
 * @ClassName: indexController
 * @Description: 后台页面
 * @author:sangjin
 * @date 2017年10月26日 下午4:45:59
 */
@Controller
public class indexController {
	@Resource
	private SearchItemServiceI searchItemServiceI;

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月26日 下午4:46:33
	 * @Title: index
	 * @Description: 后台页面的初始化页面
	 */
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月26日 下午4:47:00
	 * @Title: showPage
	 * @Description: 后面页面显示页数
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}

	@RequestMapping("/rest/page/{itemedit}")
	public String itemEdit(@PathVariable String itemedit) {
		return itemedit;
	}
	
	@RequestMapping("index/import")
	@ResponseBody
	public MarketResult importItemToSolr() {
		MarketResult result = searchItemServiceI.importItemToSolr();
		return result;
	}
}
