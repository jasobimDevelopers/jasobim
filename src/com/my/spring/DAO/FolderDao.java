package com.my.spring.DAO;
import com.my.spring.model.Folder;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface FolderDao {
	Folder getById(Long id);
	DataWrapper<Folder> getByName(String name);
	boolean deleteFloder(Long id);
	boolean addFloder(Folder file);
	DataWrapper<List<Folder>> getFolderList(Folder floder);
}
