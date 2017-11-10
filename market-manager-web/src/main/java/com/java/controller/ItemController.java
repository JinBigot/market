package com.java.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.dto.EazyUIStyle;
import com.java.dto.MarketResult;
import com.java.model.TbItem;
import com.java.service.ItemServiceI;

/**
 * 
 * @ClassName: ItemController
 * @Description: 商品管理
 * @author:sangjin
 * @date 2017年10月27日 下午4:47:57
 */
@Controller
public class ItemController {
	@Resource
	private ItemServiceI itemService;

	@RequestMapping("item/get/{id}")
	@ResponseBody // json
	public TbItem getItemById(@PathVariable Long id) {
		TbItem item = itemService.getItemById(id);
		return item;
	}

	@RequestMapping("item/list")
	@ResponseBody
	public EazyUIStyle getItemList(@RequestParam(defaultValue = "1", required = false, value = "page") Integer pagenum,
			Integer rows) {
		EazyUIStyle eazyUIStyle = itemService.getAllList(pagenum, rows);
		return eazyUIStyle;
	}

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月27日 下午4:47:30
	 * @Title: insertItem
	 * @Description:查询产品的新增
	 */
	@RequestMapping(value = "item/save", method = RequestMethod.POST)
	@ResponseBody
	public MarketResult insertItem(TbItem item, String desc) {
		MarketResult marketResult = itemService.insertItem(item, desc);
		return marketResult;
	}

	
	@RequestMapping("rest/item/query/item/desc/{id}")
	@ResponseBody
	public MarketResult queryItemById(@PathVariable Long id) {
		MarketResult queryItemById = itemService.queryItemById(id);
		return queryItemById;
	}
	
	
	@RequestMapping("rest/item/param/item/query/{id}")
	@ResponseBody
	public MarketResult queryParamItemById(@PathVariable Long id){
		MarketResult queryParamItemById = itemService.queryParamItemById(id);
		return queryParamItemById;
	}
	
	@RequestMapping(value = "rest/item/update", method = RequestMethod.POST)
	@ResponseBody
	public MarketResult updateItem(TbItem item) {
		MarketResult result = itemService.updateItem(item);
		return result;
	}
}
