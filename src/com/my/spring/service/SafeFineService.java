package com.my.spring.service;

import com.my.spring.model.SafeFine;
import com.my.spring.model.SafeFinePojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface SafeFineService {
    DataWrapper<Void> addSafeFine(SafeFine building, String token);
    DataWrapper<Void> deleteSafeFine(Long id,String token);
    DataWrapper<Void> updateSafeFine(SafeFine building,String token);
	DataWrapper<List<SafeFinePojo>> getSafeFineList(Integer pageIndex, Integer pageSize, SafeFine duct, String token);
}
