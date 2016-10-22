package com.my.spring.service;

import com.my.spring.model.Messagefiles;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface MessagefilesService {
	DataWrapper<Void> addMessagefiles(Messagefiles messagefiles);
    DataWrapper<Void> deleteMessagefiles(Long id);
    DataWrapper<Void> updateMessagefiles(Messagefiles messagefiles);
    DataWrapper<List<Messagefiles>> getMessagefilesList();
}
