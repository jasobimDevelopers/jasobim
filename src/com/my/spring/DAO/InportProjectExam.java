package com.my.spring.DAO;

import org.springframework.beans.factory.annotation.Autowired;

import com.my.spring.DAOImpl.QuantityDaoImpl;
import com.my.spring.model.Quantity;

public class InportProjectExam {
	@Autowired
    QuantityDaoImpl projectExamController;
	Quantity projectExam =new Quantity();
	
}
