package com.my.spring.service;

import com.my.spring.model.Messageleave;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface MessageleaveService {
	DataWrapper<Void> addMessageleave(Messageleave messageleave);
    DataWrapper<Void> deleteMessageleave(Long id);
    DataWrapper<Void> updateMessageleave(Messageleave messageleave);
    DataWrapper<List<Messageleave>> getMessageleaveList();
}
