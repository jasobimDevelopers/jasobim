package com.my.spring.service;

import java.util.List;

import com.my.spring.model.UserTeam;
import com.my.spring.model.UserTeamPojo;
import com.my.spring.utils.DataWrapper;
public interface UserTeamService {
	DataWrapper<Void> deleteUserTeamById(String token,Long id);
	DataWrapper<Void> addUserTeam(String token,UserTeam role);
	DataWrapper<Void> deleteUserTeamByIdList(String token,String[] id);
	DataWrapper<List<UserTeamPojo>> getUserTeamList(Integer pageIndex, Integer pageSize, UserTeam item, String token);
	DataWrapper<Void> updateUserTeam(String token, UserTeam userTeam);
}
