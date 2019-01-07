package com.my.spring.service;

import java.util.List;
import com.my.spring.model.Nature;
import com.my.spring.utils.DataWrapper;
public interface NatureService {
	DataWrapper<Void> deleteNatureById(String token,Long id);
	DataWrapper<Nature> addNature(String token,Nature role);
	DataWrapper<Void> updateNature(String token, Nature Nature);
	DataWrapper<List<Nature>> getNatureList(Integer pageIndex, Integer pageSize, Nature Nature,
			String token);
}
