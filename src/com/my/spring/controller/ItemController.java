package com.my.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Item;
import com.my.spring.service.ItemService;
import com.my.spring.utils.DataWrapper;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/item")
public class ItemController {
    @Autowired
    ItemService itemService;
    @RequestMapping(value="/uploadItem", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> uploadItem(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request){
    	String filePath = "/fileupload/items";
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	if(itemService.batchImport(filePath, file,token,request)){
        	dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
        }else{
        	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }
    @RequestMapping(value="/addItem", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addItem(
            @ModelAttribute Item item,
            @RequestParam(value = "token",required = true) String token){
        return itemService.addItem(item,token);
    }
    @RequestMapping(value="/deleteItem")
    @ResponseBody
    public DataWrapper<Void> deleteItem(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return itemService.deleteItem(id,token);
    }
    @RequestMapping(value="/deleteItemByTypeNameAndProjectId")
    @ResponseBody
    public DataWrapper<Void> deleteItemByTypeNameAndProjectId(
            @RequestParam(value = "typename",required = true) String typename,
            @RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token){
        return itemService.deleteItemByTypeNameAndProjectId(projectId,typename,token);
    }
    @RequestMapping(value="/deleteItemByProjectId")
    @ResponseBody
    public DataWrapper<Void> deleteItemByProjectId(
            @RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = false) String token){
        return itemService.deleteItemByProjectId(projectId,token);
    }


    @RequestMapping(value="/admin/updateItem",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateItem(
            @ModelAttribute Item Item,
            @RequestParam(value = "token",required = true) String token){
        System.out.println(Item);
        return itemService.updateItem(Item,token);
    }


    @RequestMapping(value="/admin/getItemList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<Item>> getItemList(
    		@RequestParam(value="projectId",required=true) Long projectId,
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Item item,
            @RequestParam(value = "token",required = false) String token){
        return itemService.getItemList(projectId,pageIndex,pageSize,item,token);
    }
    @RequestMapping(value="/getItemById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Item> getItemById(
    		@RequestParam(value = "itemId",required = true) Long itemId,
            @RequestParam(value = "token",required = false) String token){
        return itemService.getItemById(itemId,token);
    }
    @RequestMapping(value="/getItemByOthers")
    @ResponseBody
    public DataWrapper<List<Item>> getItemByOthers(
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "typeName",required = false) Long typeName,
    		@RequestParam(value = "buildingNum",required = false) Long buildingNum,
    		@RequestParam(value = "floorNum",required = false) Long floorNum,
    		@RequestParam(value = "unitNum",required = false) Long unitNum,
    		@RequestParam(value = "householdNum",required = false) Long householdNum,
    		@RequestParam(value = "token",required = false) String token){
        return itemService.getItemByOthers(projectId,typeName,buildingNum,floorNum,unitNum,householdNum,token);
    }
    /*
     * 查询项目相应栋号下的地下层层数
     * */
    @RequestMapping(value="/getItemByBase",method=RequestMethod.GET)
    @ResponseBody
    public Long getItemByBase(
    	@RequestParam(value = "projectId",required = true) Long projectId,
    	@RequestParam(value = "buildingId",required = true) Long buildingId,
    	@RequestParam(value = "token",required = true) String token
    		){
        return itemService.getItemByBase(projectId,buildingId,token);
    }
    /*
     * 查询项目相应栋号楼层数
     * */
    @RequestMapping(value="/getItemByBuidlingNum",method=RequestMethod.GET)
    @ResponseBody
    public Long getItemByBuidlingNum(
    	@RequestParam(value = "projectId",required = true) Long projectId,
    	@RequestParam(value = "buildingId",required = true) Long buildingId,
    	@RequestParam(value = "token",required = true) String token
    		){
        return itemService.getItemByBuidlingNum(projectId,buildingId,token);
    }
    
    @RequestMapping(value="/getHouseHoldType",method=RequestMethod.GET)
    @ResponseBody
    public List<Object> getHouseHoldType(
    	@RequestParam(value = "projectId",required = true) Long projectId,
    	@RequestParam(value = "buildingId",required = true) Long buildingId,
    	@RequestParam(value = "floorId",required = true) Long floorId,
    	@RequestParam(value = "token",required = true) String token
    		){
        return itemService.getHouseHoldType(projectId, buildingId, floorId, token);
    }

}
