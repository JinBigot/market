package com.java.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 
 * @ClassName: ItemParamController 
 * @Description: 规格参数的修改
 * @author:sangjin
 * @date 2017年10月27日 下午1:47:13
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.dto.EazyUIStyle;
import com.java.dto.MarketResult;
import com.java.service.ItemParamServiceI;

@Controller
@RequestMapping("item/param")
public class ItemParamController {

	@Resource
	private ItemParamServiceI itemParamServiceI;

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月27日 下午1:53:10
	 * @Title: getAllItemParam
	 * @Description:查询有信息 以分页显示
	 */
	@RequestMapping("list")
	@ResponseBody
	public EazyUIStyle getAllItemParam(
			@RequestParam(defaultValue = "1", name = "page", required = false) Integer pageNum, Integer rows) {
		EazyUIStyle eazyUIStyle = itemParamServiceI.getAllItemParam(pageNum, rows);
		return eazyUIStyle;
	}

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月28日 下午1:43:04
	 * @Title: selectItemcatById
	 * @Description:页面获取catid判断此类目是否添加
	 */
	@RequestMapping("query/itemcatid/{id}")
	@ResponseBody
	public MarketResult selectItemcatById(@PathVariable Long id) {
		MarketResult selectItemcatById = itemParamServiceI.selectItemcatById(id);
		return selectItemcatById;
	}

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月28日 下午3:05:21
	 * @Title: addItemParam
	 * @Description:规格参数 添加
	 */
	@RequestMapping("save/{id}")
	@ResponseBody
	public MarketResult addItemParam(@PathVariable Long id, String paramData) {
		MarketResult addItemParam = itemParamServiceI.addItemParam(id, paramData);
		return addItemParam;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public MarketResult deleteItemParam(Long[] ids) {
		MarketResult itemParam = itemParamServiceI.deleteItemParam(ids);
		return itemParam;
	}
	
}
