package com.my.spring.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.my.spring.model.User;

@Controller
public class RestTemplateAction {

    @Autowired
    private RestTemplate template;

    @RequestMapping("RestTem")
    public @ResponseBody User RestTem(String method) {
        User user = null;
        //查找
        if ("get".equals(method)) {
            user = template.getForObject(
                    "http://localhost:8080/tao-manager-web/get/{id}",
                    User.class, "呜呜呜呜");

            //getForEntity与getForObject的区别是可以获取返回值和状态、头等信息
            ResponseEntity<User> re = template.
                    getForEntity("http://localhost:8080/tao-manager-web/get/{id}",
                    User.class, "呜呜呜呜");
            System.out.println(re.getStatusCode());
           // System.out.println(re.getBody().getUsername());

        //新增
        } else if ("post".equals(method)) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Auth-Token", UUID.randomUUID().toString());
            MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
            postParameters.add("id", "啊啊啊");
            postParameters.add("name", "部版本");
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
                    postParameters, headers);
            user = template.postForObject(
                    "https://api.sms.jpush.cn/v1/codes", requestEntity,
                    User.class);
        //删除
        } else if ("delete".equals(method)) {
            template.delete("http://localhost:8080/tao-manager-web/delete/{id}","aaa");
        //修改
        } else if ("put".equals(method)) {
            template.put("http://localhost:8080/tao-manager-web/put/{id}",null,"bbb");
        }
        return user;

    }
}