package com.java;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.mapper.TbItemMapper;
import com.java.model.TbItem;
import com.java.model.TbItemExample;

public class TestPageHepler {
	//@Test
//	public void TestPageHelper() {
//
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
//				"classpath:spring/applicationContext-*.xml");
//		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
//
//		TbItemExample example = new TbItemExample();
//
//		PageHelper helper = new PageHelper();
//		helper.startPage(1, 20);
//		List<TbItem> items = itemMapper.selectByExample(example);
//
//		PageInfo<TbItem> info = new PageInfo<>(items);
//
//		System.out.println(info.getSize() + "==========================");
//		System.out.println(info.getEndRow());
//	}
}
