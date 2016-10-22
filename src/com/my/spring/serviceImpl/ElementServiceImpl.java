package com.my.spring.serviceImpl;

import com.my.spring.DAO.BuildingDao;
import com.my.spring.DAO.ElementDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.ProjectExamDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.BuildingEntity;
import com.my.spring.model.Customer;
import com.my.spring.model.Element;
import com.my.spring.model.ProjectEntity;
import com.my.spring.model.ProjectExam;
import com.my.spring.service.ElementService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.ElementType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("elementService")
public class ElementServiceImpl implements ElementService {
    @Autowired
    ElementDao elementDao;
    @Autowired
    ProjectExamDao projectExamDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    BuildingDao buildingDao;
    private int count=0;
    
    @Override
    public DataWrapper<Void> addElement(Element element) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!elementDao.addElement(element)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteElement(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!elementDao.deleteElement(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateElement(Element element) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!elementDao.updateElement(element)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<Element>> getElementList() {
        return elementDao.getElementList();
    }

    @Override
	public boolean batchImport(String name, MultipartFile file) {
		boolean b = false;
        //创建处理EXCEL
        ReadExcel readExcel=new ReadExcel();
        //解析excel，获取客户信息集合。
        List<Element> elementList = readExcel.getExcelInfo(name ,file);

        if(elementList != null){
            b = true;
        }
        
        //迭代添加客户信息（注：实际上这里也可以直接将customerList集合作为参数，在Mybatis的相应映射文件中使用foreach标签进行批量添加。）
        for(Element element:elementList){
        	elementDao.addElement(element);
        	
            //工程量实时更新，当有构件的表导入的时候，更新工程项目所对应的工程量数据
        	ProjectExam projectExam=new ProjectExam();
        	ElementType elementType=new ElementType();
        	Long projectid;
        	Long buildingid;
        	Long housenum;
        	String value;
        	String familyandtype;
        	String systemtype;
        	String devicetype;
        	String size;
        	String material;
        	String layernum;
        	String unitnum;
        	int projectNum;
        	int buildingNum;
        	int tempA=0;//强电桥架-电缆桥架配件（单位：个）
        	int tempB=0;//弱电桥架-电缆桥架配件（单位：个）
        	int tempC=0;//消防桥架-电缆桥架配件（单位：个）
        	float temp1=0;//强电桥架（单位：长度）
        	float temp2=0;//弱电桥架（单位：长度）
        	float temp3=0;//消防桥架（单位：长度）
        	int temp4=0;//电气设备(单位：)
        	int temp5;//建筑内部给水系统
        	int temp6;//建筑内部排水系统
        	int temp7;//建筑雨水排水系统
        	int temp8;//建筑内部热水供应系统
        	int temp9;//空调送风系统
        	int temp10;//空调排风系统
        	int temp11;//空调回风系统
        	int temp12;//空调新风系统
        	int temp13;//空调供/回水系统
        	int temp14;//空调制冷剂系统
        	int temp15;//空调冷凝水系统
        	int temp16;//送风工程
        	int temp17;//排风工程
        	int temp18;//加压送风系统
        	int temp19;//排烟兼排风系统
        	int temp20;//送风兼排烟补风系统
        	int temp21;//消防栓给水系统
        	int temp22;//湿式自动喷水灭火系统
        	List<Element> elementlist=new ArrayList<Element>();
        	List<ProjectEntity> ret = new ArrayList<ProjectEntity>();
        	ret=projectDao.getProjectList().getData();
        	projectNum=ret.size();///项目的总数
        	List<BuildingEntity> retb = new ArrayList<BuildingEntity>();
        	retb=buildingDao.getBuildingList().getData();
        	buildingNum=retb.size();
        	//int 
        	for(int i=0;i<projectNum;i++){
        		String project_number=ret.get(i).getProject_number();
        		elementlist=elementDao.getElementByLocation(project_number);
        		for(int j=0;j<elementlist.size();j++){
        			
        			if(elementlist.get(j).getService_type().equals(elementType.ArcWater1))
        			{
        				if(elementlist.get(j).getName().equals("电缆桥架配件"))
        					tempA++;
        				else
        					temp1=temp1+elementlist.get(j).getLength();
        			}else if(elementlist.get(j).getService_type().equals(elementType.ArcWater2))
        			{
        				if(elementlist.get(j).getName().equals("电缆桥架配件"))
        					tempB++;
        				else
        					temp2=temp2+elementlist.get(j).getLength();
    					
    				}else if(elementlist.get(j).getService_type().equals(elementType.ArcWater3)){
    					if(elementlist.get(j).getName().equals("电缆桥架配件"))
        					tempC++;
        				else
        					temp3=temp3+elementlist.get(j).getLength();
    				}else if(elementlist.get(j).getService_type().equals(elementType.ArcWater4)){
        				temp4++;
    				}
    					
        		}
        		
        	}
        	
        	
        }
        return b;
	}
}
