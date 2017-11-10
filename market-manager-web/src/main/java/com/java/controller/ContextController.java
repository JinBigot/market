package com.java.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.context.service.ContextServiceI;
import com.java.dto.EazyUIStyle;
import com.java.dto.MarketResult;
import com.java.model.TbContent;
/**
 * 
 * @ClassName: ContextController 
 * @Description: 内容增删改查
 * @author:sangjin
 * @date 2017年10月26日 下午3:56:29
 */
@Controller
public class ContextController {
	@Resource
	private ContextServiceI contextServiceI;

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月26日 下午3:56:37
	 * @Title: addContext
	 * @Description: 内容管理==新增内容
	 */
	@RequestMapping("content/save")
	@ResponseBody
	public MarketResult addContext(TbContent content) {
		MarketResult context = contextServiceI.addContext(content);
		return context;
	}
	
	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月26日 下午6:29:36
	 * @Title: getAllContent
	 * @Description:内容列表查询 ==》内容管理 实现分页
	 */
	@RequestMapping("content/query/list")
	@ResponseBody
	public EazyUIStyle getAllContent(@RequestParam(defaultValue="1",name="page")Integer pageNum,Integer rows,Long categoryId) {
		EazyUIStyle eazyUIStyle = contextServiceI.getAllContent(pageNum, rows, categoryId);
		return eazyUIStyle;
	}
	
	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月27日 下午4:08:19
	 * @Title: deleteContent
	 * @Description:网站内容管理==》内容管理==》删除
	 */
	@RequestMapping("content/delete")
	@ResponseBody
	public MarketResult deleteContent(Long[] ids) {
		MarketResult content = contextServiceI.deleteContent(ids);
		return content;
	}
	
	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月27日 下午4:37:22
	 * @Title: updateContent
	 * @Description:内容管理 ==》编辑
	 */
	@RequestMapping("rest/content/edit")
	@ResponseBody
	public MarketResult updateContent(TbContent content) {
		MarketResult updateContent = contextServiceI.updateContent(content);
		return updateContent;
	}
}
