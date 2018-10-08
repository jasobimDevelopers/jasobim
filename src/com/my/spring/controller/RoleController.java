package com.my.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.Department;
import com.my.spring.model.Role;
import com.my.spring.model.RolePojo;
import com.my.spring.service.RoleService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/role")
public class RoleController {
    @Autowired
    RoleService roleService;  
    @RequestMapping(value="/admin/addRole", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addRole(
            @ModelAttribute Role role,
            @RequestParam(value = "token",required = true) String token){
        return roleService.addRole(token,role);
    }
    @RequestMapping(value="/admin/deleteRole")
    @ResponseBody
    public DataWrapper<Void> deleteRole(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return roleService.deleteRoleById(token,id);
    }
   


    @RequestMapping(value="/admin/updateRole",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateRole(
            @ModelAttribute Role role,
            @RequestParam(value = "token",required = true) String token){
        return roleService.updateRole(token,role);
    }


    @RequestMapping(value="/admin/getRoleList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<RolePojo>> getRoleList(
            @RequestParam(value = "token",required = false) String token){
        return roleService.getRoleList(token);
    }

}
