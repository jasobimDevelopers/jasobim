package com.my.spring.DAO;

import com.my.spring.model.Normativefiles;
import com.my.spring.utils.DataWrapper;

import java.util.List;
public interface NormativefilesDao {
	boolean addNormativefiles(Normativefiles ps);
    boolean deleteNormativefiles(Long id);
    boolean updateNormativefiles(Normativefiles ps);
    Normativefiles getById(Long id);
    DataWrapper<List<Normativefiles>> getNormativefilessList(Integer pageIndex, Integer pageSize, Normativefiles news);
    DataWrapper<List<Normativefiles>> getNormativefilessListByUserId(Long userId);
}
