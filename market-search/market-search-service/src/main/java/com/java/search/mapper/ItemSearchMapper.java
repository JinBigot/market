package com.java.search.mapper;

import java.util.List;

import com.java.dto.ItemSearch;

public interface ItemSearchMapper {
	List<ItemSearch> getAllItems();
	
	ItemSearch findItemById(Long itemId);
	
	Integer findItemStatusById(Long itemId);
}
