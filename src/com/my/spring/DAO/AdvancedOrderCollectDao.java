package com.my.spring.DAO;

import com.my.spring.model.AdvancedOrderCollect;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface AdvancedOrderCollectDao {
	boolean addAdvancedOrderCollect(AdvancedOrderCollect ps);
    boolean deleteAdvancedOrderCollect(Long id);
    boolean updateAdvancedOrderCollect(AdvancedOrderCollect ps);
    AdvancedOrderCollect getById(Long id);
    AdvancedOrderCollect getAdvancedOrderById(Long id);
    DataWrapper<List<AdvancedOrderCollect>> getAdvancedOrderCollectsList(Integer pageIndex, Integer pageSize, AdvancedOrderCollect advancedOrder);
    DataWrapper<List<AdvancedOrderCollect>> getAdvancedOrderCollectByUserId(Long userId);
}
