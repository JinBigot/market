package com.java.service;

import com.java.dto.EazyUIStyle;
import com.java.dto.MarketResult;

public interface ItemParamServiceI {

	EazyUIStyle getAllItemParam(Integer pageNum, Integer rows);

	MarketResult addItemParam(Long id, String paramData);

	MarketResult selectItemcatById(Long id);

	MarketResult deleteItemParam(Long[] ids);
}
