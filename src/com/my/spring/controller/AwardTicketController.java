package com.my.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.AwardTicket;
import com.my.spring.model.AwardTicketPojo;
import com.my.spring.service.AwardTicketService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/awardTicket")
public class AwardTicketController {
    @Autowired
    AwardTicketService awardTicketService;  
   
    @RequestMapping(value="/admin/addAwardTicket", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<AwardTicket> addAwardTicket(
            @ModelAttribute AwardTicket awardTicket,
            @RequestParam(value = "dDate",required = true) String dDate,
            @RequestParam(value = "pics",required = true) MultipartFile[] pics,
    		@RequestParam(value = "vois",required = true) MultipartFile[] vois,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return awardTicketService.addAwardTicket(token,dDate,awardTicket,pics,vois,request);
    }
    @RequestMapping(value="/admin/deleteAwardTicket",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteAwardTicket(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return awardTicketService.deleteAwardTicketById(token,id);
    }
   

    @RequestMapping(value="/admin/getAwardTicketList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<AwardTicketPojo>> getAwardTicketList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute AwardTicket AwardTicket,
            @RequestParam(value = "token",required = false) String token){
        return awardTicketService.getAwardTicketList(pageIndex,pageSize,AwardTicket,token);
    }

}
