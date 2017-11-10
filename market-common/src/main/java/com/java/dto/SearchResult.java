package com.java.dto;

import java.io.Serializable;
import java.util.List;

import com.java.dto.ItemSearch;

public class SearchResult implements Serializable {

	private long pageNum;
	private long recordNum;
	private List<ItemSearch> itemSearchs;

	public long getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(long recordNum) {
		this.recordNum = recordNum;
	}

	public long getPageNum() {
		return pageNum;
	}

	public void setPageNum(long pageNum) {
		this.pageNum = pageNum;
	}

	public List<ItemSearch> getItemSearchs() {
		return itemSearchs;
	}

	public void setItemSearchs(List<ItemSearch> itemSearchs) {
		this.itemSearchs = itemSearchs;
	}

}
