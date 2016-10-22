package test;

import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangofactory.swagger.plugin.EnableSwagger;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@EnableSwagger
@RequestMapping("/mvc")
public class TestController {
 
	@RequestMapping(value="/user/{id}",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据用户名获取用户对象", httpMethod = "GET", response = String.class, notes = "根据用户名获取用户对象")
    public void get(@PathVariable Integer id,PrintWriter pw){
        System.out.println("get"+id);
        //return "/hello";
        pw.print("hello:"+id);
    }
     
//    @RequestMapping(value="/user/{id}",method=RequestMethod.POST)
//    public String post(@PathVariable("id") Integer id){
//        System.out.println("post"+id);
//        return "/hello";
//    }
//     
//    @RequestMapping(value="/user/{id}",method=RequestMethod.PUT)
//    public String put(@PathVariable("id") Integer id){
//        System.out.println("put"+id);
//        return "/hello";
//    }
//     
//    @RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
//    public String delete(@PathVariable("id") Integer id){
//        System.out.println("delete"+id);
//        return "/hello";
//    }
     
}
