package com.my.spring.DAO;

import com.my.spring.model.Paper;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface PaperDao {
    boolean addPaper(Paper Paper);
    boolean deletePaper(Long id);
    boolean updatePaper(Paper Paper);
    DataWrapper<List<Paper>> getPaperList(Long projecId, Integer pageSize, Integer pageIndex, Paper paper);
    Paper getById(Long id);
	boolean deletePaperByProjectId(Long id);
}
