package com.my.spring.utils;  
  
import java.util.ArrayList;  
import java.util.Comparator;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Set;

import com.google.gson.JsonArray;

import net.sf.json.JSONArray;

import java.util.Collections;  
  
/** 
 * 多叉树类 
*/  
public class MaterialPlanNodeUtil {  
 
 public String getJasonStringOfMaterialPlan(List dataList) {  
	  // 读取层次数据结果集列表   
	  //List dataList = VirtualDataGenerator.getVirtualResult();    
	    
	  // 节点列表（散列表，用于临时存储节点对象）  
	  HashMap nodeList = new HashMap();  
	  // 根节点  
	  NodeMaterialPlan root = null;  
	  List<NodeMaterialPlan> pcList = new ArrayList<NodeMaterialPlan>(); 
	  // 根据结果集构造节点列表（存入散列表）  
	  for (Iterator it = dataList.iterator(); it.hasNext();) {  
		   Map dataRecord = (Map) it.next();  
		   NodeMaterialPlan node = new NodeMaterialPlan();  
		   node.id = (String) dataRecord.get("id");  
		   node.name = (String) dataRecord.get("name");
		   node.model = (String) dataRecord.get("model");
		   node.pid = (String) dataRecord.get("pid");  
		   node.standard = (String) dataRecord.get("standard");
		   node.unit = (String) dataRecord.get("unit"); 
		   node.num = (String) dataRecord.get("num"); 
		   node.getTime = (String) dataRecord.get("getTime"); 
		   node.outPlace = (String) dataRecord.get("outPlace"); 
		   node.usePlace = (String) dataRecord.get("usePlace"); 
		   nodeList.put(node.id, node);  
	  }  
	  // 构造无序的多叉树  
	  Set entrySet = nodeList.entrySet();  
	  for(Iterator it = entrySet.iterator(); it.hasNext();) {  
		  NodeMaterialPlan node = (NodeMaterialPlan) ((Map.Entry) it.next()).getValue();  
		   if (node.pid == null || node.pid.equals("")) {  
			    // root = node; 
			    pcList.add(node);
		   } else {  
		    ((NodeMaterialPlan) nodeList.get(node.pid)).addChild(node);  
		   }  
	  }  
	  // 输出无序的树形菜单的JSON字符串  
	  //System.out.println(pcList);
	  String results = pcList.toString();
	  
	    return results;
	    
	 }  
     
  
  
  

/** 
* 节点类 
*/  
class NodeMaterialPlan {  

 public String id;  
 public String name;  
 public String model;  
 public String standard;
 public String unit;
 public String num;
 public String getTime;
 public String outPlace;
 public String usePlace;
 public String pid;
 /** 
  * 孩子节点列表 
  */  
 private MaterialPlanChildren children = new MaterialPlanChildren();  
   
 // 先序遍历，拼接JSON字符串  
 public String toString() {    
  String result = "{"  
   + "\"id\" : \"" + id + "\""+", \"name\" : \"" + name + "\""
   + ", \"model\" : \"" + model + "\"" + ", \"standard\" : \"" 
   + standard + "\"" +", \"unit\" : \"" + unit + "\""
   +", \"num\" : \"" + num + "\""+", \"getTime\" : \"" + getTime + "\""
   +", \"outPlace\" : \"" + outPlace + "\""+", \"usePlace\" : \"" + usePlace + "\"";  
    
  if (children != null && children.getSize() != 0) {  
   result += ", \"children\" : " + children.toString();  
  } else {  
   result += ",\"leaf\" : \"true\"";  
  }  
  
  System.out.println(result);
      
  return result + "}";  
 }
   
 // 兄弟节点横向排序  
 public void sortChildren() {  
  if (children != null && children.getSize() != 0) {  
   children.sortChildren();  
  }  
 }  
   
 // 添加孩子节点  
 public void addChild(NodeMaterialPlan node) {  
  this.children.addChild(node);  
 }  
}  
  
/** 
* 孩子列表类 
*/  
class MaterialPlanChildren {  
 private List list = new ArrayList();  
   
 public int getSize() {  
  return list.size();  
 }  
   
 public void addChild(NodeMaterialPlan node) {  
  list.add(node);  
 }  
   
 // 拼接孩子节点的JSON字符串  
 public String toString() {  
  String result = "[";    
  for (Iterator it = list.iterator(); it.hasNext();) {  
   result += ((NodeMaterialPlan) it.next()).toString();  
   result += ",";  
  }  
  result = result.substring(0, result.length() - 1);  
  result += "]";  
  return result;  
 }  
   
 // 孩子节点排序  
 public void sortChildren() {  
  // 对本层节点进行排序  
  // 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器  
  Collections.sort(list, new NodeIDComparator());  
  // 对每个节点的下一层节点进行排序  
  for (Iterator it = list.iterator(); it.hasNext();) {  
   ((NodeMaterialPlan) it.next()).sortChildren();  
  }  
 }  
}  
  
/** 
 * 节点比较器 
 */  
class NodeIDComparator implements Comparator {  
 // 按照节点编号比较  
 public int compare(Object o1, Object o2) {  
  int j1 = Integer.parseInt(((NodeMaterialPlan)o1).id);  
     int j2 = Integer.parseInt(((NodeMaterialPlan)o2).id);  
     return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));  
 }   
}  

} 