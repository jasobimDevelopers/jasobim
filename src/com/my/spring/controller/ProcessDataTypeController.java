package com.my.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.ProcessDataTypePojo;
import com.my.spring.service.ProcessDataTypeService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/processDataType")
public class ProcessDataTypeController {
	@Autowired
	ProcessDataTypeService processDataTypeService;
	
	/**
	 * 
	 * @param userName、password、realName   //必须
	 * @param email、tel可有可无
	 * 其他参数不需要，由程序指定，如日期，用户类型
	 * @return
	 */
	@RequestMapping(value="/addProcessDataType", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProcessDataType(
    		@RequestParam(value="name",required=true) String name,
    		@RequestParam(value="token",required=true) String token) {
        return processDataTypeService.addProcessDataType(name,token);
    }
	
	
	@RequestMapping(value="/admin/deleteProcessDataType", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteProcessDataTypeByAdmin(
    		@RequestParam(value="id",required=true) Long id,
    		@RequestParam(value="token",required=true) String token) {
	        return processDataTypeService.deleteProcessDataType(id,token);
    }

	//管理员获取用户列表
	@RequestMapping(value="/admin/getProcessDataTypeList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProcessDataTypePojo>> getProcessDataTypeListByAdmin(
    		@RequestParam(value="token",required=true) String token) {
        return processDataTypeService.getProcessDataTypeList(token);
    }
	

}
