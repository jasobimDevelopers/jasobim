package com.my.spring.DAO;

import com.my.spring.model.Element;
import com.my.spring.model.UserEntity;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface ElementDao {
    boolean addElement(Element element);
    boolean deleteElement(Long id);
    boolean updateElement(Element element);
    DataWrapper<List<Element>> getElementList();
    List<Element> getElementByLocation(String location);
}
