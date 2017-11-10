package com.java.test;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

public class TestSolr {

	//@Test
	public void testSolr() throws Exception {
		// 创建一个solrserver，使用httpsolrserver创建对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.66.217:8080/solr/collection1");
		// 创建一个文档对象solrinputdocument对象
		SolrInputDocument document = new SolrInputDocument();
		document.setField("id", "001");
		document.setField("item_title", "Iphonex");
		document.setField("item_price", "8888");
		solrServer.add(document);
		solrServer.commit();
	}

	// 根据id删除
	//@Test
	public void deleteById() throws Exception {
		// 创建一个solrserver，使用httpsolrserver创建对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.66.217:8080/solr/collection1");
		solrServer.deleteById("001");
		solrServer.commit();
	}

	// 根据查询删除
	//@Test
	public void deleteByQuery() throws Exception {
		// 创建一个solrserver，使用httpsolrserver创建对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.66.217:8080/solr/collection1");
		solrServer.deleteByQuery("item_title:Iphonex");
		solrServer.commit();
	}

	// 查詢索引库
	//@Test
	public void simpleQuery() throws Exception {
		// 创建一个solrserver对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.66.217:8080/solr/collection1");
		// 创建一个solrquery对象
		SolrQuery query = new SolrQuery();
		// 想solrquery添加查询条件、过滤条件···
		query.setQuery("*:*");
		// 执行查询，得到一个respone对象
		QueryResponse queryResponse = solrServer.query(query);
		// 取出查询结果
		SolrDocumentList documentList = queryResponse.getResults();
		// 遍历结果并打印
		for (SolrDocument document : documentList) {
			System.out.println(document.get("id"));
			System.out.println(document.get("item_title"));
			System.out.println(document.get("item_price"));
		}
	}

	// 带高亮显示
	//@Test
	public void searchHigher() throws Exception {
		// 创建一个solrserver对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.66.217:8080/solr/collection1");
		// 创建一个solrquery对象
		SolrQuery solrQuery = new SolrQuery();
		// 想solrquery添加查询条件、过滤条件···
		solrQuery.setQuery("Iphone");
		// 默认搜索域
		solrQuery.set("df", "item_keywords");
		// 分页
		solrQuery.setStart(0);
		solrQuery.setRows(10);
		// 高亮
		solrQuery.setHighlight(true);
		// 设置高亮域，用多个空格隔开
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<font color='orange'>");
		solrQuery.setHighlightSimplePost("</font>");
		// 执行查询操作，得到返回的list
		QueryResponse queryResponse = solrServer.query(solrQuery);
		SolrDocumentList documentList = queryResponse.getResults();
		for (SolrDocument document : documentList) {
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<String> list = highlighting.get("id").get("item_title");
			String itemTitle = "";
			if (!CollectionUtils.isEmpty(list)) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) document.get("item_title");
			}
			System.out.println(itemTitle);
			System.out.println(document.get("id") + "" + document.get("item_title") + document.get("item_price"));
		}
	}

}
