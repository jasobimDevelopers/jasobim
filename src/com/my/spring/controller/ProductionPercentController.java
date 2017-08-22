package com.my.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.ProductionPercent;
import com.my.spring.model.ProductionPercentPojo;
import com.my.spring.service.ProductionPercentService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/ProductionPercent")
public class ProductionPercentController {
	@Autowired
	ProductionPercentService productionPercentService;
	
	/**
	 * 
	 * @param userName、password、realName   //必须
	 * @param email、tel可有可无
	 * 其他参数不需要，由程序指定，如日期，用户类型
	 * @return
	 */
	@RequestMapping(value="/addProductionPercent", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProductionPercent(
    		@ModelAttribute ProductionPercent ProductionPercent,
    		@RequestParam(value="token",required=true) String token) {
        return productionPercentService.addProductionPercent(null,token,null);
    }
	
	
	@RequestMapping(value="/admin/deleteProductionPercent", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteProductionPercentByAdmin(
    		@RequestParam(value="ProductionPercentId",required=true) String productionPercentId,
    		@RequestParam(value="token",required=true) String token) {
	        return productionPercentService.deleteProductionPercentPercent(productionPercentId,token);
    }

	//管理员获取用户列表
	@RequestMapping(value="/admin/getProductionPercentList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProductionPercentPojo>> getProductionPercentListByAdmin(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ProductionPercent ProductionPercent,
    		@RequestParam(value="token",required=true) String token) {
        return productionPercentService.getProductionPercentList(pageIndex,pageSize,ProductionPercent,token);
    }
	

}
