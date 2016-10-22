package com.my.spring.DAO;

import org.springframework.beans.factory.annotation.Autowired;

import com.my.spring.DAOImpl.ProjectExamDaoImpl;
import com.my.spring.model.ProjectExam;

public class InportProjectExam {
	@Autowired
    ProjectExamDaoImpl projectExamController;
	ProjectExam projectExam =new ProjectExam();
	
}
