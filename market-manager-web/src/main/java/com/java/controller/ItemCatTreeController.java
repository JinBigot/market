package com.java.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.dto.EazyUITree;
import com.java.service.ItemCatTreeServiceI;

/**
 * 
 * @ClassName: ItemCatTreeController 
 * @Description: 商品业务的实现 
 * @author:sangjin
 * @date 2017年10月26日 下午4:47:41
 */
@Controller
@RequestMapping("item/cat")
public class ItemCatTreeController {

	@Resource
	private ItemCatTreeServiceI itemCatTreeService;

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月26日 下午4:48:09
	 * @Title: getAllTree
	 * @Description: 获取所有商品并以tree形式显示
	 */
	@RequestMapping("list")
	@ResponseBody
	public List<EazyUITree> getAllTree(@RequestParam(defaultValue = "0", name = "id") Long parentId) {
		List<EazyUITree> eazyUITrees = itemCatTreeService.getItemCatList(parentId);
		return eazyUITrees;
	}
}
