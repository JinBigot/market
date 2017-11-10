package com.java.service;

import com.java.dto.EazyUIStatus;

public interface ItemEditServiceI {

	EazyUIStatus updateItems(Long[] ids, String status);

}
