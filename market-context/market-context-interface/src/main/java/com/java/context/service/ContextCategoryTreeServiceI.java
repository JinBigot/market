package com.java.context.service;

import java.util.List;

import com.java.dto.EazyUITree;
import com.java.dto.MarketResult;

public interface ContextCategoryTreeServiceI {

	List<EazyUITree> getAllContextTree(long parentId);
	
	MarketResult addContextCategory(Long parentId,String name);
	
	MarketResult updateContextCategory(Long id,String name);
	
	MarketResult deleteContentCategory(Long id);
}
