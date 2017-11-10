package com.java.context.service;

import java.util.List;

import com.java.dto.EazyUIStyle;
import com.java.dto.MarketResult;
import com.java.model.TbContent;

public interface ContextServiceI {
	MarketResult addContext(TbContent tbContent);
	
	List<TbContent> getContentById(long id);
	
	EazyUIStyle getAllContent(int pageNum,Integer rows,Long categoryId);
	
	MarketResult deleteContent(Long[] ids);
	
	MarketResult updateContent(TbContent content);
	
	
}
