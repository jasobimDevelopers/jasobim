package com.my.spring.baseController;

import com.my.spring.model.RoleEntity;
import com.my.spring.service.RoleService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @RequestMapping(value="addRole", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addRole(
            @ModelAttribute RoleEntity role,
            @RequestParam(value = "token",required = false) String token){
        return roleService.addRole(role);
    }
    @RequestMapping(value="deleteRole")
    @ResponseBody
    public DataWrapper<Void> deleteRole(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return roleService.deleteRole(id);
    }

    @RequestMapping(value="updateRole",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateRole(
            @ModelAttribute RoleEntity role,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(role);
        return roleService.updateRole(role);
    }


    @RequestMapping(value="getRoleList")
    @ResponseBody
    public DataWrapper<List<RoleEntity>> getRoleList(
            @RequestParam(value = "token",required = false) String token){
        return roleService.getRoleList();
    }
}
