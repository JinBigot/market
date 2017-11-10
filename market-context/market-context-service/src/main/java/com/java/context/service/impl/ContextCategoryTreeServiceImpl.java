package com.java.context.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java.context.service.ContextCategoryTreeServiceI;
import com.java.dto.EazyUITree;
import com.java.dto.MarketResult;
import com.java.enums.ContextCategorySortOrder;
import com.java.enums.ContextCategoryStatus;
import com.java.mapper.TbContentCategoryMapper;
import com.java.model.TbContentCategory;
import com.java.model.TbContentCategoryExample;
import com.java.model.TbContentCategoryExample.Criteria;
import com.java.redis.JedisClient;
import com.java.redis.JedisClientCluster;

@Service
public class ContextCategoryTreeServiceImpl implements ContextCategoryTreeServiceI {

	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EazyUITree> getAllContextTree(long parentId) {
		// 通过parentId作为查询条件
		TbContentCategoryExample categoryExample = new TbContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(categoryExample);
		List<EazyUITree> eazyUITrees = new ArrayList<>();
		for (TbContentCategory category : list) {
			EazyUITree eazyUITree = new EazyUITree();
			eazyUITree.setId(category.getId());
			eazyUITree.setText(category.getName());
			eazyUITree.setState(category.getIsParent() ? "closed" : "open");
			eazyUITrees.add(eazyUITree);
		}
		return eazyUITrees;
	}

	@Override
	public MarketResult addContextCategory(Long parentId, String name) {
		TbContentCategory category = new TbContentCategory();
		category.setParentId(parentId);
		category.setName(name);
		category.setCreated(new Date());
		category.setUpdated(new Date());
		category.setStatus(ContextCategoryStatus.CONTEXT_CATEGORY_STATUS_DEFULT);
		category.setIsParent(false);
		category.setSortOrder(ContextCategorySortOrder.CONTEXT_CATEGORY_SORTORDER_DEFULT);
		// 根据parentId查找category
		TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		// 如果父类是
		if (!contentCategory.getIsParent()) {
			contentCategory.setIsParent(true);
			contentCategory.setUpdated(new Date());
			tbContentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		}
		tbContentCategoryMapper.insert(category);
		return MarketResult.ok(category);
	}

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月26日 下午7:34:39
	 * @Title: updateContextCategory
	 * @Description:更新contextcategory
	 */
	@Override
	public MarketResult updateContextCategory(Long id, String name) {
		TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
		return MarketResult.ok();
	}

	@Override
	public MarketResult deleteContentCategory(Long id) {
		TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		// 不是父类
		if (!contentCategory.getIsParent()) {
			// 通过这个category的id查找其父类的id==》父类下有几个子类
			TbContentCategoryExample categoryExample = new TbContentCategoryExample();
			Criteria criteria = categoryExample.createCriteria();
			// 获取得到的category的父类的id
			criteria.andIdEqualTo(contentCategory.getParentId());
			// 获取该id的集合
			List<TbContentCategory> parentlist = tbContentCategoryMapper.selectByExample(categoryExample);
			// 存在父类parentlist。get0
			TbContentCategoryExample sonExample = new TbContentCategoryExample();
			Criteria soncriteria = sonExample.createCriteria();
			soncriteria.andParentIdEqualTo(parentlist.get(0).getId());
			List<TbContentCategory> sonlist = tbContentCategoryMapper.selectByExample(sonExample);
			// 不存在其他子类
			if (sonlist.size() == 1) {
				parentlist.get(0).setIsParent(false);
				tbContentCategoryMapper.updateByPrimaryKey(parentlist.get(0));
			}
			tbContentCategoryMapper.deleteByPrimaryKey(id);
			// 2、不是父类，但是其父类还有其他子类
			// 修改子类信息 将status设置成删除状态
			contentCategory.setStatus(2);
			// 更新
			tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
		} else {
			// 3、是父类，将子类信息一个个上移
			// 取出所有子类
			TbContentCategoryExample categoryExample = new TbContentCategoryExample();
			Criteria criteria = categoryExample.createCriteria();
			criteria.andParentIdEqualTo(id);
			List<TbContentCategory> sonlist = tbContentCategoryMapper.selectByExample(categoryExample);
			for (TbContentCategory category : sonlist) {
				if (!category.getIsParent()) {
					category.setIsParent(false);
					tbContentCategoryMapper.updateByPrimaryKey(category);
				} else {
					// return A;
					deleteContentCategory(category.getId());
				}
			}
		}
		return MarketResult.ok();
	}
}
