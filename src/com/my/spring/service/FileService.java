package com.my.spring.service;

import com.my.spring.utils.DataWrapper;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface FileService {
    DataWrapper<Void> uploadFile(HttpServletRequest request,MultipartFile file);
}
