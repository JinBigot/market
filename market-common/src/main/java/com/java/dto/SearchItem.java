package com.java.dto;

import java.io.Serializable;

public class SearchItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1592753488698344368L;
	private String id;
	private String title;
	private String sellPoint;
	private long price;
	private String image;
	private String categoryName;
	private String itemDesc;
	public synchronized String getId() {
		return id;
	}
	public synchronized void setId(String id) {
		this.id = id;
	}
	public synchronized String getTitle() {
		return title;
	}
	public synchronized void setTitle(String title) {
		this.title = title;
	}
	public synchronized String getSellPoint() {
		return sellPoint;
	}
	public synchronized void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public synchronized long getPrice() {
		return price;
	}
	public synchronized void setPrice(long price) {
		this.price = price;
	}
	public synchronized String getImage() {
		return image;
	}
	public synchronized void setImage(String image) {
		this.image = image;
	}
	public synchronized String getCategoryName() {
		return categoryName;
	}
	public synchronized void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public synchronized String getItemDesc() {
		return itemDesc;
	}
	public synchronized void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	
	
}
