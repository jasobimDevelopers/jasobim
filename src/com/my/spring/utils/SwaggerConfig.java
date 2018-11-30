package com.my.spring.utils;

import org.springframework.context.annotation.Bean;  
//import org.springframework.context.annotation.ComponentScan;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;  
  
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
@Configuration //必须存在
@EnableSwagger2 //必须存在
@EnableWebMvc //必须存在
public class SwaggerConfig{
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.my.spring.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("前台API接口")
                .description("前台API接口")
                .version("1.1.0")
                .build();
    }
}