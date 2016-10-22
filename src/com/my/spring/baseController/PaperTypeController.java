package com.my.spring.baseController;

import com.my.spring.model.PaperType;
import com.my.spring.service.PaperTypeService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/paperType")
public class PaperTypeController {
    @Autowired
    PaperTypeService paperTypeService;
    @RequestMapping(value="addPaperType", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addPaperType(
            @ModelAttribute PaperType paperType,
            @RequestParam(value = "token",required = false) String token){
        return paperTypeService.addPaperType(paperType);
    }
    @RequestMapping(value="deletePaperType")
    @ResponseBody
    public DataWrapper<Void> deletePaperType(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return paperTypeService.deletePaperType(id);
    }

    @RequestMapping(value="updatePaperType",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updatePaperType(
            @ModelAttribute PaperType paperType,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(paperType);
        return paperTypeService.updatePaperType(paperType);
    }


    @RequestMapping(value="getPaperTypeList")
    @ResponseBody
    public DataWrapper<List<PaperType>> getPaperTypeList(
            @RequestParam(value = "token",required = false) String token){
        return paperTypeService.getPaperTypeList();
    }
}
