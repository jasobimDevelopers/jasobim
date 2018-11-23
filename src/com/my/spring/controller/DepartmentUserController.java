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
import com.my.spring.model.DepartmentUser;
import com.my.spring.model.DepartmentUserPojo;
import com.my.spring.service.DepartmentUserService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/departmentUser")
public class DepartmentUserController {
    @Autowired
    DepartmentUserService departmentUserService;  
   
    @RequestMapping(value="/addDepartmentUser", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addDepartmentUser(
            @ModelAttribute DepartmentUser departmentUser,
            @RequestParam(value = "imgZs",required = false) MultipartFile imgZs,
            @RequestParam(value = "imgFs",required = false) MultipartFile imgFs,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return departmentUserService.addDepartmentUser(token,departmentUser,imgZs,imgFs,request);
    }
    @RequestMapping(value="/updateDepartmentUser", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateDepartmentUser(
            @ModelAttribute DepartmentUser departmentUser,
            @RequestParam(value = "imgZs",required = false) MultipartFile imgZs,
            @RequestParam(value = "imgFs",required = false) MultipartFile imgFs,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return departmentUserService.updateDepartmentUser(token,departmentUser,imgZs,imgFs,request);
    }
    @RequestMapping(value="/deleteDepartmentUser",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteDepartmentUser(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return departmentUserService.deleteDepartmentUserById(token,id);
    }
   

    @RequestMapping(value="/getDepartmentUserList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<DepartmentUserPojo>> getDepartmentUserList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute DepartmentUser departmentUser,
            @RequestParam(value = "token",required = false) String token){
        return departmentUserService.getDepartmentUserList(pageIndex,pageSize,departmentUser,token);
    }
    @RequestMapping(value="/getDepartmentUserById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<DepartmentUserPojo> getDepartmentUserById(
    		@RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = false) String token){
        return departmentUserService.getDepartmentUserById(token,id);
    }

}
