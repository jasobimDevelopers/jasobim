 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.DepartmentPojo;
import com.my.spring.model.ItemData;
import com.my.spring.model.ItemDataPojo;
import com.my.spring.service.ItemDataService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequestMapping(value="api/itemData")
public class ItemDataController {
    @Autowired
    ItemDataService ItemDataService;
    @RequestMapping(value="/admin/addItemData", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addItemData(
            @ModelAttribute ItemData ItemData,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=ItemDataService.addItemData(ItemData,token);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }

    @RequestMapping(value="/admin/updateItemData",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateItemData(
            @ModelAttribute ItemData ItemData,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(ItemData);
        return ItemDataService.updateItemData(ItemData,token);
    }


    @RequestMapping(value="/admin/getItemDataList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ItemDataPojo>> getItemDataList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ItemData ItemData){
        return ItemDataService.getItemDataList(token,pageIndex,pageSize,ItemData);
    }
}
