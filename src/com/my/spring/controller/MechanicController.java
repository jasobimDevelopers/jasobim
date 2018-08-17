package com.my.spring.controller;

import java.text.ParseException;
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

import com.my.spring.model.Mechanic;
import com.my.spring.model.MechanicPojo;
import com.my.spring.model.MechanicPojos;
import com.my.spring.service.MechanicService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/mechanic")
public class MechanicController {
	@Autowired
	MechanicService amService;
    @RequestMapping(value="/addMechanic", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMechanic(
    		@ModelAttribute Mechanic mechanic,
    		HttpServletRequest request,
    		@RequestParam(value = "idCardImgZs", required = false) MultipartFile idCardImgZs,
    		@RequestParam(value = "idCardImgFs", required = false) MultipartFile idCardImgFs,
            @RequestParam(value = "token",required = true) String token){
        return amService.addMechanic(mechanic, token,idCardImgZs,idCardImgFs,request);
    }
    
    @RequestMapping(value="/updateMechanic", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMechanic(
    		@ModelAttribute Mechanic mechanic,
    		HttpServletRequest request,
    		@RequestParam(value = "idCardImgZs", required = false) MultipartFile idCardImgZs,
    		@RequestParam(value = "idCardImgFs", required = false) MultipartFile idCardImgFs,
            @RequestParam(value = "token",required = true) String token){
        return amService.updateMechanic(mechanic, token,idCardImgZs,idCardImgFs,request);
    }
    @RequestMapping(value="/deleteMechanic",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteMechanic(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return amService.deleteMechanic(id,token);
    }

    @RequestMapping(value="/getMechanicList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MechanicPojo>> getMechanicList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Mechanic ps) throws ParseException{
        return amService.getMechanicList(token, ps, pageSize, pageIndex);
    }
    @RequestMapping(value="/getMechanicInfos", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MechanicPojos>> getMechanicInfos(
            @RequestParam(value = "token",required = true) String token,
    		@ModelAttribute Mechanic ps) throws ParseException{
        return amService.getMechanicInfos(token, ps);
    }
   
}
