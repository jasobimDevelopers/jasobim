 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Budget;
import com.my.spring.model.BudgetPojo;
import com.my.spring.service.BudgetService;
import com.my.spring.utils.DataWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;



@Controller
@RequestMapping(value="api/budget")
public class BudgetController {
	@Autowired
    BudgetService BudgetService;
    @RequestMapping(value="/vs/addBudget", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addBudget(
            @ModelAttribute Budget news,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=BudgetService.addBudget(news,token);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/admin/deleteBudget",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteNews(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return BudgetService.deleteBudget(id,token);
    }

    @RequestMapping(value="/updateBudget",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateBudget(
            @ModelAttribute Budget News,
            @RequestParam(value = "token",required = false) String token){
        return BudgetService.updateBudget(News,token);
    }


    @RequestMapping(value="/vs/getBudgetList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<BudgetPojo>> getBudgetList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Budget News){
        return BudgetService.getBudgetList(token,pageIndex,pageSize,News);
    }
    /////导入物资清单
    @RequestMapping(value="/web/importBudget", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> importBudgetList(
    		 @RequestParam(value = "file", required = false) MultipartFile file,
             @RequestParam(value = "token",required = true) String token,
             @ModelAttribute Budget News,
            HttpServletRequest request){
    	return BudgetService.importBudget(file,request,token,News);
    }
   
}
