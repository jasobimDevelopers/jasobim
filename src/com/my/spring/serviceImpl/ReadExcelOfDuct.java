package com.my.spring.serviceImpl;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.my.spring.utils.WDWUtil;
import com.my.spring.model.Building;
import com.my.spring.model.Duct;
import com.my.spring.model.Item;
import com.my.spring.model.MinItem;

public class ReadExcelOfDuct {
    //总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    ///////全局变量

    //构造方法
    public ReadExcelOfDuct(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;} 
    //获取总列数
    public int getTotalCells() {  return totalCells;} 
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }  
    
  /**
   * 验证EXCEL文件
   * @param filePath
   * @return
   */
  public boolean validateExcel(String filePath){
        if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){  
            errorMsg = "文件名不是excel格式";  
            return false;  
        }  
        return true;
  }
    
  /**
   * 读EXCEL文件，获取客户信息集合
   * @param fielName
   * @return
   */
  public List<Duct> getExcelInfo(String fileName,MultipartFile Mfile,Long projectId){
      
      //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
       CommonsMultipartFile cf= (CommonsMultipartFile)Mfile; //获取本地存储路径
       File file = new  File("D:\\fileupload");
       //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
       if (!file.exists()) file.mkdirs();
       //新建一个文件
       File file1 = new File(file +"/"+ new Date().getTime() + ".xls"); 
       //将上传的文件写入新建的文件中
       try {
           cf.getFileItem().write(file1); 
       } catch (Exception e) {
           e.printStackTrace();
       }
       
       //初始化客户信息的集合    
       List<Duct> elementList=new ArrayList<Duct>();
       //初始化输入流
       InputStream is = null;  
       try{
          //验证文件名是否合格
          if(!validateExcel(fileName)){
              return null;
          }
          //根据文件名判断文件是2003版本还是2007版本
          boolean isExcel2003 = true; 
          if(WDWUtil.isExcel2007(fileName)){
              isExcel2003 = false;  
          }
          //根据新建的文件实例化输入流
          is = cf.getInputStream();
          //根据excel里面的内容读取客户信息
          elementList = getExcelInfo(is, isExcel2003,projectId); 
          is.close();
      }catch(Exception e){
          e.printStackTrace();
      } finally{
          if(is !=null)
          {
              try{
                  is.close();
              }catch(IOException e){
                  is = null;    
                  e.printStackTrace();  
              }
          }
      }
      return elementList;
  }
  /**
   * 读EXCEL文件，获取客户信息集合
   * @param fielName
   * @return
   */
  public List<Item> getExcelInfo(MultipartFile Mfile){
      
      //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
       CommonsMultipartFile cf= (CommonsMultipartFile)Mfile; //获取本地存储路径
       /*
       File file = new  File("D:\\fileupload");
       //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
       if (!file.exists()) file.mkdirs();
       //新建一个文件
       File file1 = new File("D:\\fileupload" + new Date().getTime() + ".xlsx"); 
       //将上传的文件写入新建的文件中
       try {
           cf.getFileItem().write(file1); 
       } catch (Exception e) {
           e.printStackTrace();
       }*/
       
       //初始化客户信息的集合    
       List<Item> elementList=new ArrayList<Item>();
       //初始化输入流
       InputStream is = null;  
       try{
         /* //验证文件名是否合格
          if(!validateExcel(fileName)){
              return null;
          }
          //根据文件名判断文件是2003版本还是2007版本
          boolean isExcel2003 = true; 
          if(WDWUtil.isExcel2007(fileName)){
              isExcel2003 = false;  
          }*/
          //根据新建的文件实例化输入流
          is = cf.getInputStream();
          //根据excel里面的内容读取客户信息
         // elementList = getExcelInfo(is, isExcel2003); 
          is.close();
      }catch(Exception e){
          e.printStackTrace();
      } finally{
          if(is !=null)
          {
              try{
                  is.close();
              }catch(IOException e){
                  is = null;    
                  e.printStackTrace();  
              }
          }
      }
      return elementList;
  }
  /**
   * 根据excel里面的内容读取客户信息
   * @param is 输入流
   * @param isExcel2003 excel是2003还是2007版本
   * @return
   * @throws IOException
   */
  public  List<Duct> getExcelInfo(InputStream is,boolean isExcel2003,Long projectId){
       List<Duct> elementList=null;
       try{
           /** 根据版本选择创建Workbook的方式 */
           Workbook wb = null;
           //当excel是2003时
           if(isExcel2003){
               wb = new HSSFWorkbook(is); 
           }
           else{//当excel是2007时
               wb = new XSSFWorkbook(is); 
           }
           //读取Excel里面客户的信息
           elementList=readExcelValue(wb,projectId);
       }
       catch (IOException e)  {  
           e.printStackTrace();  
       }  
       return elementList;
  }
  /**
   * 读取Excel里面客户的信息
   * @param wb
   * @return
   */
  @SuppressWarnings("unused")
