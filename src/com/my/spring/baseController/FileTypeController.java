package com.my.spring.baseController;

import com.my.spring.model.FileType;
import com.my.spring.service.FileTypeService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/fileType")
public class FileTypeController {
    @Autowired
    FileTypeService fileTypeService;
    @RequestMapping(value="addFileType", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addFileTypet(
            @ModelAttribute FileType fileType,
            @RequestParam(value = "token",required = false) String token){
        return fileTypeService.addFileType(fileType);
    }
    @RequestMapping(value="deleteFileTypet")
    @ResponseBody
    public DataWrapper<Void> deleteFileTypet(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return fileTypeService.deleteFileType(id);
    }

    @RequestMapping(value="updateFileTypet",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateFileTypet(
            @ModelAttribute FileType fileTypet,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(fileTypet);
        return fileTypeService.updateFileType(fileTypet);
    }


    @RequestMapping(value="getFileTypetList")
    @ResponseBody
    public DataWrapper<List<FileType>> getFileTypetList(
            @RequestParam(value = "token",required = false) String token){
        return fileTypeService.getFileTypeList();
    }
}
