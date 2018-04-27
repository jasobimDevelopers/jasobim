package com.my.spring.DAO;
import com.my.spring.model.MenuList;
import com.my.spring.model.MenuListCopy;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface MenuListDao {
	MenuList getById(Long id);
	boolean deleteMenuList(Long id);
	boolean addMenuList(MenuList file);
	boolean deleteMenuListList(String[] ids);
	DataWrapper<List<MenuListCopy>> getMenuListByIdList(String[] id);
	DataWrapper<List<MenuList>> getMenuListByIdLists(String[] id);
	DataWrapper<List<MenuList>> getMenuLists();
	boolean updateMenuList(MenuList ml);
	List<MenuListCopy> getMenuParrentByChild(List<MenuListCopy> ml);
}
