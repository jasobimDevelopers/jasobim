package com.my.spring.serviceImpl;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.my.spring.DAO.ProjectProcessDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserIndexDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.ProjectProcess;
import com.my.spring.model.ProjectProcessPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.ProjectProcessService;
import com.my.spring.service.UserIndexService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;

@Service("projectProcessService")
public class ProjectProcessServiceImpl implements ProjectProcessService  {
	@Autowired
	ProjectProcessDao projectProcessDao;
	@Autowired
	UserIndexService userIndexService;
	@Autowired
	UserIndexDao userIndexDao;
	@Autowired
	UserDao userDao;
	@Autowired
	FileService fileSerivce;
	private static String filePath="fileUploads/projectProcessFiles";
	@Override
	public DataWrapper<Void> deleteProjectProcessById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!projectProcessDao.deleteProjectProcess(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<ProjectProcess> addProjectProcess(String token,ProjectProcess role,String startTime,String endTime) {
		DataWrapper<ProjectProcess> result = new DataWrapper<ProjectProcess>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				try {
					role.setStartDate(Parameters.getSdfs().parse(startTime));
					role.setEndDate(Parameters.getSdfs().parse(endTime));
					role.setImportTime(new Date());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!projectProcessDao.addProjectProcess(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}else{
					result.setData(role);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteProjectProcessByIdList(String token,String[] id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!projectProcessDao.deleteProjectProcessList(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateProjectProcess(String token, ProjectProcess ProjectProcess) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(ProjectProcess!=null){
				ProjectProcess dp = new ProjectProcess();
				dp = projectProcessDao.getById(ProjectProcess.getId());
				if(!projectProcessDao.updateProjectProcess(dp)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> importProjectProcess(String token, MultipartFile file, HttpServletRequest request,
			Long projectId) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(file!=null){
				Files files=fileSerivce.uploadFile(filePath, file, 6, request);
				if(files!=null){
					File filestr = new File("D:/jasobim/tomcat_8080/webapps/ROOT/"+files.getUrl());
					//File filestr = new File("C:/Users/Han/Desktop/processMode (2).mpp");
					if(projectProcessDao.deleteProjectProcessByProjectId(projectId)){
						readMmpFileToDB(filestr,projectId);
					}else{
						readMmpFileToDB(filestr,projectId);
					}
				}
			}
			
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	public void readMmpFileToDB(File file,Long projectId) {//如果读取的是MultipartFile，那么直接使用获取InputStream即可
        //这个是读取文件的组件
        MPPReader mppRead = new MPPReader();
        //注意，如果在这一步出现了读取异常，肯定是版本不兼容，换个版本试试
        ProjectFile pf;
		try {
			pf = mppRead.read(file);
			List<Task> tasks = pf.getChildTasks();
	        //这个可以不用，这个list只是我用来装下所有的数据，如果不需要可以不使用
	        List<ProjectProcess> proList = new LinkedList<>();
	        //这个是用来封装任务的对象，为了便于区别，初始化批次号，然后所有读取的数据都需要加上批次号
	        ProjectProcess pro = new ProjectProcess();
	        //这个方法是一个递归方法
	        getChildrenTask(tasks.get(0), pro ,proList, 0,projectId);
		} catch (MPXJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //从文件中获取的任务对象
    }
	 /**
	 * 这个方法是一个递归
	 * 方法的原理：进行读取父任务，如果下一层任务还是父任务，那么继续调用当前方法，如果到了最后一层，调用另外一个读取底层的方法
	 * @param task
	 * @param project
	 * @param list
	 * @param levelNum
	 */
	public void getChildrenTask(Task task, ProjectProcess project, List<ProjectProcess> list, int levelNum,Long projectId){
	        if(task.getResourceAssignments().size() == 0){//这个判断是进行是否是最后一层任务的判断==0说明是父任务
	            levelNum ++;//层级号需要增加，这个只是博主用来记录该层的层级数
	            List<Task> tasks = task.getChildTasks();//继续获取子任务
	            for (int i = 0; i < tasks.size(); i++) {//该循环是遍历所有的子任务
	                if(tasks.get(i).getResourceAssignments().size() == 0){//说明还是在父任务层
	                	ProjectProcess pro = new ProjectProcess();
	                    if (project.getId() != null){//说明不是第一次读取了，因为如果是第一层，那么还没有进行数据库的添加，没有返回主键Id
	                        pro.setParentId(project.getId());//将上一级目录的Id赋值给下一级的ParentId
	                    }
	                    pro.setProjectId(projectId);
	                    pro.setImportTime(new Date());//导入时间
	                    pro.setLevel(levelNum);//层级
	                    pro.setTaskName(tasks.get(i).getName());//这个是获取文件中的“任务名称”列的数据
	                    pro.setDurationDate(tasks.get(i).getDuration().toString());//获取的是文件中的“工期”
	                    pro.setStartDate(tasks.get(i).getStart());//获取文件中的 “开始时间”
	                    pro.setEndDate(tasks.get(i).getFinish());//获取文件中的 “完成时间”
	                    pro.setResource(tasks.get(i).getResourceGroup());//获取文件中的 “资源名称”
	                    projectProcessDao.addProjectProcess(pro);//将该条数据添加到数据库，并且会返回主键Id，用做子任务的ParentId,这个需要在mybatis的Mapper中设置
	                    getChildrenTask(tasks.get(i), pro,list,levelNum,projectId);//继续进行递归，当前保存的只是父任务的信息
	                }else{//继续进行递归
	                    getChildrenTask(tasks.get(i), project, list, levelNum,projectId);
	                }
	            }
	        }else{//说明已经到了最底层的子任务了，那么就调用进行最底层数据读取的方法
	            if (project.getId() != null){
	                getResourceAssignment(task, project, list, levelNum,projectId);
	            }
	        }
	    }
	public void getResourceAssignment(Task task, ProjectProcess project, List<ProjectProcess> proList, int levelNum,Long projectId){
	    List<ResourceAssignment> list = task.getResourceAssignments();//读取最底层的属性
	    ResourceAssignment rs = list.get(0);
	    ProjectProcess pro = new ProjectProcess();
	    pro.setTaskName(task.getName());
	    pro.setProjectId(projectId);
	    pro.setParentId(project.getId());
	    pro.setLevel(levelNum);
	    pro.setImportTime(new Date());
	    pro.setDurationDate(task.getDuration().toString());
	    pro.setStartDate(rs.getStart());//注意，这个从ResourceAssignment中读取
	    pro.setEndDate(rs.getFinish());//同上
	    String resource = "";
	    if(list.size() > 1){
	        for (int i = 0; i < list.size(); i++) {
	            if (list.get(i).getResource() != null){
	                if(i < list.size() - 1){
	                    resource += list.get(i).getResource().getName() + ",";
	                }else{
	                    resource += list.get(i).getResource().getName();
	                }
	            }
	        }
	    }else{

	        if(list.size() > 0 && list.get(0).getResource() != null){
	            resource = list.get(0).getResource().getName();
	        }
	    }
	    if(!StringUtils.isEmpty(resource)){
	        pro.setResource(resource);
	    }
	    projectProcessDao.addProjectProcess(pro);//将数据保存在数据库中,同样会返回主键
	    proList.add(pro);
	}

	@Override
	public DataWrapper<List<ProjectProcessPojo>> getProjectProcessList(ProjectProcess projectProcess, String token) {
		DataWrapper<List<ProjectProcessPojo>> result = new DataWrapper<List<ProjectProcessPojo>>();
		List<ProjectProcess> size = new ArrayList<ProjectProcess>();
		List<ProjectProcessPojo> resultPojo = new ArrayList<ProjectProcessPojo>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(projectProcess!=null){
				size = projectProcessDao.getProjectProcessList(projectProcess);
				for(int i=0;i<size.size();i++){
					ProjectProcessPojo pojo = new ProjectProcessPojo();
					pojo.setId(size.get(i).getId());
					pojo.setParentId(size.get(i).getParentId());
					pojo.setStartDate(Parameters.getSdf().format(size.get(i).getStartDate()));
					pojo.setEndDate(Parameters.getSdf().format(size.get(i).getEndDate()));
					pojo.setLevel(size.get(i).getLevel());
					pojo.setImportTime(Parameters.getSdf().format(size.get(i).getImportTime()));
					pojo.setTaskName(size.get(i).getTaskName());
					  
					int days = (int) ((size.get(i).getEndDate().getTime() - size.get(i).getStartDate().getTime()) / (1000*3600*24));
					pojo.setDurationDate(days);
					resultPojo.add(pojo);
				}
				result.setData(resultPojo);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<ProjectProcessPojo>> findProjectProcessList(String name, Long projectId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<ProjectProcessPojo>> result = new DataWrapper<List<ProjectProcessPojo>>();
		List<ProjectProcessPojo> ret = new ArrayList<ProjectProcessPojo>();
		List<ProjectProcess> lists = new ArrayList<ProjectProcess>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(projectId!=null){
				lists = projectProcessDao.getProjectProcessListByName(name,projectId);
				if(!lists.isEmpty()){
					for(int i=0;i<lists.size();i++){
						ProjectProcessPojo pojo = new ProjectProcessPojo();
						pojo.setTaskName(lists.get(i).getTaskName());
						pojo.setImportTime(Parameters.getSdf().format(lists.get(i).getImportTime()));
						pojo.setStartDate(Parameters.getSdf().format(lists.get(i).getStartDate()));
						pojo.setEndDate(Parameters.getSdf().format(lists.get(i).getEndDate()));
						pojo.setLevel(lists.get(i).getLevel());
						int days = (int) ((lists.get(i).getEndDate().getTime() - lists.get(i).getStartDate().getTime()) / (1000*3600*24));
						pojo.setDurationDate(days);
						pojo.setParentId(lists.get(i).getParentId());
						pojo.setId(lists.get(i).getId());
						ret.add(pojo);
					}
					result.setData(ret);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

/*	@Override
	public DataWrapper<Object> findProjectProcessList(Long projectId, String token) {
		DataWrapper<Object> foldersList = new DataWrapper<Object>();
		DataWrapper<List<MaterialPlan>> mps = new DataWrapper<List<MaterialPlan>>();
		User userInMemory=SessionManager.getSession(token); 
		if(userInMemory!=null){
			mps=mpDao.getMaterialPlanList(floder,dates);
			if(mps.getData()!=null)
			{
				List dataList = new ArrayList();  
				for(MaterialPlan ss:mps.getData()){
					HashMap dataRecord1 = new HashMap();  
					dataRecord1.put("id", ss.getId().toString());
					dataRecord1.put("name", ss.getName());
					dataRecord1.put("remark", ss.getRemark());
					if(ss.getModel()!=null){
						dataRecord1.put("model", ss.getModel());
					}else{
						dataRecord1.put("model", "");
					}
					if(ss.getStartTime()!=null){
						dataRecord1.put("startTime", Parameters.getSdfs().format(ss.getStartTime()));
					}else{
						dataRecord1.put("startTime", "");
					}
					
					if(ss.getEndTime()!=null){
						dataRecord1.put("endTime",Parameters.getSdfs().format( ss.getEndTime()));
					}
					if(ss.getStandard()!=null){
						dataRecord1.put("standard", ss.getStandard());
					}else{
						dataRecord1.put("standard", "");
					}
					if(ss.getUnit()!=null){
						dataRecord1.put("unit",ss.getUnit());
					}else{
						dataRecord1.put("unit","");
					}
					if(ss.getNum()!=null){
						dataRecord1.put("num", ss.getNum()+"");
					}else{
						dataRecord1.put("num","");
					}
					if(ss.getGetTime()!=null){
						dataRecord1.put("getTime", ss.getGetTime());
					}else{
						dataRecord1.put("getTime", "");
					}
					if(ss.getOutPlace()!=null){
						dataRecord1.put("outPlace", ss.getOutPlace());
					}else{
						dataRecord1.put("outPlace", "");
					}
					if(ss.getUsePlace()!=null){
						dataRecord1.put("usePlace", ss.getUsePlace());
					}else{
						dataRecord1.put("usePlace", "");
					}
					if(ss.getPid()==0){
						dataRecord1.put("pid", "");
					}else{
						dataRecord1.put("pid", ss.getPid().toString());
					}
					dataList.add(dataRecord1);
				}
				MaterialPlanNodeUtil nodeUtil = new MaterialPlanNodeUtil();
				
				String resultString=nodeUtil.getJasonStringOfMaterialPlan(dataList);
				if(resultString!=null){
					foldersList.setData(JSONArray.parse(resultString));
				}
				
			}
			FolderPojo folderPojo = new FolderPojo();
			
			
		}else{
			foldersList.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return foldersList;  
	}*/
	
}
