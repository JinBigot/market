package com.java.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.item.dto.WebItem;
import com.java.model.TbItem;
import com.java.model.TbItemDesc;
import com.java.service.ItemServiceI;

@Controller
public class ItemIndexController {

	@Resource
	private ItemServiceI itemService;
	
	@RequestMapping("item/{itemId}")
	public String showIndex(@PathVariable Long itemId ,Model model) {
		//通过id获取tbitem
		TbItem tbItem = itemService.getItemById(itemId);
		//将获取的tbitem信息赋值到webitem中去
		WebItem webItem = new WebItem();
			webItem.setBarcode(tbItem.getBarcode());
			webItem.setCid(tbItem.getCid());
			webItem.setCreated(tbItem.getCreated());
			webItem.setId(tbItem.getId());
			webItem.setImage(tbItem.getImage());
			webItem.setNum(tbItem.getNum());
			webItem.setPrice(tbItem.getPrice());
			webItem.setSellPoint(tbItem.getSellPoint());
			webItem.setStatus(tbItem.getStatus());
			webItem.setTitle(tbItem.getTitle());
			webItem.setUpdated(tbItem.getUpdated());
		//通过id获取商品描述信息
		TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
		
		model.addAttribute("item", webItem);
		model.addAttribute("itemDesc", tbItemDesc);
		return "item";
	}
}
