package com.my.spring.DAO;

import com.my.spring.model.AdvancedOrderNew;
import com.my.spring.model.AdvancedOrderCopy;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface AdvancedOrderNewDao {
	boolean addAdvancedOrderNew(AdvancedOrderNew ps);
    boolean deleteAdvancedOrderNew(Long id);
    boolean updateAdvancedOrderNew(AdvancedOrderNew ps);
    AdvancedOrderNew getById(Long id);
    DataWrapper<List<AdvancedOrderNew>> getAdvancedOrderNewList(Integer pageIndex, Integer pageSize, AdvancedOrderNew advancedOrder, int adminFlag, String content);
    DataWrapper<List<AdvancedOrderNew>> getAdvancedOrderNewByUserId(Long userId);
	List<AdvancedOrderCopy> getAdvancedOrderNewListNotRead(Long id, Integer pageSize, Integer pageIndex);
}
