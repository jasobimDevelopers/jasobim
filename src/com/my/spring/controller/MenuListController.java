package com.my.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.MenuList;
import com.my.spring.model.MenuListPojo;
import com.my.spring.service.MenuListService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/menu")
public class MenuListController {
    @Autowired
    MenuListService menuListService;  
   
    @RequestMapping(value="/admin/addMenu", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMenu(
            @ModelAttribute MenuList item,
            @RequestParam(value = "token",required = true) String token){
        return menuListService.addMenuList(token,item);
    }
    
    @RequestMapping(value="/admin/updateMenu", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMenu(
            @ModelAttribute MenuList item,
            @RequestParam(value = "token",required = true) String token){
        return menuListService.updateMenu(token,item);
    }
    @RequestMapping(value="/admin/deleteMenu", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteMenu(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return menuListService.deleteMenuListById(token,id);
    }
   
  
    @RequestMapping(value="/admin/getMenuListList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MenuList>> getMenuLists(
            @RequestParam(value = "token",required = false) String token){
        return menuListService.getMenuLists(token);
    }
    
    @RequestMapping(value="/admin/getMenuById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<MenuListPojo> getMenuById(
    		@RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = false) String token){
        return menuListService.getById(token,id);
    }
   
}
