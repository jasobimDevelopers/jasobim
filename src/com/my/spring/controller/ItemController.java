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
import com.my.spring.model.MinItem;
import com.my.spring.model.MinItemPojo;
import com.my.spring.service.ItemService;
import com.my.spring.utils.DataWrapper;

/**
 * Created by xyx 2016/11/1.
 */
@Controller
@RequestMapping(value="api/item")
public class ItemController {
    @Autowired
    ItemService itemService;  
    /*
     *模型的原始构件信息上传
     */
    @RequestMapping(value="/admin/uploadItem", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> uploadItem(
            @RequestParam(value = "fileList", required = false) MultipartFile[] fileList,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "projectId",required = true) Long projectId,
            HttpServletRequest request){
    	String filePath = "/fileupload/items";
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	for(int i=0;i<fileList.length;i++){
    		if(itemService.batchImport(filePath, fileList[i],token,request,projectId)){
            	dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
            }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
    	}
    	
        return dataWrapper;
    }
    
   /* 
     *模型的原始构件信息上传(客户端生成二维码图片)
     
    @RequestMapping(value="/admin/uploadItems", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> uploadItems(
            @RequestParam(value = "fileList", required = false) MultipartFile[] fileList,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "projectId",required = true) Long projectId,
            HttpServletRequest request){
    	String filePath = "/fileupload/items";
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	for(int i=0;i<fileList.length;i++){
    		if(itemService.batchImportss(filePath, fileList[i],token,request,projectId)){
            	dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
            }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
    	}
    	
        return dataWrapper;
    }*/
    
    /*
     *轻量化处理后的模型构件信息上传
     */
    @RequestMapping(value="/admin/uploadMinItem", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> uploadMinItem(
            @RequestParam(value = "fileList", required = false) MultipartFile[] fileList,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "projectId",required = true) Long projectId,
            HttpServletRequest request){
    	String filePath = "/fileupload/minitems";
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	for(int i=0;i<fileList.length;i++){
    		if(itemService.batchImports(filePath, fileList[i],token,request,projectId)){
            	dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
            }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
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
    /*@RequestMapping(value="/deleteItemByTypeNameAndProjectId")
    @ResponseBody
    public DataWrapper<Void> deleteItemByTypeNameAndProjectId(
            @RequestParam(value = "typename",required = true) String typename,
            @RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token){
        return itemService.deleteItemByTypeNameAndProjectId(projectId,typename,token);
    }*/
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
    	if(item.getFloorNum()!=null){
    		if(item.getFloorNum()==1){
        		item.setFloorNum(3);////对应地下二层
        	}
    	}
    	
        return itemService.getItemList(projectId,pageIndex,pageSize,item,token);
    }
    @RequestMapping(value="/admin/getMinItemList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MinItem>> getMinItemList(
    		@RequestParam(value="projectId",required=true) Long projectId,
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute MinItem item,
            @RequestParam(value = "token",required = false) String token){
    	if(item.getFloorNum()!=null){
    		if(item.getFloorNum()==1){
        		item.setFloorNum(3);
        	}
    	}
    	
        return itemService.getMinItemList(projectId,pageIndex,pageSize,item,token);
    }
    @RequestMapping(value="/getItemById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Item> getItemById(
    		@RequestParam(value = "itemId",required = true) Long itemId,
            @RequestParam(value = "token",required = false) String token){
        return itemService.getItemById(itemId,token);
    }
    @RequestMapping(value="/getMinItemById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<MinItemPojo> getMinItemById(
    		@RequestParam(value = "id",required = true) Long id
            ){
    	DataWrapper<MinItemPojo> dataWrapper = new DataWrapper<MinItemPojo>();
    	dataWrapper=itemService.getMinItemById(id);
        return dataWrapper;
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
    @RequestMapping(value="/getCodeImg",method=RequestMethod.POST)
    @ResponseBody
    public String getCodeImg(
    		HttpServletRequest request,
    		@ModelAttribute Item item
    		){
    	
        return itemService.getCodeImg(item,request);
    }

}
