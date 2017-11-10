package com.java.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCluster {

	//@Test
	public void testSolrClusterAdd() {
		// 使用cloudsolrserver链接solr集群
		CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.66.8:2181,192.168.66.8:2182,192.168.66.8:2183");
		// 设置默认访问的collection
		cloudSolrServer.setDefaultCollection("collection2");
		// 创建一个document
		SolrInputDocument document = new SolrInputDocument();
		// 将域添加到document中
		document.addField("id", 456);
		document.addField("item_title", "锤子T2");
		document.addField("item_price", "2999");
		try {
			cloudSolrServer.add(document);
			cloudSolrServer.commit();
		} catch (SolrServerException e) {
			//
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
