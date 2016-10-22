package com.my.spring.DAO;

import com.my.spring.model.PaperType;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface PaperTypeDao {
    boolean addPaperType(PaperType paperType);
    boolean deletePaperType(Long id);
    boolean updatePaperType(PaperType paperType);
    DataWrapper<List<PaperType>> getPaperTypeList();
}
