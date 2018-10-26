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

import com.my.spring.model.ContractLofting;
import com.my.spring.service.ContractLoftingService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/contractLofting")
public class ContractLoftingController {
    @Autowired
    ContractLoftingService contractLoftingService;  
   
    @RequestMapping(value="/admin/addContractLofting", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ContractLofting> addContractLofting(
            @ModelAttribute ContractLofting contractLofting,
            @RequestParam(value = "rowIdList",required = false) String rowIdList,
            @RequestParam(value = "valueList",required = false) String valueList,
            @RequestParam(value = "token",required = true) String token){
        return contractLoftingService.addContractLofting(token,contractLofting,rowIdList,valueList);
    }
    @RequestMapping(value="/admin/deleteContractLofting",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteContractLofting(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token){
        return contractLoftingService.deleteContractLoftingById(token,id,projectId);
    }

    @RequestMapping(value="/admin/getContractLoftingList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ContractLofting>> getContractLoftingList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ContractLofting contractLofting,
            @RequestParam(value = "token",required = true) String token){
        return contractLoftingService.getContractLoftingList(pageIndex,pageSize,contractLofting,token);
    }
    @RequestMapping(value="/updateContractLofting",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ContractLofting> updateContractLofting(
    		@ModelAttribute ContractLofting contractLofting,
            @RequestParam(value = "token",required = true) String token){
        return contractLoftingService.updateContractLofting(token,contractLofting);
    }
    @RequestMapping(value="/importContractLoftingByProjectId",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> importContractLoftingByProjectId(
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "files",required = true) MultipartFile files,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return contractLoftingService.importContractLoftingByProjectId(token,projectId,files,request);
    }

}
