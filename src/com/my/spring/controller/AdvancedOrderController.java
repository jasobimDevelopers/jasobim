 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AdvancedOrder;
import com.my.spring.model.AdvancedOrderPojo;
import com.my.spring.service.AdvancedOrderService;
import com.my.spring.utils.DataWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="api/advancedOrder")
public class AdvancedOrderController {
    @Autowired
    AdvancedOrderService AdvancedOrderService;
    @RequestMapping(value="/addAdvancedOrder", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addAdvancedOrder(
            @ModelAttribute AdvancedOrder ps,
            HttpServletRequest request,
            @RequestParam(value = "contentFiles",required = false) MultipartFile[] contentFiles,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=AdvancedOrderService.addAdvancedOrder(ps,token,contentFiles,request);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/deleteAdvancedOrder",method=RequestMethod.GET)
    @ResponseBody
    
    public DataWrapper<Void> deleteAdvancedOrder(
            @RequestParam(value = "id",required = true) String id,
            @RequestParam(value = "token",required = true) String token){
        return AdvancedOrderService.deleteAdvancedOrder(id,token);
    }

    @RequestMapping(value="/admin/getAdvancedOrderList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<AdvancedOrderPojo>> getAdvancedOrderList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@RequestParam(value="content",required=false) String content,
    		@ModelAttribute AdvancedOrder ps){
        return AdvancedOrderService.getAdvancedOrderList(token,pageIndex,pageSize,ps,content);
    }
    ////通过用户id查找留言
    @RequestMapping(value="/getAdvancedOrderListByUserId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<AdvancedOrder>> getNewsListByUserId(
    		@RequestParam(value = "userId",required = true) Long userId,
            @RequestParam(value = "token",required = true) String token){
        return AdvancedOrderService.getAdvancedOrderListByUserId(userId,token);
    }
////通过用户id查找留言
    @RequestMapping(value="/getAdvancedOrderById",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<AdvancedOrderPojo> getAdvancedOrderById(
	    @RequestParam(value = "id",required = true) Long id,
	    @RequestParam(value = "token",required = false) String token,
	    @RequestParam(value = "weixin",required = false) String weixin){
        return AdvancedOrderService.getAdvancedOrderById(id,token,weixin);
    }
    
           
    ////通过用户id查找留言
    @RequestMapping(value="/updateAdvancedOrder",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateAdvancedOrder(
    		@ModelAttribute AdvancedOrder ps,
    		HttpServletRequest request,
    		@RequestParam(value = "photo",required = false) MultipartFile photo,
            @RequestParam(value = "token",required = true) String token){
        return AdvancedOrderService.updateAdvancedOrder(ps,token,request,photo);
    }
////通过用户id查找留言
    @RequestMapping(value="/exportAdvancedOrder",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> exportAdvancedOrder(
    		@RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = true) String token){
        return AdvancedOrderService.exportAdvancedOrder(id,token);
    }
   
}
