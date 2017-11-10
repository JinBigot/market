package com.java.search.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.dto.SearchResult;
import com.java.search.service.SearchItemServiceI;

@Controller
public class IndexController {
	
	@Resource
	private SearchItemServiceI searchItemServiceI ;
	
	@Value("${SOLR_SEARCH_ROWS}")
	private Integer SOLR_SEARCH_ROWS;
	
	@RequestMapping("search")
	public String showIndex(@RequestParam("q")String query,
							@RequestParam(defaultValue="1")Integer page,
							Model model) throws Exception {
		//解决乱码
		query = new String(query.getBytes("ISO-8859-1"),"UTF-8");
		SearchResult searchResult = searchItemServiceI.search(query, page, SOLR_SEARCH_ROWS);
//		int i = 1/0;
		model.addAttribute("query", query);
		model.addAttribute("totalPages", searchResult.getPageNum());
		model.addAttribute("itemList", searchResult.getItemSearchs());
		model.addAttribute("page", page);
		return "search";
	}
}
