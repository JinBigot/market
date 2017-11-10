package com.java.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.dto.EazyUIStatus;
import com.java.service.ItemEditServiceI;

@Controller
@RequestMapping("rest/item")
public class ItemEditController {

	@Resource
	private ItemEditServiceI itemEditService;

	@RequestMapping("{status}")
	@ResponseBody
	public EazyUIStatus updateItems(Long[] ids, @PathVariable String status) {
		EazyUIStatus eazyUIStatus = new EazyUIStatus();
		eazyUIStatus = itemEditService.updateItems(ids, status);
		return eazyUIStatus;
	}

}
