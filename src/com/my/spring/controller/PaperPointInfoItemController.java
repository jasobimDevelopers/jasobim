package com.my.spring.controller;

import com.my.spring.model.PaperPointInfoItem;
import com.my.spring.model.PaperPointInfoItemPojo;
import com.my.spring.service.PaperPointInfoItemService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(value="api/paperPointInfoItem")
public class PaperPointInfoItemController {
    @Autowired
    PaperPointInfoItemService paperPointInfoItemService;
    @RequestMapping(value="/addPaperPointInfoItem", method = RequestMethod.POST)
    @ResponseBody
    //@Cacheable(value="user-key", condition="#userId <50")
    public DataWrapper<Void> addPaperPointInfoItem(
            @ModelAttribute PaperPointInfoItem paperPointInfoItem,
            @RequestParam(value = "token",required = true) String token){
        return paperPointInfoItemService.addPaperPointInfoItem(paperPointInfoItem,token);
    }
    @RequestMapping(value="/deletePaperPointInfoItem", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deletePaperPointInfoItem(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return paperPointInfoItemService.deletePaperPointInfoItem(id,token);
    }

  /*  @RequestMapping(value="/getPaperPointInfoItemListByPointId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<PaperPointInfoItem>> getPaperPointInfoItemByProjectId(
    		@RequestParam(value = "pointId",required = true) Long pointId,
    		@RequestParam(value = "token",required = true) String token){
        return paperPointInfoItemService.getPaperPointInfoItemByPointId(pointId,token);
    }*/
    @RequestMapping(value="/getPaperPointInfoItemListByPointId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<PaperPointInfoItemPojo>> getPaperPointInfoItemListByPointId(
    		@RequestParam(value = "pointId",required = true) Long pointId,
    		@RequestParam(value = "token",required = true) String token){
        return paperPointInfoItemService.getPaperPointInfoItemListsByPointId(pointId,token);
    }
    @RequestMapping(value="/getPaperPointInfoItemModelListByPointId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<PaperPointInfoItem>> getPaperPointInfoItemModelListByPointId(
    		@RequestParam(value = "pointId",required = true) Long pointId,
    		@RequestParam(value = "token",required = true) String token){
        return paperPointInfoItemService.getPaperPointInfoItemModelListByPointId(pointId,token);
    }
}
