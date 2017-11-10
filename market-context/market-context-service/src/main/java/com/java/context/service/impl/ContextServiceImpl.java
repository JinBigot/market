package com.java.context.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.context.service.ContextServiceI;
import com.java.dto.EazyUIStyle;
import com.java.dto.MarketResult;
import com.java.mapper.TbContentMapper;
import com.java.model.TbContent;
import com.java.model.TbContentExample;
import com.java.model.TbContentExample.Criteria;
import com.java.redis.JedisClient;
import com.java.utils.JsonUtils;

@Service
public class ContextServiceImpl implements ContextServiceI {

	@Resource
	private TbContentMapper contentMapper;

	@Resource
	private JedisClient jedisClientCluster;

	@Value("${CONTENT_INDEX}")
	private String CONTENT_INDEX;

	@Override
	public MarketResult addContext(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		contentMapper.insert(tbContent);
		return MarketResult.ok();
	}

	@Override
	public List<TbContent> getContentById(long id) {
		// 如果缓存redis存在 直接调用缓存数据
		String string = jedisClientCluster.hget(CONTENT_INDEX, id + "");
		// 判断是否存在,存在
		if (StringUtils.isNotBlank(string)) {
			List<TbContent> contents = JsonUtils.jsonToList(string, TbContent.class);
			return contents;
		}
		// 不存在调用数据库查询
		// 通过id查找对应的list集合
		TbContentExample contentExample = new TbContentExample();
		Criteria criteria = contentExample.createCriteria();
		List<TbContent> list = contentMapper.selectByExample(contentExample);
		// 并将数据库查询的结果保存在redis缓存中
		jedisClientCluster.hset(CONTENT_INDEX, id + "", JsonUtils.objectToJson(list));
		return list;
	}

	/**
	 * 
	 * @Author:sangjin
	 * @Date: 2017年10月26日 下午6:39:13
	 * @Title: getAllContent
	 * @Description:获取所有cintent实现类
	 */
	@Override
	public EazyUIStyle getAllContent(int pageNum, Integer rows, Long categoryId) {
		PageHelper.startPage(pageNum, rows);
		TbContentExample contentExample = new TbContentExample();
		Criteria createCriteria = contentExample.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(contentExample);
		PageInfo<TbContent> info = new PageInfo<>(list);
		EazyUIStyle eazyUIStyle = new EazyUIStyle();
		eazyUIStyle.setTotal(info.getTotal());
		eazyUIStyle.setRows(info.getList());
		return eazyUIStyle;
	}

	@Override
	public MarketResult deleteContent(Long[] ids) {
		// 通过ids查找content
		for (Long id : ids) {
			contentMapper.deleteByPrimaryKey(id);
		}
		return MarketResult.ok();
	}

	@Override
	public MarketResult updateContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKey(content);
		return MarketResult.ok();
	}

}
