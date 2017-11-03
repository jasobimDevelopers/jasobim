package com.my.spring.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.my.spring.model.AdvancedOrderPojo;
import com.my.spring.model.ConstructionTask;
import com.my.spring.model.ConstructionTaskPojo;

import freemarker.cache.FileTemplateLoader;  
import freemarker.cache.TemplateLoader;  
import freemarker.template.Configuration;  
import freemarker.template.Template;  

public class DataExportWordTest {
	public String exportConstructionToWord(ConstructionTaskPojo constructionTask){
		Map<String, Object> cont = new HashMap<String, Object>();// 存储数据  
		if(constructionTask.getCompanyName()!=null){
			cont.put("companyName", constructionTask.getCompanyName());  
		}
        if(constructionTask.getCreateDate()!=null){
            Calendar c = Calendar.getInstance();
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        	try {
				c.setTime(sdf.parse(constructionTask.getCreateDate()));
				cont.put("createYear", c.get(Calendar.YEAR));  
	            cont.put("createMonth", c.get(Calendar.MONTH)+1);  
	            cont.put("createDay", c.get(Calendar.DATE));  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        if(constructionTask.getReceiveUserName()!=null){
        	cont.put("receiveUserName", constructionTask.getReceiveUserName());  
        }
        if(constructionTask.getTeamName()!=null){
        	cont.put("teamName", constructionTask.getTeamName());
        }
        if(constructionTask.getTaskContent()!=null){
        	  cont.put("taskContent", constructionTask.getTaskContent());  
        }
        if(constructionTask.getFinishedDate()!=null){
        	 cont.put("finishedDate", constructionTask.getFinishedDate());  
        }
        if(constructionTask.getRewards()!=null){
        	cont.put("rewards", constructionTask.getRewards());  
        }  
        if(constructionTask.getDetailContent()!=null){
        	cont.put("detailContent", constructionTask.getDetailContent());
        }
        if(constructionTask.getApprovalDateList()!=null){
        	cont.put("checkDate",constructionTask.getApprovalDateList()[0]);
        	if(constructionTask.getApprovalDateList().length>0){
        		cont.put("passDate",constructionTask.getApprovalDateList()[1]);
        	}
        }
        if(constructionTask.getCreateUserName()!=null){
        	cont.put("createUserName",constructionTask.getCreateUserName());
        }
        String[] nametest={"teamLeader","quanlityer","anquanUser","constructor","quantityer","leaderName"};
        String[] notetest={"teamLeaderNote","quanlityerNote","anquanNote","constructorNote","quantity","leaderNote"};
        if(constructionTask.getApprovalPeopleNameList()!=null){
        	for(int i=0;i<constructionTask.getApprovalPeopleNameList().length;i++){
        		cont.put(nametest[i],constructionTask.getApprovalPeopleNameList()[i]);
        	}
        }
        if(constructionTask.getApprovalPeopleNoteList()!=null){
        	for(int i=0;i<constructionTask.getApprovalPeopleNoteList().length;i++){
        		cont.put(notetest[i],constructionTask.getApprovalPeopleNoteList()[i]);
        	}
        }
		try {  
            //模板的路径  
            //File fir = new File("W:/test/wswhr/");  
            File fir = new File("C:/Users/Han/Desktop/");
              
            //生成文件的路径及文件名。  
            File outFile = new File("C:/Users/Han/Desktop/施工任务单.doc");  
  
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));  
  
            // 使用FileTemplateLoader  
            //指定模板路径  
            TemplateLoader templateLoader = null;  
            templateLoader = new FileTemplateLoader(fir);  
            String tempname = "劳务公司班组施工任务单模板.xml";  
              
            Configuration cfg = new Configuration();  
            cfg.setTemplateLoader(templateLoader);  
            Template t = cfg.getTemplate(tempname, "UTF-8");  
              
            t.process(cont, out);  
            out.flush();  
            out.close();  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		return "constructionTask/施工任务单.doc";
	}
	/////////预付单
	public String exportAdvancedOrderToWord( AdvancedOrderPojo aop){
		Map<String, Object> cont = new HashMap<String, Object>();// 存储数据  
		if(aop.getProjectName()!=null){
			cont.put("projectName", aop.getProjectName());  
		}
        if(aop.getCreateDate()!=null){
	        cont.put("month", aop.getMonth());  
        }
        if(aop.getLeader()!=null){
        	cont.put("leader", aop.getLeader());  
        }
       
        if(aop.getConstructPart()!=null){
        	  cont.put("constructionPart", aop.getConstructPart());  
        }
        if(aop.getQuantityDes()!=null){
        	 cont.put("quantityDes", aop.getQuantityDes());  
        }
       
        String[] nametest={"constructor","quanlityer","anquanUser","quantityer"};
        String[] notetest={"constructorNote","quanlityerNote","anquanUserNote","quantityerNote"};
        if(aop.getApprovalPeopleName()!=null){
        	for(int i=0;i<aop.getApprovalPeopleName().length;i++){
        		cont.put(nametest[i],aop.getApprovalPeopleName()[i]);
        	}
        }
        if(aop.getApprovalPeopleNote()!=null){
        	for(int i=0;i<aop.getApprovalPeopleNote().length;i++){
        		cont.put(notetest[i],aop.getApprovalPeopleNote()[i]);
        	}
        }
		try {  
            //模板的路径  
            //File fir = new File("W:/test/wswhr/");  
            //File fir = new File("E:/JasoBim/BimAppDocument/tomcat81/webapps/ROOT/advancedOrder/");
            File fir = new File("C:/Users/Han/Desktop/");  
            //生成文件的路径及文件名。  
            ///File outFile = new File("E:/JasoBim/BimAppDocument/tomcat81/webapps/ROOT/advancedOrder/预付单.doc");  
            File outFile = new File("C:/Users/Han/Desktop/预付单.doc");  
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));  
  
            // 使用FileTemplateLoader  
            //指定模板路径  
            TemplateLoader templateLoader = null;  
            templateLoader = new FileTemplateLoader(fir);  
            String tempname = "预付单模板.xml";  
              
            Configuration cfg = new Configuration();  
            cfg.setTemplateLoader(templateLoader);  
            Template t = cfg.getTemplate(tempname, "UTF-8");  
              
            t.process(cont, out);  
            out.flush();  
            out.close();  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		return "advancedOrder/预付单.doc";
	}
	public static void main(String[] args) {
		
	}
	
}
