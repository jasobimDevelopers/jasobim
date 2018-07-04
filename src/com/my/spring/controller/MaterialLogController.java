 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MaterialLog;
import com.my.spring.model.MaterialLogPojo;
import com.my.spring.service.MaterialLogService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value="api/materialLog")
public class MaterialLogController {
    @Autowired
    MaterialLogService materialLogService;
    @RequestMapping(value="/vs/addMaterialLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addNews(
            @ModelAttribute MaterialLog news,
            @RequestParam(value = "date",required = false) String date,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=materialLogService.addMaterialLog(news,token,date);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/deleteMaterialLog",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteMaterialLog(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return materialLogService.deleteMaterialLog(id,token);
    }

    @RequestMapping(value="/vs/updateMaterialLog",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMaterialLog(
            @ModelAttribute MaterialLog News,
            @RequestParam(value = "token",required = false) String token){
        return materialLogService.updateMaterialLog(News,token);
    }


    @RequestMapping(value="/vs/getMaterialLogList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MaterialLogPojo>> getMaterialLogList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute MaterialLog News){
        return materialLogService.getMaterialLogList(token,pageIndex,pageSize,News);
    }
   
}
