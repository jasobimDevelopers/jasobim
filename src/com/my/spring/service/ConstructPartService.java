package com.my.spring.service;

import java.util.List;

import com.my.spring.model.ConstructPart;
import com.my.spring.utils.DataWrapper;

/**
* @author 徐雨祥
* @version 创建时间：2018年8月31日 下午1:51:31
* 类说明
*/
public interface ConstructPartService {
	DataWrapper<Void> deleteConstructPartById(Long id,String token);
	DataWrapper<ConstructPart> addConstructPart(ConstructPart constructPart,String token);
	DataWrapper<ConstructPart> getConstructPartById(String token, Long id);
	DataWrapper<List<ConstructPart>> getConstructPartList(String token, ConstructPart constructPart);
}
