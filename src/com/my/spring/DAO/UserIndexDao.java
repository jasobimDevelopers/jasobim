package com.my.spring.DAO;
import com.my.spring.model.MaxIndex;
import com.my.spring.model.UserIndex;
import com.my.spring.model.UserIndexUserId;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface UserIndexDao {
	UserIndex getById(Long id);
	boolean deleteUserIndex(Long id);
	boolean addUserIndex(UserIndex role);
	boolean deleteUserIndexList(String[] ids);
	DataWrapper<List<UserIndex>> getUserIndexList(Integer pageIndex, Integer pageSize, UserIndex UserIndex);
	boolean updateUserIndex(UserIndex dp);
	boolean addUserIndexList(List<UserIndex> newList);
	List<UserIndex> getUserIndexListOfAbout(Long userId,Integer aboutType);
	boolean deleteUserIndexByAboutId(Long id, Integer type);
	List<UserIndexUserId> getUserIndexListByGroup();
	MaxIndex getIndexMaxByUserId(Long id);
	boolean deleteUserIndexByAboutType(Long id, Integer type); 
}
