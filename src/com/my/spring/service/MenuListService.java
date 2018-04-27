package com.my.spring.service;


import java.util.List;

import com.my.spring.model.MenuList;
import com.my.spring.model.MenuListCopy;
import com.my.spring.model.MenuListPojo;
import com.my.spring.utils.DataWrapper;

import net.sf.json.JSONArray;

public interface MenuListService {
	DataWrapper<MenuListPojo> getById(String token,Long id);
	DataWrapper<Void> deleteMenuListById(String token,Long id);
	DataWrapper<Void> deleteMenuListByIdList(String token,String[] id);
	DataWrapper<Void> addMenuList(String token, MenuList file);
	DataWrapper<String> getMenuListMapByIdList(String token, String[] id);
	DataWrapper<String> getMenuListMapByIdList(String[] id);
	DataWrapper<List<MenuList>> getMenuLists(String token);
	DataWrapper<String> getMenuListMapByIdList(List<MenuListCopy> menu);
	DataWrapper<Void> updateMenu(String token, MenuList item);
}
