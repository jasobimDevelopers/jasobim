package com.my.spring.controller;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.MaterialPlan;
import com.my.spring.service.MaterialPlanService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/materialPlan")
public class MaterialPlanController {
	 @Autowired
	 MaterialPlanService MaterialPlanService;
	 /*
	  * 新建材料
	  * 
	  * */
	 @RequestMapping(value="/addMaterialPlan",method=RequestMethod.POST)
     @ResponseBody
     public DataWrapper<Void> addFloder(
    		@ModelAttribute MaterialPlan floder,
    		@RequestParam(value="start",required=false) String start,
    		@RequestParam(value="end",required=false) String end,
    		@RequestParam(value="token",required=true) String token) throws ParseException{
        return MaterialPlanService.addMaterialPlan(token, floder,start,end);
     }
	 /*
	  * 删除材料
	  * */
	 @RequestMapping(value="/deleteMaterialPlanById",method=RequestMethod.GET)
	 @ResponseBody
	 public DataWrapper<Void> deleteMaterialPlan(
			 @RequestParam(value="token",required=true) String token,
			 @RequestParam(value="id",required=true) Long id){
		 return MaterialPlanService.deleteMaterialPlan(token, id);
	 }
	 
	 /*
	  * 修改材料
	  * 
	  * */
	 @RequestMapping(value="/updateMaterialPlanById",method=RequestMethod.POST)
	 @ResponseBody
	 public DataWrapper<Void> updateMaterialPlan(
			 @ModelAttribute MaterialPlan mp,
			 @RequestParam(value="token",required=true) String token){
		 return MaterialPlanService.updateMaterialPlan(token, mp);
	 }
	
	 
	 
	 /*
	  * 获取材料的树状结构
	  * */
     @RequestMapping(value="/getMaterialPlanList",method=RequestMethod.GET)
     @ResponseBody
     public DataWrapper<Object> getMaterialPlanList(
    		@ModelAttribute MaterialPlan mp,
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="dates",required=false) String dates){
        return MaterialPlanService.getMaterialPlanList(token, mp,dates);
     }
	 
    
    
     
}
