package com.my.spring.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.my.spring.model.AdvancedOrderPojo;
import com.my.spring.model.ConstructionLogPojo;
import com.my.spring.model.ConstructionTaskNewPojo;
import com.my.spring.model.ProductionRecords;

import freemarker.cache.FileTemplateLoader;  
import freemarker.cache.TemplateLoader;  
import freemarker.template.Configuration;  
import freemarker.template.Template;  

public class DataExportWordTest {
	public String exportConstructionToWord(ConstructionTaskNewPojo constructionTask){
		Map<String, Object> cont = new HashMap<String, Object>();// 存储数据  
		//private blob ss;
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
       
        String[] nametest={"teamLeader","quanlityer","anquanUser","constructor","quantityer","leaderName"};
        String[] notetest={"teamLeaderNote","quanlityerNote","anquanNote","constructorNote","quantity","leaderNote"};
        /*if(constructionTask.getApprovalPeopleNameList()!=null){
        	for(int i=0;i<constructionTask.getApprovalPeopleNameList().length;i++){
        		cont.put(nametest[i],constructionTask.getApprovalPeopleNameList()[i]);
        	}
        }
        if(constructionTask.getApprovalPeopleNoteList()!=null){
        	for(int i=0;i<constructionTask.getApprovalPeopleNoteList().length;i++){
        		cont.put(notetest[i],constructionTask.getApprovalPeopleNoteList()[i]);
        	}
        }*/
		try {  
            //模板的路径  
            //File fir = new File("W:/test/wswhr/");  
			
            File fir = new File("D:/jasobim/tomcat_3001/webapps/ROOT/constructionTask");
              
            //生成文件的路径及文件名。  
            File outFile = new File("D:/jasobim/tomcat_3001/webapps/ROOT/constructionTask/施工任务单.doc");  
  
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
	/////////施工日志
	public static String exportConstructionLogToWord(ConstructionLogPojo constructionLog,String leader){
		Map<String, Object> cont = new HashMap<String, Object>();// 存储数据  
		//private blob ss;
        if(constructionLog.getConstructionDate()!=null){
            Calendar c = Calendar.getInstance();
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        	try {
				c.setTime(sdf.parse(constructionLog.getConstructionDate()));
				cont.put("year", c.get(Calendar.YEAR));  
	            cont.put("month", c.get(Calendar.MONTH)+1);  
	            cont.put("day", c.get(Calendar.DATE));  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        cont.put("dayWeather", constructionLog.getDayWeather());
        cont.put("dayWindForce", constructionLog.getDayWindForce());
        cont.put("dayTemperature", constructionLog.getDayTemperature());
        cont.put("nightWeather", constructionLog.getNightWeather());
        cont.put("nightWindForce", constructionLog.getNightWindForce());
        cont.put("nightTemperature", constructionLog.getNightTemperature());
        String productionLogList="";
        String list="                                                                       ";
        if(!constructionLog.getProductionRecordsList().isEmpty()){
        	 for(int i=0;i<constructionLog.getProductionRecordsList().size();i++){
        		 productionLogList=productionLogList+constructionLog.getProductionRecordsList().get(i).getUserNameList();
        		 productionLogList=productionLogList+","+constructionLog.getProductionRecordsList().get(i).getConstructPartName();
        		 productionLogList=productionLogList+","+constructionLog.getProductionRecordsList().get(i).getConstructContent();
        		 productionLogList=productionLogList+";";
        	 }
        	 
        }
        cont.put("productionLogList", productionLogList);
        String questionLogList = "";
        if(constructionLog.getTechnologyDiscloseContent()!=null){
        	questionLogList=questionLogList+constructionLog.getTechnologyDiscloseContent()+";";
        }
        if(constructionLog.getQualityDiscloseContent()!=null){
        	questionLogList=questionLogList+constructionLog.getQualityDiscloseContent()+";";
        }
        if(constructionLog.getSafetyDiscloseContent()!=null){
        	questionLogList=questionLogList+constructionLog.getSafetyDiscloseContent()+";";
        }
        cont.put("questionLogList", questionLogList);
        String materialLogList = "";
        if(constructionLog.getMaterialDiscloseContent()!=null){
        	materialLogList=materialLogList+constructionLog.getMaterialDiscloseContent();
        }
        cont.put("materialLogList", materialLogList);
        cont.put("leader", leader);
		try {  
            //模板的路径  
            //File fir = new File("W:/test/wswhr/");  
			
            File fir = new File("D:/jasobim/tomcat_8080/webapps/ROOT/files/constructionLog/");
              
            //生成文件的路径及文件名。  
            File outFile = new File("D:/jasobim/tomcat_8080/webapps/ROOT/files/constructionLog/施工日志.doc");  
  
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));  
  
            // 使用FileTemplateLoader  
            //指定模板路径  
            TemplateLoader templateLoader = null;  
            templateLoader = new FileTemplateLoader(fir);  
            String tempname = "施工日志.xml";  
              
            Configuration cfg = new Configuration();  
            cfg.setTemplateLoader(templateLoader);  
            Template t = cfg.getTemplate(tempname, "UTF-8");  
              
            t.process(cont, out);  
            out.flush();  
            out.close();  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		return "files/constructionLog/施工日志.doc";
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
            File fir = new File("D:/jasobim/tomcat_3001/webapps/ROOT/advancedOrder/");
            //File fir = new File("C:/Users/Han/Desktop/");  
            //生成文件的路径及文件名。  
            File outFile = new File("D:/jasobim/tomcat_3001/webapps/ROOT/advancedOrder/预付单.doc");  
            //File outFile = new File("C:/Users/Han/Desktop/预付单.doc");  
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
		ConstructionLogPojo log = new ConstructionLogPojo();
		log.setConstructionDate("2018-09-08");
		log.setDayTemperature("24°");
		log.setDayWeather("多云");
		log.setDayWindForce("3级");
		log.setNightTemperature("18°");
		log.setNightWeather("多云");
		log.setNightWindForce("4级");
		log.setQualityDiscloseContent("质量问题：钢架生锈");
		log.setSafetyDiscloseContent("安全问题：脚手架松动");
		log.setTechnologyDiscloseContent("技术问题：图纸问题");
		log.setMaterialDiscloseContent("材料进出场记录：进入200米电缆桥架");
		List<ProductionRecords> records = new ArrayList<ProductionRecords>();
		for(int i=0;i<3;i++){
			ProductionRecords record = new ProductionRecords();
			record.setConstructContent("施工内容测试"+i);
			record.setConstructPartName("施工部位测试"+i);
			record.setUserNameList("作业人员测试"+i);
			records.add(record);
		}
		log.setProductionRecordsList(records);
		exportConstructionLogToWord(log,"黄卫国");
		
	}
	
}
