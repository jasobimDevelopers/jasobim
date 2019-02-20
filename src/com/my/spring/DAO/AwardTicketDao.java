package com.my.spring.DAO;

import com.my.spring.model.AwardTicket;
import com.my.spring.model.QualityCheck;
import com.my.spring.model.User;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface AwardTicketDao {
	AwardTicket getById(Long id);
	boolean deleteAwardTicket(Long id);
	boolean addAwardTicket(AwardTicket role);
	boolean deleteAwardTicketList(String[] ids);
	DataWrapper<List<AwardTicket>> getAwardTicketLists(Integer pageIndex, Integer pageSize, AwardTicket AwardTicket,String start,String end,List<QualityCheck> find);
	boolean updateAwardTicket(AwardTicket dp);
	DataWrapper<List<AwardTicket>> getAwardTicketList(Integer pageIndex, Integer pageSize,
			AwardTicket awardTicket, String start, String end, List<User> aboutUsers);
}