private List<Duct> readExcelValue(Workbook wb,Long projectId){ 
      //得到第一个shell  
       Sheet sheet=wb.getSheetAt(0);
       
      //得到Excel的行数
       this.totalRows=sheet.getPhysicalNumberOfRows();
       
      //得到Excel的列数(前提是有行数)
       if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
       }
       
       List<Duct> elementList=new ArrayList<Duct>();
       Duct item = null;    
      //循环Excel行数,从第二行开始。标题不入库
       for(int r=1;r<totalRows;r++){
           Row row = sheet.getRow(r);
           if (row == null) continue;
           item = new Duct();
           //循环Excel的列
           for(int c = 0; c <this.totalCells; c++){    
               Cell cell = row.getCell(c);
               if (null != cell){
                   if(c==0){
                	   String temp=cell.getStringCellValue();
                	   Long test=Long.valueOf(temp);
                	   //item.setSelfId(test);//模型中的id
                   }else if(c==1){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   String temp=cell.getStringCellValue();
                	   if(temp!=null && !(temp.equals(""))){
	                	   item.setLocation(cell.getStringCellValue());//位置
	                	  
	                	   int buildingid=Integer.valueOf(temp.substring(temp.indexOf("B")+1,temp.indexOf("C")));
	                	   int unitid=Integer.valueOf(temp.substring(temp.indexOf("C")+1,temp.indexOf("D")));
	                	   int floorid=Integer.valueOf(temp.substring(temp.indexOf("D")+1,temp.indexOf("E")));
	                	   int householdid=Integer.valueOf(temp.substring(temp.indexOf("E")+1));
	                	  // item.setBuildingNum(buildingid);
	                	  // item.setFloorNum(floorid);
	                	  // item.setHouseholdNum(householdid);
	                	  // item.setProjectId(projectId);
	                	   //item.setUnitNum(unitid);
                	   }
                	   
                   }else if(c==3){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   String str=cell.getStringCellValue();
                	   if(str==null){
                		   str="";
                	   }
                	   item.setSize(str);//构件的尺寸
                   }else if(c==4){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   String str=cell.getStringCellValue();
                	   if(str==null){
                		   str="";
                	   }
                	   item.setServiceType(str);//构件的设备类型
                  }else if(c==6){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   String str=cell.getStringCellValue();
                	   if(str==null){
                		   str="";
                	   }
                	   //item.setTypeName(str);//构件类型名称
                   }else if(c==7){
                	   //item.setLength(cell.getNumericCellValue());//构件的长度
                   }else if(c==8){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   String str=cell.getStringCellValue();
                	   if(str==null){
                		   str="";
                	   }
                	   item.setFamilyAndType(str);//构件的类型
                   }else if(c==9){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   item.setLevel(cell.getStringCellValue());//构件标高
                   }else if(c==10){
                	   //item.setOffset(cell.getNumericCellValue());//构件偏移量
                   }else if(c==11){
                	   //String area=cell.getStringCellValue();
                	  // item.setArea(cell.getNumericCellValue());
                	   //构件面积
                   }else if(c==12){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   String str=cell.getStringCellValue();
                	   if(str==null){
                		   str="";
                	   }
                	  // item.setMaterial(str);//构件材质
                   }else if(c==13){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	  // item.setSystemType(cell.getStringCellValue());//构件的系统类型
                	   String test=cell.getStringCellValue();
                	   
                	   }
                	   
                   }
               }
           }
           try{
        	   elementList.add(item);
           }catch(Exception e){
        	   e.printStackTrace();
           }
        return elementList;
  }

}