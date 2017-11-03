package com.my.spring.utils;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.ComponentScan;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.config.annotation.EnableWebMvc;  
  
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;  
import com.mangofactory.swagger.models.dto.ApiInfo;  
import com.mangofactory.swagger.plugin.EnableSwagger;  
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;  
  
/** 
 * <B>文件名称：</B>SwaggerConfig.java<BR> 
 * <B>文件描述：</B><BR> 
 *  
 * <B>版权声明：</B>(C)2014-2015<BR> 
 * <B>公司部门：</B><BR> 
 * <B>创建时间：</B>2016年6月30日<BR> 
 *  
 * @author  
 * @version  
 */  
@Configuration  
@EnableWebMvc  
@EnableSwagger  
@ComponentScan("cn.brandwisdom.roomVouchers")  
public class SwaggerConfig {  
  
    private SpringSwaggerConfig springSwaggerConfig;  
  
      
  
      
    @Autowired  
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {  
        this.springSwaggerConfig = springSwaggerConfig;  
    }  
  
    /** 
     * 链式编程 来定制API样式 
     * 后续会加上分组信息 
     * 
     * @return 
     */  
    @Bean  
    public SwaggerSpringMvcPlugin customImplementation() {  
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)  
                .apiInfo(apiInfo())  
                .includePatterns(".*")  
                .apiVersion("0.0.1");  
        //.swaggerGroup(PROJECT_NAME);  
    }  
  
    private ApiInfo apiInfo() {  
        ApiInfo apiInfo = new ApiInfo("My Apps API Title", "My Apps APIDescription", "My Apps API terms ofservice",  
                "My Apps API ContactEmail", "My Apps API LicenceType", "My Apps API LicenseURL");  
        return apiInfo;  
    }  
}  