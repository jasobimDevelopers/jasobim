package com.my.spring.service;

import com.my.spring.model.Papers;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface PapersService {
	DataWrapper<Void> addPapers(Papers papers);
    DataWrapper<Void> deletePapers(Long id);
    DataWrapper<Void> updatePapers(Papers papers);
    DataWrapper<List<Papers>> getPapersList();
}
