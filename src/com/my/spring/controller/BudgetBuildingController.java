package com.my.spring.controller;
import com.my.spring.model.BudgetBuilding;
import com.my.spring.service.BudgetBuildingService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(value="api/budgetBuilding")
public class BudgetBuildingController {
	@Autowired
	BudgetBuildingService budgetBuildingService;
    @RequestMapping(value="/vs/addBudgetBuilding", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<BudgetBuilding> addBudgetBuilding(
            @ModelAttribute BudgetBuilding news,
            @RequestParam(value = "token",required = true) String token){
        return budgetBuildingService.addBudgetBuilding(news,token);
    }
    @RequestMapping(value="/admin/deleteBudget",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteBudgetBuilding(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return budgetBuildingService.deleteBudgetBuilding(id,token);
    }

    @RequestMapping(value="/updateBudgetBuilding",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateBudgetBuilding(
            @ModelAttribute BudgetBuilding News,
            @RequestParam(value = "token",required = false) String token){
        return budgetBuildingService.updateBudgetBuilding(News,token);
    }


    @RequestMapping(value="/vs/getBudgetBuildingList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<BudgetBuilding>> getBudgetBuildingList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute BudgetBuilding News){
        return budgetBuildingService.getBudgetBuildingList(token,pageIndex,pageSize,News);
    }
   
}
