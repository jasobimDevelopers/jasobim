package com.my.spring.DAO;

import com.my.spring.model.Papers;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface PapersDao {
    boolean addPapers(Papers papers);
    boolean deletePapers(Long id);
    boolean updatePapers(Papers papers);
    DataWrapper<List<Papers>> getPapersList();
}
