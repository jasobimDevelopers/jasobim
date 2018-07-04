package com.my.spring.DAO;

import com.my.spring.model.WechatUrl;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface WechatUrlDao {
	boolean addWechatUrl(WechatUrl news);
    boolean deleteWechatUrl(Long id);
    boolean updateWechatUrl(WechatUrl news);
    WechatUrl getById(Long id);
    DataWrapper<List<WechatUrl>> getWechatUrlList(Integer pageIndex, Integer pageSize, WechatUrl news);
    WechatUrl getWechatUrlEarly();
    DataWrapper<List<WechatUrl>> getWechatUrlListByUserId(Long userId);
}
