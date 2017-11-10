package com.java.portal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.context.service.ContextServiceI;
import com.java.model.TbContent;
import com.java.portal.pojo.Banner;
import com.java.utils.JsonUtils;
/**
 * 
 * @ClassName: IndexProtalControl 
 * @Description: 前台页面的显示
 * @author:sangjin
 * @date 2017年10月26日 下午4:49:07
 */
@Controller
public class IndexProtalControl {
	@Resource
	private ContextServiceI contextServiceI;

	@Value("${CATEGORY_ID}")
	private Long categoryId;
	
	@Value("${BANNER_HEIGHT}")
	private Long bannerHeight;
	
	@Value("${BANNER_WIDTH}")
	private Long bannerWidth;
	
	@Value("${BANNER_WIDTHB}")
	private Long bannerWidthB;
	
	@Value("${BANNER_HEIGHTB}")
	private Long bannerHeightB;
	
	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月26日 下午6:09:46
	 * @Title: index
	 * @Description:显示index页面并实现页面图片的轮播
	 */
	@RequestMapping("/")
	public String index(Model model) {
		//通过页面穿回来的content参数获取此id下包含的内容
		List<TbContent> list = contextServiceI.getContentById(categoryId);
		List<Banner> banners = new ArrayList<>();
		
		for(TbContent tbContent :list) {
			Banner banner = new Banner();
			banner.setAlt(tbContent.getTitle());
			banner.setHref(tbContent.getUrl());
			banner.setSrc(tbContent.getPic());
			banner.setSrcB(tbContent.getPic2());
			banner.setHeight(bannerHeight);
			banner.setHeightB(bannerHeightB);
			banner.setWidth(bannerWidth);
			banner.setWidthB(bannerWidthB);
			banners.add(banner);
		}
		String Json = JsonUtils.objectToJson(banners);
		model.addAttribute("ad1",Json);
		return "index";
	}
}
