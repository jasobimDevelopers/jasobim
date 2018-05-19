package com.my.spring.utils;  
  
import java.util.ArrayList;  
import java.util.Comparator;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Set;  
import java.util.Collections;  
  
/** 
 * 多叉树类 
*/  
public class NodeUtil {  
 public String getJasonString(List dataList) {  
  // 读取层次数据结果集列表   
  //List dataList = VirtualDataGenerator.getVirtualResult();    
    
  // 节点列表（散列表，用于临时存储节点对象）  
  HashMap nodeList = new HashMap();  
  // 根节点  
  Node root = null;  
  List<Node> pcList = new ArrayList<Node>(); 
  // 根据结果集构造节点列表（存入散列表）  
  for (Iterator it = dataList.iterator(); it.hasNext();) {  
	   Map dataRecord = (Map) it.next();  
	   Node node = new Node();  
	   node.id = (String) dataRecord.get("id");  
	   node.name = (String) dataRecord.get("name");  
	   node.parentId = (String) dataRecord.get("parentId");  
	   node.uploadDate = (String) dataRecord.get("uploadDate");
	   node.remark= (String) dataRecord.get("remark");
	   node.fileType = (String) dataRecord.get("fileType");
	   node.url = (String) dataRecord.get("url");
	   node.userName = (String) dataRecord.get("userName"); 
	   nodeList.put(node.id, node);  
  }  
  // 构造无序的多叉树  
  Set entrySet = nodeList.entrySet();  
  for (Iterator it = entrySet.iterator(); it.hasNext();) {  
   Node node = (Node) ((Map.Entry) it.next()).getValue();  
   if (node.parentId == null || node.parentId.equals("")) {  
    // root = node; 
    pcList.add(node);
   } else {  
	   if(((Node)nodeList.get(node.parentId))!=null){
		   ((Node) nodeList.get(node.parentId)).addChild(node);  
	   }else{
		   pcList.add(node);
	   }
   
   }  
  }  
  // 输出无序的树形菜单的JSON字符串  
  System.out.println(pcList);     
  return pcList.toString();
  // 对多叉树进行横向排序  
  //root.sortChildren();  
  // 输出有序的树形菜单的JSON字符串  
 // System.out.println(root.toString());   
    
    
 }  
 
     
 
  
  
/** 
* 节点类 
*/  
class Node {  
 /** 
  * 节点编号 
  */  
 public String id;  
 /** 
  * 节点内容 
  */  
 public String name;  
 /** 
  * 父节点编号 
  */  
 public String parentId;  
 public String userName;
 public String remark;
 public String fileType;
 public String url;
 public String uploadDate;
 /** 
  * 孩子节点列表 
  */  
 private Children children = new Children();  
   
 // 先序遍历，拼接JSON字符串  
 public String toString() {    
  String result = "{"  
   + "id : '" + id + "'"  
   + ", name : '" + name + "'"+ ", fileType : '" + fileType + "'"+ ", url : '" + url + "'"+ ", userName : '" 
   + userName + "'"+", remark : '" + remark + "'"+ ", uploadDate : '" + uploadDate + "'";  
    
  if (children != null && children.getSize() != 0) {  
   result += ", children : " + children.toString();  
  } else {  
   result += ", leaf : true";  
  }  
      
  return result + "}";  
 }  
   
 // 兄弟节点横向排序  
 public void sortChildren() {  
  if (children != null && children.getSize() != 0) {  
   children.sortChildren();  
  }  
 }  
   
 // 添加孩子节点  
 public void addChild(Node node) {  
  this.children.addChild(node);  
 }  
}  

  
/** 
* 孩子列表类 
*/  
class Children {  
 private List list = new ArrayList();  
   
 public int getSize() {  
  return list.size();  
 }  
   
 public void addChild(Node node) {  
  list.add(node);  
 }  
   
 // 拼接孩子节点的JSON字符串  
 public String toString() {  
  String result = "[";    
  for (Iterator it = list.iterator(); it.hasNext();) {  
   result += ((Node) it.next()).toString();  
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
   ((Node) it.next()).sortChildren();  
  }  
 }  
}  
  
/** 
 * 节点比较器 
 */  
class NodeIDComparator implements Comparator {  
 // 按照节点编号比较  
 public int compare(Object o1, Object o2) {  
  int j1 = Integer.parseInt(((Node)o1).id);  
     int j2 = Integer.parseInt(((Node)o2).id);  
     return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));  
 }   
}  
  
/** 
 * 构造虚拟的层次数据 
 */  

} 