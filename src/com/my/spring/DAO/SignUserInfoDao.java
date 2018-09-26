package com.my.spring.DAO;

import com.my.spring.model.SignUserInfo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface SignUserInfoDao {
    boolean addSignUserInfo(SignUserInfo Item);
    boolean deleteSignUserInfo(Long id);
    boolean updateSignUserInfo(SignUserInfo Item);
    boolean deleteSignUserInfoByMobile(String mobile);
	DataWrapper<List<SignUserInfo>> getSignUserInfoList(Integer pageSize, Integer pageIndex, SignUserInfo item);
	SignUserInfo getSignUserInfoById(Long id);
   
}
