package com.java.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java.dto.EazyUITree;
import com.java.mapper.TbItemCatMapper;
import com.java.model.TbItemCat;
import com.java.model.TbItemCatExample;
import com.java.model.TbItemCatExample.Criteria;
import com.java.service.ItemCatTreeServiceI;

@Service
public class ItemCatTreeServiceImpl implements ItemCatTreeServiceI {

	@Resource
	private TbItemCatMapper tbItemCatMapper;

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年11月7日 上午9:10:50
	 * @Title: getItemCatList
	 * @Description:商品类目树状显示
	 */
	@Override
	public List<EazyUITree> getItemCatList(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> cats = tbItemCatMapper.selectByExample(example);
		List<EazyUITree> eazyUITrees = new ArrayList<>();
		for (TbItemCat TbItemCat : cats) {
			EazyUITree eazyUITree = new EazyUITree();
			eazyUITree.setId(TbItemCat.getId());
			eazyUITree.setText(TbItemCat.getName());
			eazyUITree.setState((TbItemCat.getIsParent()) ? "closed" : "open");
			eazyUITrees.add(eazyUITree);
		}
		return eazyUITrees;
	}
}
