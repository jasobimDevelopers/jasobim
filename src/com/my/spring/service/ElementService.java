package com.my.spring.service;

import com.my.spring.model.Element;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface ElementService {
    DataWrapper<Void> addElement(Element element);
    DataWrapper<Void> deleteElement(Long id);
    DataWrapper<Void> updateElement(Element element);
    DataWrapper<List<Element>> getElementList();
    public boolean batchImport(String name,MultipartFile file);
}
