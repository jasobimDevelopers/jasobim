package com.my.spring.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.ConstructPart;
import com.my.spring.service.ConstructPartService;
import com.my.spring.utils.DataWrapper;

/**
* @author 徐雨祥
* @version 创建时间：2018年8月31日 下午2:24:25
* 类说明
*/
@Controller
@RequestMapping(value="api/constructPart")
public class ConstructPartController {
	    @Autowired
	    ConstructPartService constructPartService;
	    /**	     *新增接口
	     * */
	    @RequestMapping(value="/admin/addConstructPart", method = RequestMethod.POST)
	    @ResponseBody
	    public DataWrapper<ConstructPart> addConstructPart(
	            @ModelAttribute ConstructPart constructPart,
	            @RequestParam(value = "token",required = true) String token){
	        return constructPartService.addConstructPart(constructPart,token);
	    }
	    /**
	     * 删除接口
	     * */
	    @RequestMapping(value="/deleteConstructPart",method=RequestMethod.GET)
	    @ResponseBody
	    public DataWrapper<Void> deleteConstructPart(
	            @RequestParam(value = "id",required = true) Long id,
	            @RequestParam(value = "token",required = true) String token){
	        return constructPartService.deleteConstructPartById(id,token);
	    }

	    /**
	     * 列表获取接口
	     * */
	    @RequestMapping(value="/admin/getConstructPartList", method = RequestMethod.GET)
	    @ResponseBody
	    public DataWrapper<List<ConstructPart>> getConstructionTaskNewList(
	            @RequestParam(value = "token",required = true) String token,
	    		@ModelAttribute ConstructPart constructPart){
	        return constructPartService.getConstructPartList(token,constructPart);
	    }
}
