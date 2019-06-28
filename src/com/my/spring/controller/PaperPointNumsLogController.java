package com.my.spring.controller;

import com.my.spring.model.PaperPointNumsLog;
import com.my.spring.service.PaperPointNumsLogService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="api/paperPointNumsLog")
public class PaperPointNumsLogController {
    @Autowired
    PaperPointNumsLogService PaperPointNumsLogService;
    @RequestMapping(value="/addPaperPointNumsLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addPaperPointNumsLog(
            @ModelAttribute PaperPointNumsLog PaperPointNumsLog,
            @RequestParam(value = "token",required = true) String token){
        return PaperPointNumsLogService.addPaperPointNumsLog(PaperPointNumsLog,token);
    }
    @RequestMapping(value="/deletePaperPointNumsLog",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deletePaperPointNumsLog(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return PaperPointNumsLogService.deletePaperPointNumsLog(id,token);
    }
    @RequestMapping(value="/updatePaperPointNumsLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updatePaperPointNumsLog(
            @ModelAttribute PaperPointNumsLog PaperPointNumsLog,
            @RequestParam(value = "token",required = true) String token){
        return PaperPointNumsLogService.updatePaperPointNumsLog(PaperPointNumsLog,token);
    }

}
