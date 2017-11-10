package com.java.service;

import java.util.List;

import com.java.dto.EazyUITree;

public interface ItemCatTreeServiceI {
	
	public List<EazyUITree> getItemCatList(Long parentId);
}
