package com.my.spring.DAO;

import com.my.spring.model.PaperPointInfoItem;
import java.util.List;

public interface PaperPointInfoItemDao {
    boolean addPaperPointInfoItem(PaperPointInfoItem PaperPointInfoItem);
    boolean deletePaperPointInfoItem(Long id);
    boolean updatePaperPointInfoItem(PaperPointInfoItem PaperPointInfoItem);
	List<PaperPointInfoItem> getPaperPointInfoItemByPointId(Long id);
	PaperPointInfoItem getPaperPointInfoItemById(Long id);
	boolean addPaperPointInfoItemList(List<PaperPointInfoItem> saveList);
}
