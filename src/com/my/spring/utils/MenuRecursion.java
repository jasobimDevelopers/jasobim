package com.my.spring.utils;  
  
import java.util.ArrayList;  
import java.util.List;

import com.my.spring.model.Folder;
import com.my.spring.model.MaterialPlan;  
  

public class MenuRecursion {  
	 //子节点  
	public static List<String> childPath = new ArrayList<String>();
    static  List<Folder> childFolder=new ArrayList<Folder>();  
    static  List<MaterialPlan> childMaterialPlan=new ArrayList<MaterialPlan>();  
    static  Folder parrentFolder=new Folder();  
    static String childPaths="";
    static String parrentNames;
    public MenuRecursion(String parrentName){
    	this.childPaths="";
    	this.childFolder.clear();
    	this.parrentNames=parrentName;
    }
    public MenuRecursion(){
    	this.childFolder.clear();
    }
    /** 
     * 获取某个父节点下面的所有子节点 
     * @param menuList 
     * @param pid 
     * @return 
     */  
    public static List<MaterialPlan> treeChildListOfMaterialPlan( List<MaterialPlan> menuList, Long pid){  
        for(MaterialPlan mu: menuList){  
            //遍历出父id等于参数的id，add进子节点集合  
            if(mu.getPid()==pid){  
                //递归遍历下一级  
            	treeChildListOfMaterialPlan(menuList,mu.getId());  
            	childMaterialPlan.add(mu);  
            }  
        }  
    return childMaterialPlan;  
    }  
    /** 
     * 获取某个父节点下面的所有子节点 
     * @param menuList 
     * @param pid 
     * @return 
     */  
    public static List<Folder> treeChildList( List<Folder> menuList, Long pid){  
        for(Folder mu: menuList){  
            //遍历出父id等于参数的id，add进子节点集合  
            if(mu.getParrentId().equals(pid)){  
                //递归遍历下一级  
            	treeChildList(menuList,mu.getId());  
            	childFolder.add(mu);  
            }  
        }  
    return childFolder;  
    }  
    
    /** 
     * 获取某个父节点下面的所有子节点 ,并返回相应的路径
     * @param menuList 
     * @param pid 
     * @return 
     */  
    public static List<Folder> treeChildList( List<Folder> menuList, Long pid,String parrentName){  
    	if(childPaths.equals("")){
    		childPaths=parrentName;
    	}
        for(int i=0;i<menuList.size();i++){  
            //遍历出父id等于参数的id，add进子节点集合  
            if(menuList.get(i).getParrentId().equals(pid)){  
            	if(menuList.get(i).getFileType()==1){
            		childPaths=childPaths+"/"+menuList.get(i).getName()+"."+menuList.get(i).getRemark();
            	}else{
            		if(childPaths.equals("")){
            			childPaths=parrentNames;
            		}
            		childPaths=childPaths+"/"+menuList.get(i).getName();
            	}
            	menuList.get(i).setRemark(childPaths);
            	treeChildList(menuList,menuList.get(i).getId(),menuList.get(i).getName()); 
                //递归遍历下一级  
            	childFolder.add(menuList.get(i)); 
        		String[] test = childPaths.split("/");
        		String tests="";
        		for(int p=0;p<test.length-1;p++){
        			if(tests.equals("")){
        				tests=test[p];
        			}else{
        				tests=tests+"/"+test[p];
        			}
        			
        		}
        		childPaths=tests;
            }  
        }  
    return childFolder;  
    }  
    /** 
     * 获取某个子节点上面的顶层父节点 
     * @param menuList 
     * @param pid 
     * @return 
     */  
    public static Folder treeParrentList( List<Folder> menuList, Long pid){  
        for(Folder mu: menuList){  
    		 if(mu.getId()==pid){  
    			//遍历出父id等于参数的pid
    			 if(mu.getParrentId().equals(0)){
             		parrentFolder=mu;
             	}else{
             		 //递归遍历上一级  
                 	treeParrentList(menuList,mu.getParrentId());  
             	}
             }  
        }  
        return parrentFolder;  
    }  
}