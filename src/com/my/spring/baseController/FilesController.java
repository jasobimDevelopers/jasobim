package com.my.spring.baseController;

import com.my.spring.model.Files;
import com.my.spring.service.FilesService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/files")
public class FilesController {
    @Autowired
    FilesService filesService;
    @RequestMapping(value="addFiles", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addFiles(
            @ModelAttribute Files files,
            @RequestParam(value = "token",required = false) String token){
        return filesService.addFiles(files);
    }
    @RequestMapping(value="deleteFiles")
    @ResponseBody
    public DataWrapper<Void> deleteFiles(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return filesService.deleteFiles(id);
    }

    @RequestMapping(value="updateFiles",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateFiles(
            @ModelAttribute Files files,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(files);
        return filesService.updateFiles(files);
    }


    @RequestMapping(value="getFilesList")
    @ResponseBody
    public DataWrapper<List<Files>> getFilesList(
            @RequestParam(value = "token",required = false) String token){
        return filesService.getFilesList();
    }
}
