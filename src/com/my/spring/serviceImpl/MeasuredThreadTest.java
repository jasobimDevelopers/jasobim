package com.my.spring.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.my.spring.DAO.PaperPointInfoDao;
import com.my.spring.model.BuildingPointNums;
public class MeasuredThreadTest extends Thread{
	private String buildings;
	private String checkTypes;
	private Long projectId; 
	private List<BuildingPointNums> testLists;
	@Autowired
    PaperPointInfoDao paperPointInfoDao;
    public MeasuredThreadTest(String buildings,String checkTypes,Long projectId,PaperPointInfoDao paperPointInfoDao)
    {
        this.buildings = buildings;
        this.checkTypes=checkTypes;
        this.projectId=projectId;
        this.paperPointInfoDao=paperPointInfoDao;
    }
    /*public void run()
    {
    	
    	this.testLists=paperPointInfoDao.getCountPointNumsList(buildings,checkTypes,projectId);
    }*/
   /* public static void main(String[] args)
    {
        Thread thread = new MeasuredThreadTest();
        thread.start();        
    }*/
	public List<BuildingPointNums> getTestLists() {
		return testLists;
	}
	public void setTestLists(List<BuildingPointNums> testLists) {
		this.testLists = testLists;
	}
}
