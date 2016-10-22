package com.my.spring.baseController;

import com.my.spring.model.ProjectEntity;
import com.my.spring.service.ProjectService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @RequestMapping(value="addProject", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProject(
            @ModelAttribute ProjectEntity project,
            @RequestParam(value = "token",required = false) String token){
        return projectService.addProject(project);
    }
    @RequestMapping(value="deleteProject")
    @ResponseBody
    public DataWrapper<Void> deleteProject(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return projectService.deleteProject(id);
    }

    @RequestMapping(value="updateProject",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateProject(
            @ModelAttribute ProjectEntity project,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(project);
        return projectService.updateProject(project);
    }


    @RequestMapping(value="getProjectList")
    @ResponseBody
    public DataWrapper<List<ProjectEntity>> getProjectList(
            @RequestParam(value = "token",required = false) String token){
        return projectService.getProjectList();
    }
}
