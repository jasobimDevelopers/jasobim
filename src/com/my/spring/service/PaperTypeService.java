package com.my.spring.service;

import com.my.spring.model.PaperType;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface PaperTypeService {
	DataWrapper<Void> addPaperType(PaperType paperType);
    DataWrapper<Void> deletePaperType(Long id);
    DataWrapper<Void> updatePaperType(PaperType paperType);
    DataWrapper<List<PaperType>> getPaperTypeList();
}
