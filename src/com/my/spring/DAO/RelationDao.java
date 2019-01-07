package com.my.spring.DAO;

import java.util.List;
import com.my.spring.model.Relation;
public interface RelationDao {
	boolean addRelation(Relation role);
	List<Relation> getRelationListsByIds(Integer relationType,Long aboutId,Long userId);
	boolean deleteRelation(Relation role);
	boolean addRelationList(List<Relation> sendUserList);
}
