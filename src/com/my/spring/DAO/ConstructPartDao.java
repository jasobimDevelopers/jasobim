package com.my.spring.DAO;

import java.util.List;
import com.my.spring.model.ConstructPart;
/**
* @author 徐雨祥
* @version 创建时间：2018年8月31日 下午1:51:31
* 类说明
*/
public interface ConstructPartDao {
	 boolean addConstructPart(ConstructPart am);
     boolean deleteConstructPart(Long id);
     boolean updateConstructPart(ConstructPart am);
	ConstructPart getById(Long id);
	List<ConstructPart> getConstructPartList(ConstructPart am);
}
