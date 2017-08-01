package com.my.spring.DAO;

import com.my.spring.model.MinItem;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface MinItemDao {

	boolean addMinItemList(List<MinItem> itemList);

	DataWrapper<List<MinItem>> getMinItemList(Long projectId, Integer pageSize, Integer pageIndex, MinItem item);

	MinItem getMinItemBySelfId(Long selfId);

	MinItem getMinItemById(Long id);
}
