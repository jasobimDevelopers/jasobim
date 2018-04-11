package com.my.spring.DAO;
import com.my.spring.model.ProjectFiles;
import com.my.spring.model.User;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface ProjectFilesDao {
	ProjectFiles getById(Long id);
	DataWrapper<ProjectFiles> getByName(String name);
	boolean deleteProjectFiles(Long id);
	boolean addProjectFiles(ProjectFiles file);
	DataWrapper<List<ProjectFiles>> getProjectFilesList(User user, Integer pageSize, Integer pageIndex,
			ProjectFiles projectFiles);
}
