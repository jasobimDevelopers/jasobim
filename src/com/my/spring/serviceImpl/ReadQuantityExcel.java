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
import com.my.spring.model.Quantity;

public class ReadQuantityExcel {
    //总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    ///////全局变量

    //构造方法
    public ReadQuantityExcel(){}
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
  public List<Quantity> getExcelInfo(String fileName,MultipartFile Mfile){
      
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
       List<Quantity> elementList=new ArrayList<Quantity>();
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
          elementList = getExcelInfo(is, isExcel2003); 
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
  public List<Quantity> getExcelInfo(MultipartFile Mfile){
      
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
       List<Quantity> elementList=new ArrayList<Quantity>();
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
  public  List<Quantity> getExcelInfo(InputStream is,boolean isExcel2003){
       List<Quantity> elementList=null;
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
           elementList=readExcelValue(wb);
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
  private List<Quantity> readExcelValue(Workbook wb){ 
      //得到第一个shell  
       Sheet sheet=wb.getSheetAt(0);
       
      //得到Excel的行数
       this.totalRows=sheet.getPhysicalNumberOfRows();
       
      //得到Excel的列数(前提是有行数)
       if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
       }
       
       List<Quantity> quantityList=new ArrayList<Quantity>();
         
      //循环Excel行数,从第二行开始。标题不入库
       for(int r=1;r<totalRows;r++){
           Row row = sheet.getRow(r);
           if (row == null) continue;
           Quantity quantity=new Quantity(); 
           //循环Excel的列
           for(int c = 0; c <this.totalCells; c++){    
               Cell cell = row.getCell(c);
               if (null != cell){
                   if(c==0){
                	   String temp=cell.getStringCellValue();
                	   quantity.setName(temp);//工程量的名称
                   }else if(c==1){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   String temp=cell.getStringCellValue();
                	   if(temp!=null && !(temp.equals(""))){
	                	   int professionType=Integer.valueOf(cell.getStringCellValue());
	                	   quantity.setProfessionType(professionType);
                	   }
                	   
                   }else if(c==2){
                	   Double value=cell.getNumericCellValue();
                	   quantity.setValue(value);
                	   
                   }else if(c==3){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   quantity.setUnit(cell.getStringCellValue());
                   }else if(c==4){
                	   Long projectId=Long.valueOf(cell.getStringCellValue());
                	   quantity.setProjectId(projectId);
                   }else if(c==5){
                	   int buildingNum=Integer.valueOf(cell.getStringCellValue());
                	   quantity.setBuildingNum(buildingNum);
                   }else if(c==6){
                	   int floorNum=Integer.valueOf(cell.getStringCellValue());
                	   quantity.setFloorNum(floorNum);
                   }else if(c==7){
                	   int unitNum=Integer.valueOf(cell.getStringCellValue());
                	   quantity.setUnitNum(unitNum);
                   }else if(c==8){
                	   int householdNum=Integer.valueOf(cell.getStringCellValue());
                	   quantity.setHouseholdNum(householdNum);
                   }else if(c==9){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   quantity.setFamilyAndType(cell.getStringCellValue());
                   }else if(c==10){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   quantity.setSystemType(cell.getStringCellValue());
                   }else if(c==11){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   quantity.setServiceType(cell.getStringCellValue());
                   }else if(c==12){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   quantity.setSize(cell.getStringCellValue());
                   }else if(c==13){
                	   cell.setCellType(Cell.CELL_TYPE_STRING);
                	   quantity.setMaterial(cell.getStringCellValue());
                   }
               }
           }

           try{
        	   quantityList.add(quantity);
           }catch(Exception e){
        	   e.printStackTrace();
           }
       }
       return quantityList;
  }

}