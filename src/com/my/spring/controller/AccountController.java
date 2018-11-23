package com.my.spring.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.utils.Test;

@Controller
public class AccountController {
	@RequestMapping(value="api/ssocallback", method = RequestMethod.POST)
    @ResponseBody
    public String ssocallback(
    		HttpServletRequest request) throws IOException{
        String access_token = request.getParameter("access_token");
        Test test = new Test();
        String reponse=test.test("http://192.168.2.53:8020/connect/userinfo", access_token);
        if(reponse!=null){
        	return "login";
        }
		return null;
    }
	/*public static String CreateNo()
	{
	     Random random = new Random();
	     //tring strRandom = random.; //生成编号 
	     //String code = DateTime.Now.ToString("yyyyMMddHHmmss") + strRandom;//形如
	     //return code;
	}

	//MD5加密
	/// <summary>
	        /// MD5加密
	        /// </summary>
	        /// <param name="str">加密字符</param>
	        /// <param name="code">加密位数16/32</param>
	        /// <returns></returns>
    public static String MD5(String str, int code)
    {
    	String strEncrypt = String.Empty;
        if (code == 16)
        {
            strEncrypt = System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(str, "MD5").Substring(8, 16);
        }

        if (code == 32)
        {
            strEncrypt = System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(str, "MD5");
        }

        return strEncrypt;
    }      */
}
