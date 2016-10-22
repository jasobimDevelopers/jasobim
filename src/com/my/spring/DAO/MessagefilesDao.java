package com.my.spring.DAO;

import com.my.spring.model.Messagefiles;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface MessagefilesDao {
    boolean addMessagefiles(Messagefiles messagefiles);
    boolean deleteMessagefiles(Long id);
    boolean updateMessagefiles(Messagefiles messagefiles);
    DataWrapper<List<Messagefiles>> getMessagefilesList();
}
