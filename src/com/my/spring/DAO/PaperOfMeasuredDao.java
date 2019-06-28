package com.my.spring.DAO;

import com.my.spring.model.PaperOfMeasured;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface PaperOfMeasuredDao {
    boolean addPaperOfMeasured(PaperOfMeasured building);
    boolean deletePaperOfMeasured(Long id);
    boolean updatePaperOfMeasured(PaperOfMeasured building);
    DataWrapper<List<PaperOfMeasured>> getPaperOfMeasuredList();
	List<PaperOfMeasured> getPaperOfMeasuredByProjectId(Long projectId);
	boolean deletePaperOfMeasuredByProjectId(Long id);
	PaperOfMeasured getById(Long paperOfMeasuredId);
}
