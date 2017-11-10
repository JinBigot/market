package com.java.search.service;


import com.java.dto.MarketResult;
import com.java.dto.SearchResult;


public interface SearchItemServiceI {
	MarketResult importItemToSolr();
	
	SearchResult search(String queryString,int page, int rows) throws Exception;
	
	MarketResult addSolrByItemId(Long itemId);
	
	MarketResult editSolrByItemId(Long itemId);
	
}
