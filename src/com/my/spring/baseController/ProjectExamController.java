package com.my.spring.baseController;

import com.my.spring.model.ProjectExam;
import com.my.spring.service.ProjectExamService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/projectExam")
public class ProjectExamController {
    @Autowired
    ProjectExamService ProjectExamService;
    @RequestMapping(value="addProjectExam", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProjectExam(
            @ModelAttribute ProjectExam projectExam,
            @RequestParam(value = "token",required = false) String token){
        	///////////////////工程量计算
    	    /*按照楼号计算*/
    	
    	return ProjectExamService.addProjectExam(projectExam);
    }
    @RequestMapping(value="deleteProjectExam")
    @ResponseBody
    public DataWrapper<Void> deleteProjectExam(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return ProjectExamService.deleteProjectExam(id);
    }

    @RequestMapping(value="updateProjectExam",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateProjectExam(
            @ModelAttribute ProjectExam projectExam,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(projectExam);
        return ProjectExamService.updateProjectExam(projectExam);
    }


    @RequestMapping(value="getProjectExamList")
    @ResponseBody
    public DataWrapper<List<ProjectExam>> getProjectExamList(
            @RequestParam(value = "token",required = false) String token){
        return ProjectExamService.getProjectExamList();
    }
}
