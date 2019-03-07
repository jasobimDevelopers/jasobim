package com.my.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.Production;
import com.my.spring.model.ProductionPojo;
import com.my.spring.service.ProductionService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/production")
public class ProductionController {
	@Autowired
	ProductionService productionService;
	
	/**
	 * 
	 * @param userName、password、realName   //必须
	 * @param email、tel可有可无
	 * 其他参数不需要，由程序指定，如日期，用户类型
	 * @return
	 */
	@RequestMapping(value="/addProduction", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProduction(
    		@ModelAttribute Production production,
    		@RequestParam(value="token",required=true) String token) {
        return productionService.addProduction(production.getSomeThing(),token,production.getProfessionType());
    }
	
	
	@RequestMapping(value="/admin/deleteProduction", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteProductionByAdmin(
    		@RequestParam(value="ProductionId",required=true) String ProductionId,
    		@RequestParam(value="token",required=true) String token) {
	        return productionService.deleteProduction(ProductionId,token);
    }

	
	@RequestMapping(value="/admin/getProductionList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProductionPojo>> getProductionListByAdmin(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Production Production,
    		@RequestParam(value="token",required=true) String token) {
        return productionService.getProductionList(pageIndex,pageSize,Production,token);
    }
	

}
