 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ConstructionTaskNew;
import com.my.spring.model.ConstructionTaskNewPojo;
import com.my.spring.service.ConstructionTaskNewService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequestMapping(value="api/constructionTaskNew")
public class ConstructionTaskNewController {
    @Autowired
    ConstructionTaskNewService ConstructionTaskNewService;
    @RequestMapping(value="/admin/addConstructionTaskNew", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addConstructionTaskNew(
            @ModelAttribute ConstructionTaskNew ConstructionTaskNew,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=ConstructionTaskNewService.addConstructionTaskNew(ConstructionTaskNew,token);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/deleteConstructionTaskNew",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteConstructionTaskNew(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return ConstructionTaskNewService.deleteConstructionTaskNew(id,token);
    }

    @RequestMapping(value="/admin/updateConstructionTaskNew",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateConstructionTaskNew(
            @ModelAttribute ConstructionTaskNew ConstructionTaskNew,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(ConstructionTaskNew);
        return ConstructionTaskNewService.updateConstructionTaskNew(ConstructionTaskNew,token);
    }


    @RequestMapping(value="/admin/getConstructionTaskNewList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ConstructionTaskNewPojo>> getConstructionTaskNewList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "type",required = true) Integer type,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ConstructionTaskNew ConstructionTaskNew){
        return ConstructionTaskNewService.getConstructionTaskNewList(token,pageIndex,pageSize,ConstructionTaskNew,type);
    }
   
}
