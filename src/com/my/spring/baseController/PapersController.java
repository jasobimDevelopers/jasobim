package com.my.spring.baseController;

import com.my.spring.model.Papers;
import com.my.spring.service.PapersService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/papers")
public class PapersController {
    @Autowired
    PapersService papersService;
    @RequestMapping(value="addPapers", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addPapers(
            @ModelAttribute Papers papers,
            @RequestParam(value = "token",required = false) String token){
        return papersService.addPapers(papers);
    }
    @RequestMapping(value="deletePapers")
    @ResponseBody
    public DataWrapper<Void> deletePapers(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return papersService.deletePapers(id);
    }

    @RequestMapping(value="updatePapers",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updatePapers(
            @ModelAttribute Papers papers,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(papers);
        return papersService.updatePapers(papers);
    }


    @RequestMapping(value="getPapersList")
    @ResponseBody
    public DataWrapper<List<Papers>> getPapersList(
            @RequestParam(value = "token",required = false) String token){
        return papersService.getPapersList();
    }
}
