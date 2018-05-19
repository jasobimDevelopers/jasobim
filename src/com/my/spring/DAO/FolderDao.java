package com.my.spring.DAO;
import com.my.spring.model.Folder;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface FolderDao {
	Folder getById(Long id);
	DataWrapper<Folder> getByName(String name);
	boolean deleteFloder(Long id);
	boolean addFloder(Folder file);
	List<Folder> getFolderLists(List<Folder> childrens);
	List<Folder> getFolderList(Folder childrens);
	boolean addFloderList(List<Folder> addList);
	boolean updateFloder(Folder floder);
	DataWrapper<List<Folder>> getFolderLists(Folder folder);
	List<Folder> getAllFolder();
	boolean deleteFloderList(String[] ids);
	List<Folder> getFolderListLike(Long projectId, String name);
	List<Folder> getFolderIndexList(Long projectId, Long pid);
	List<Folder> getByIds(String ids);
	List<Folder> getAllFolderss();
}
