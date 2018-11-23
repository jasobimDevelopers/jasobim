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
import com.my.spring.model.DepartmentPojo;
import com.my.spring.service.DepartmentService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;  
   
    @RequestMapping(value="/admin/addDepartment", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Department> addDepartment(
            @ModelAttribute Department department,
            @RequestParam(value = "token",required = true) String token){
        return departmentService.addDepartment(token,department);
    }
    @RequestMapping(value="/admin/updateDepartment", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateDepartment(
            @ModelAttribute Department department,
            @RequestParam(value = "token",required = true) String token){
        return departmentService.updateDepartment(token,department);
    }
    @RequestMapping(value="/admin/deleteDepartment",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteDepartment(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return departmentService.deleteDepartmentById(token,id);
    }
   

    @RequestMapping(value="/admin/getDepartmentList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<DepartmentPojo>> getDepartmentList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Department department,
            @RequestParam(value = "token",required = false) String token){
        return departmentService.getDepartmentList(pageIndex,pageSize,department,token);
    }
    @RequestMapping(value="/admin/getDepartmentById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<DepartmentPojo> getDepartmentById(
    		@RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = false) String token){
        return departmentService.getDepartmentById(token,id);
    }

}
