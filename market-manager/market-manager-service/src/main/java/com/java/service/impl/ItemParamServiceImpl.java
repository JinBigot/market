package com.java.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.dto.EazyUIStyle;
import com.java.dto.MarketResult;
import com.java.mapper.TbItemCatMapper;
import com.java.mapper.TbItemParamMapper;
import com.java.model.EazyUIStyleParam;
import com.java.model.TbItemCat;
import com.java.model.TbItemCatExample;
import com.java.model.TbItemCatExample.Criteria;
import com.java.model.TbItemParam;
import com.java.model.TbItemParamExample;
import com.java.service.ItemParamServiceI;

@Service
public class ItemParamServiceImpl implements ItemParamServiceI {

	@Resource
	private TbItemParamMapper tbItemParamMapper;

	// private TbItemCatMapper tbItemCatMapper;

	@Override
	public EazyUIStyle getAllItemParam(Integer pageNum, Integer rows) {
		PageHelper.startPage(pageNum, rows);
		TbItemParamExample example = new TbItemParamExample();
		List<EazyUIStyleParam> params = tbItemParamMapper.selectByParamExample(example);

		PageInfo<EazyUIStyleParam> info = new PageInfo<>(params);
		EazyUIStyle eazyUIStyle = new EazyUIStyle();
		eazyUIStyle.setTotal(info.getTotal());
		eazyUIStyle.setRows(info.getList());

		return eazyUIStyle;
	}

	
	@Override
	public MarketResult selectItemcatById(Long id) {
		// 页面获取的id查找商品类目的id
		// catid==>catitem==>判断param是否存在
		TbItemParamExample example = new TbItemParamExample();
		com.java.model.TbItemParamExample.Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(id);
		List<TbItemParam> list = tbItemParamMapper.selectByExample(example);
		//集合非空
		MarketResult marketResult = new MarketResult().ok();//200
		//为空
		//200==》非空
		if (list.size() == 0 || null == list) {
			//空的返回100
			marketResult.setStatus(100);
		}
		marketResult.setData(new TbItemParam());
		return marketResult;
	}


	@Override
	public MarketResult addItemParam(Long id,String paramData) {
		TbItemParam itemParam = new TbItemParam();
		itemParam.setParamData(paramData);
		itemParam.setItemCatId(id);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		tbItemParamMapper.insert(itemParam);
		return MarketResult.ok();
	}


	@Override
	public MarketResult deleteItemParam(Long[] ids) {
		for(Long id:ids) {
			tbItemParamMapper.deleteByPrimaryKey(id);
		}
		return MarketResult.ok();
	}

}
