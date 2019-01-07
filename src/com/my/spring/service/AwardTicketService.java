package com.my.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.AwardTicket;
import com.my.spring.model.AwardTicketPojo;
import com.my.spring.utils.DataWrapper;
public interface AwardTicketService {
	DataWrapper<Void> deleteAwardTicketById(String token,Long id);
	DataWrapper<AwardTicket> addAwardTicket(String token,String dDate,AwardTicket role,MultipartFile[] pics, MultipartFile[] vois, HttpServletRequest request);
	DataWrapper<Void> deleteAwardTicketByIdList(String token,String[] id);
	DataWrapper<List<AwardTicketPojo>> getAwardTicketList(Integer pageIndex, Integer pageSize, AwardTicket AwardTicket,
			String token);
}
