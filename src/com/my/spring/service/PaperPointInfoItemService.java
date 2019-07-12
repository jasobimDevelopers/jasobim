package com.my.spring.service;
import java.util.List;
import com.my.spring.model.PaperPointInfoItem;
import com.my.spring.model.PaperPointInfoItemPojo;
import com.my.spring.utils.DataWrapper;

public interface PaperPointInfoItemService {
    DataWrapper<Void> addPaperPointInfoItem(PaperPointInfoItem paperPointInfoItem, String token);
    DataWrapper<Void> deletePaperPointInfoItem(Long id,String token);
    DataWrapper<Void> updatePaperPointInfoItem(PaperPointInfoItem paperPointInfoItem,String token);
	DataWrapper<List<PaperPointInfoItem>> getPaperPointInfoItemByPointId(Long pointId,String token);
	DataWrapper<List<PaperPointInfoItemPojo>> getPaperPointInfoItemListsByPointId(Long pointId, String token);
	DataWrapper<List<PaperPointInfoItem>> getPaperPointInfoItemModelListByPointId(Long pointId, String token);
}
