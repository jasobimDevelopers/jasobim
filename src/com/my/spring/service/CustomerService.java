package com.my.spring.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface CustomerService {
	public boolean batchImport(String name,MultipartFile file);
}
