package com.java.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.context.service.ContextCategoryTreeServiceI;
import com.java.dto.EazyUITree;
import com.java.dto.MarketResult;

/**
 * 
 * @ClassName: ContextCategoryTreeController
 * @Description: 网站内容管理==》内容分类管理
 * @author:sangjin
 * @date 2017年10月26日 下午4:43:13
 */
@Controller
@RequestMapping("content/category")
public class ContextCategoryTreeController {
	@Resource
	private ContextCategoryTreeServiceI contextCategoryTreeServiceI;

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月26日 下午4:44:13
	 * @Title: getAllContextTree
	 * @Description:显示所有并以树状形式显示
	 */
	@RequestMapping("list")
	@ResponseBody
	public List<EazyUITree> getAllContextTree(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EazyUITree> contextTree = contextCategoryTreeServiceI.getAllContextTree(parentId);
		return contextTree;
	}

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月26日 下午4:44:50
	 * @Title: addContextCategory
	 * @Description:内容管理 ==》添加
	 */
	@RequestMapping("create")
	@ResponseBody
	public MarketResult addContextCategory(Long parentId, String name) {
		MarketResult addContextCategory = contextCategoryTreeServiceI.addContextCategory(parentId, name);
		return addContextCategory;
	}
	
	/**
	 * 	
	 * @Author:sangjin
	 * @Date: 2017年10月26日 下午8:05:09
	 * @Title: updateContentCatrgory
	 * @Description:内容分类管理==》重命名
	 */
	@RequestMapping("update")
	@ResponseBody
	public MarketResult updateContentCatrgory(Long id, String name) {
		MarketResult marketResult = contextCategoryTreeServiceI.updateContextCategory(id, name);
		return marketResult;
	}

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月27日 下午3:38:12
	 * @Title: deleteContentCategory
	 * @Description: 内容分类管理==》删除 sth wrong
	 */
	@RequestMapping("delete")
	@ResponseBody
	public MarketResult deleteContentCategory(Long id) {
		MarketResult category = contextCategoryTreeServiceI.deleteContentCategory(id);
		return category;
	}
}
