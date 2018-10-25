package com.my.spring.utils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.my.spring.model.ContractLofting;
import com.my.spring.parameters.Parameters;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
public class TestUtil {
	/**
	 * @param args
	 */
	private static List<Long> idList = new ArrayList<Long>();
	private static List<ContractLofting> plist = new ArrayList<ContractLofting>();
	public TestUtil(){
		
	}
	public TestUtil(List<ContractLofting> plist){
		this.setPlist(plist);
	}
	public static void getFileUploadModel() {//这个方法框架的方法，主要看下面这么获取文件
		 MPPReader reader = new MPPReader();
         ProjectFile  projectFile;
         //projectFile = reader.read(sbs);
        //读本地的.mpp文件，用于测试
         try {
			projectFile = reader.read("C:/Users/Han/Desktop/吉宝住宅进度9.13.mpp");
			 List<Task> list = projectFile.getAllTasks();
	         Task t = (Task) list.get(0);
	         for(int i=1 ; i<list.size();i++){
	             Task task = list.get(i);
	             if(task.getParentTask() != null){
	                 if(task.getParentTask().getUniqueID() == t.getUniqueID()){
	                     String rowguid = UUID.randomUUID().toString();
	                    /* Projectclass pc = new Projectclass();
	                     pc.setRowguid(rowguid);*/
	                     System.out.println("任务名称："+task.getName());
	                     System.out.println("任务开始时间："+ Parameters.getSdfs().format(task.getStart())); 
	                     System.out.println("任务完成时间："+Parameters.getSdfs().format(task.getFinish()));
	                 }
	             }
	         }
		} catch (MPXJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	public static void main(String[] args) {
		getFileUploadModel();
	}
	/*
	 * 查找id=id的所有子级
	 * 
	 * */
	public static List<Long> getList(List<ContractLofting> clist){
		List<ContractLofting> clist2 = new ArrayList<ContractLofting>();
		for(int i=0;i<plist.size();i++){
			for(int j=0;j<clist.size();j++){
				if(clist.get(j).getId().equals(plist.get(i).getPid())){
					clist2.add(plist.get(i));
					idList.add(plist.get(i).getId());
				}
			}
		}
		if(idList.isEmpty() || clist2.isEmpty()){
			return idList;
		}
		return getList(clist2);
		
	}
	public List<ContractLofting> getPlist() {
		return plist;
	}
	public void setPlist(List<ContractLofting> plist) {
		TestUtil.plist = plist;
	}

}
