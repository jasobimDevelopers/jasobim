package com.my.spring.DAO;

import com.my.spring.model.Messageleave;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface MessageleaveDao {
    boolean addMessageleave(Messageleave messageleave);
    boolean deleteMessageleave(Long id);
    boolean updateMessageleave(Messageleave messageleave);
    DataWrapper<List<Messageleave>> getMessageleaveList();
}
