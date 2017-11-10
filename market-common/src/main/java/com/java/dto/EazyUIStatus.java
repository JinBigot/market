package com.java.dto;

import java.io.Serializable;

public class EazyUIStatus implements Serializable {
	private String status;

	public EazyUIStatus() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EazyUIStatus(String status) {
		this.status = status;
	}

}
