package com.my.spring.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.my.spring.model.ContractLofting;
import com.my.spring.model.ProjectPartContractLofting;
import com.my.spring.serviceImpl.ThreadServiceImpl.QuestionNoticeThreadService;

public class TestUtil {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<ProjectPartContractLofting> list = new ArrayList<ProjectPartContractLofting>();
		HashMap<String,List<ProjectPartContractLofting>> lists = new  HashMap<String,List<ProjectPartContractLofting>>();
		ProjectPartContractLofting item1 = new ProjectPartContractLofting();
		item1.setName("电气系统");
		item1.setValue(12.22);
		list.add(item1);
		ProjectPartContractLofting item5 = new ProjectPartContractLofting();
		item5.setName("电气APn01");
		item5.setValue(12.44);
		list.add(item5);
		lists.put("test", list);
		List<ProjectPartContractLofting> listtest = new ArrayList<ProjectPartContractLofting>();
		listtest=lists.get("test");
		for(int i=0;i<listtest.size();i++){
			System.out.println(listtest.get(i).getValue());
		}
		
	}

}
