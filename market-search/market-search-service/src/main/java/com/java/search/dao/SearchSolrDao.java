package com.java.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.java.dto.ItemSearch;
import com.java.dto.SearchResult;

@Component
public class SearchSolrDao {

	@Resource
	private SolrServer solrServer;

	public SearchResult search(SolrQuery solrQuery) throws Exception {
		SearchResult searchResult = new SearchResult();
		// 创建查询对象
		QueryResponse queryResponse = solrServer.query(solrQuery);
		// 获取结果集
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		// 获取总条数 设置到searchresult中
		searchResult.setRecordNum(solrDocumentList.getNumFound());
		// searchresult少一个list
		// 将solrDocumentList转换成searchresult
		// 新建一个searchresult集合接受数据
		List<ItemSearch> itemSearchs = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			ItemSearch itemSearch = new ItemSearch();
			itemSearch.setCategoryName((String) solrDocument.get("item_category_name"));
			itemSearch.setId((String) solrDocument.get("id"));
			itemSearch.setImage((String) solrDocument.get("item_image"));
			itemSearch.setItemDesc((String) solrDocument.get("item_desc"));
			itemSearch.setPrice((Long) solrDocument.get("item_price"));
			itemSearch.setSellPoint((String) solrDocument.get("item_sell_point"));

			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = "";
			if (!CollectionUtils.isEmpty(list)) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			itemSearch.setTitle(itemTitle);
			itemSearchs.add(itemSearch);
		}
		searchResult.setItemSearchs(itemSearchs);
		return searchResult;
	}

}
