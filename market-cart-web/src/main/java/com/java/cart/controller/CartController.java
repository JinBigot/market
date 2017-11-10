package com.java.cart.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.dto.MarketResult;
import com.java.model.TbItem;
import com.java.service.ItemServiceI;
import com.java.utils.CookieUtils;
import com.java.utils.JsonUtils;

@Controller
@RequestMapping("cart")
public class CartController {

	@Resource
	private ItemServiceI itemService;

	@Value("${MARKET_CART_TOKEN}")
	private String MARKET_CART_TOKEN;

	@RequestMapping("add/{itemId}")
	public String addItem(@PathVariable Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 先去cookie中通过cookie的id查询商品信息
		String string = CookieUtils.getCookieValue(request, MARKET_CART_TOKEN, "UTF-8");
		// 如果不为空
		boolean flag = false;
		List<TbItem> list = new ArrayList<>();
		if (StringUtils.isNotBlank(string)) {
			// 将获得的信息转成list集合
			list = JsonUtils.jsonToList(string, TbItem.class);
			// 遍历集合 判断集合中是否有要添加的商品
			for (TbItem item : list) {
				// 如果有
				if (item.getId() == itemId.longValue()) {
					// 数量设置新值
					item.setNum(num = item.getNum() + num);
					flag = true;
					break;
				}
			}
		}
		// 集合中没有==》cookie中没有
		if (flag == false) {
			// 先从数据库中获取 在存到cookie中
			TbItem tbItem = itemService.getItemById(itemId);
			// 商品描述中可能存在双引号，转换成json有异常，所以将“转义
			if (StringUtils.isNotBlank(tbItem.getSellPoint())) {
				tbItem.setSellPoint(tbItem.getSellPoint().replaceAll("\"", "\'"));
			}
			// 图片可能存在多个的情况 ，所以要先处理 取第一个
			String images = tbItem.getImage();
			if (StringUtils.isNotBlank(images)) {
				tbItem.setImage(images.split(",")[0]);
			}
			tbItem.setNum(num);
			list.add(tbItem);
		}
		CookieUtils.setCookie(request, response, MARKET_CART_TOKEN, JsonUtils.objectToJson(list));
		return "cartSuccess";
	}

	@RequestMapping("cart")
	public String showCart(HttpServletRequest request, ModelMap map) {
		// 先从cookie中拿出
		String string = CookieUtils.getCookieValue(request, MARKET_CART_TOKEN, "UTF-8");
		List<TbItem> list = new ArrayList<>();
		// 如果不为空
		if (StringUtils.isNotBlank(string)) {
			// 将list取出
			list = JsonUtils.jsonToList(string, TbItem.class);
		}
		map.put("cartList", list);
		return "cart";
	}

	@RequestMapping("update/num/{id}/{num}")
	@ResponseBody
	public MarketResult updateCart(@PathVariable Long id, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 先从cookie中拿出
		String string = CookieUtils.getCookieValue(request, MARKET_CART_TOKEN, "UTF-8");
		List<TbItem> list = new ArrayList<>();
		if (StringUtils.isNotBlank(string)) {
			// 将list取出
			list = JsonUtils.jsonToList(string, TbItem.class);
		}
		// 遍历list 修改其中的num
		for (TbItem item : list) {
			// 找出cookie中和id相同的商品，修改其数量
			if (item.getId() == id.longValue()) {
				item.setNum(num);
			}
		}
		CookieUtils.setCookie(request, response, MARKET_CART_TOKEN, JsonUtils.objectToJson(list));
		return MarketResult.ok();
	}

	@RequestMapping("delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 取出
		String string = CookieUtils.getCookieValue(request, MARKET_CART_TOKEN, "UTF-8");
		// 判断是否为空
		List<TbItem> list = new ArrayList<>();
		if (StringUtils.isNotBlank(string)) {
			// 将list取出
			list = JsonUtils.jsonToList(string, TbItem.class);
			Iterator<TbItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				TbItem item = iterator.next();
				if (item.getId() == itemId.longValue()) {
					iterator.remove();
				}
			}
		}
		CookieUtils.setCookie(request, response, MARKET_CART_TOKEN, JsonUtils.objectToJson(list));
		return "redirect:/cart/cart.html";
	}
}
