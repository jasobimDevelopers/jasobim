package com.my.spring.baseController;

import com.my.spring.model.Element;
import com.my.spring.service.ElementService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/element")
public class ElementController {
    @Autowired
    ElementService elementService;
    @RequestMapping(value="addElement", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addElement(
            @ModelAttribute Element element,
            @RequestParam(value = "token",required = false) String token){
        return elementService.addElement(element);
    }
    @RequestMapping(value="deleteElement")
    @ResponseBody
    public DataWrapper<Void> deleteElement(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return elementService.deleteElement(id);
    }

    @RequestMapping(value="updateElement",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateElement(
            @ModelAttribute Element element,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(element);
        return elementService.updateElement(element);
    }


    @RequestMapping(value="getElementList")
    @ResponseBody
    public DataWrapper<List<Element>> getElementList(
            @RequestParam(value = "token",required = false) String token){
        return elementService.getElementList();
    }
}
