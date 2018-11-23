package com.my.spring.DAO;

import java.util.List;
import com.my.spring.model.MaterialFile;
import com.my.spring.utils.DataWrapper;

public interface MaterialFileDao {
	boolean addMaterialFile(MaterialFile mf);
	boolean deleteMaterialFile(Long id);
	DataWrapper<List<MaterialFile>> getMaterialFileList(MaterialFile mf,Integer pageSize,Integer pageIndex);
}
