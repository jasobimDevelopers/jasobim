package com.my.spring.DAO;

import com.my.spring.model.AwardTicket;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface AwardTicketDao {
	AwardTicket getById(Long id);
	boolean deleteAwardTicket(Long id);
	boolean addAwardTicket(AwardTicket role);
	boolean deleteAwardTicketList(String[] ids);
	DataWrapper<List<AwardTicket>> getAwardTicketList(Integer pageIndex, Integer pageSize, AwardTicket AwardTicket);
	boolean updateAwardTicket(AwardTicket dp);
}
