package com.my.spring.utils;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.my.spring.model.MechanicPricePojos;
import com.my.spring.utils.ExportExcelOfMenchainc;
public class MainOut {
	public static void main(String args[]){
		//模拟部分数据
		List<MechanicPricePojos> valueList = new ArrayList<MechanicPricePojos>();
		for(int i=0;i<10;i++){
			MechanicPricePojos v = new MechanicPricePojos();
			v.setUserName("人员"+i);
			v.setWorkName("工种"+i);
			v.setHourNum(i);
			v.setSalaryNum((float)(i*20));
			v.setDaySalary(i*10);
			v.setDayNum((float)(i+5));
			valueList.add(v);
		}
        try  
        {  
            FileOutputStream fout = new FileOutputStream("E:/students.xls");
            ExportExcelOfMenchainc aa = new ExportExcelOfMenchainc();
            aa.getValue(valueList, fout,"歌林小镇",8);
            fout.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
 
	}

}
