package com.my.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.ProductionRecords;
import com.my.spring.service.ProductionRecordsService;
import com.my.spring.utils.DataWrapper;

/**
* @author 徐雨祥
* @version 创建时间：2018年8月31日 下午2:24:25
* 类说明
*/
@Controller
@RequestMapping(value="api/productionRecords")
public class ProductionRecordsController {
	    @Autowired
	    ProductionRecordsService productionRecordsService;
	    /**	     *新增接口
	     * */
	    @RequestMapping(value="/admin/addProductionRecords", method = RequestMethod.POST)
	    @ResponseBody
	    public DataWrapper<ProductionRecords> addProductionRecords(
	            @ModelAttribute ProductionRecords constructPart,
	            @RequestParam(value = "token",required = true) String token){
	        return productionRecordsService.addProductionRecords(constructPart,token);
	    }
	    /**
	     * 删除接口
	     * */
	    @RequestMapping(value="/deleteProductionRecords",method=RequestMethod.GET)
	    @ResponseBody
	    public DataWrapper<Void> deleteProductionRecords(
	            @RequestParam(value = "id",required = true) Long id,
	            @RequestParam(value = "token",required = true) String token){
	        return productionRecordsService.deleteProductionRecordsById(id,token);
	    }

	    /**
	     * 列表获取接口
	     * */
	    @RequestMapping(value="/admin/getProductionRecordsList", method = RequestMethod.GET)
	    @ResponseBody
	    public DataWrapper<List<ProductionRecords>> getConstructionTaskNewList(
	            @RequestParam(value = "token",required = true) String token,
	            @RequestParam(value = "projectId",required = true) Long projectId){
	        return productionRecordsService.getProductionRecordsList(token,projectId);
	    }
}
