package com.my.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.AttenceModel;
import com.my.spring.model.AttenceModelPojo;
import com.my.spring.service.AttenceModelService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/attenceModel")
public class AttenceModelController {
	@Autowired
    AttenceModelService amService;
    @RequestMapping(value="/addAttenceModel", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addAttenceModel(
            @ModelAttribute AttenceModel am,
            @RequestParam(value = "token",required = true) String token){
        return amService.addAttenceModel(am, token);
    }
    
    @RequestMapping(value="/updateAttenceModel", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateAttenceModel(
            @ModelAttribute AttenceModel am,
            @RequestParam(value = "token",required = true) String token){
        return amService.updateAttenceModel(am, token);
    }
    @RequestMapping(value="/deleteAttenceModel",method=RequestMethod.GET)
    @ResponseBody
    
    public DataWrapper<Void> deleteAttenceModel(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return amService.deleteAttenceModel(id,token);
    }

    @RequestMapping(value="/getAttenceModelList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<AttenceModelPojo>> getAttenceModelList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute AttenceModel ps){
        return amService.getAttenceModelList(token, ps, pageSize, pageIndex);
    }
   
   
}
