package com.my.spring.serviceImpl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.my.spring.DAO.FolderDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Folder;
import com.my.spring.model.FolderPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.DeFiles;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.FolderService;
import com.my.spring.utils.CopyFilesExample;
import com.my.spring.utils.CustomFileUtil;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MenuRecursion;
import com.my.spring.utils.NodeUtil;
import com.my.spring.utils.ScatterSampleTest;
import com.my.spring.utils.SessionManager;

@Service("folderService")
public class FolderServiceImpl implements FolderService  {
	@Autowired
	FolderDao folderDao;
	@Autowired
	UserDao userDao;
	@Autowired
	FileService fileService;
	private String filePaths="fileUploads/folderFiles/";
	//子节点  
    static  List<Folder> childMenu=new ArrayList<Folder>();  
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  

	@Override
	public Folder getById(Long id) {
		// TODO Auto-generated method stub
		return folderDao.getById(id);
	}



	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<Object> getFolderList(String token, Folder floder) {
		// TODO Auto-generated method stub
		DataWrapper<Object> foldersList = new DataWrapper<Object>();
		List<Folder> folders = new ArrayList<Folder>();
		List<Folder> getfolders = new ArrayList<Folder>();
		User userInMemory=SessionManager.getSession(token); 
		if(userInMemory!=null){
			if(floder.getId()!=null){
				Folder folderOld = new Folder();
				folderOld=folderDao.getById(floder.getId());
				if(folderOld!=null){
					folders=folderDao.getAllFolderss(floder.getProjectId());
					MenuRecursion mr = new MenuRecursion();
					@SuppressWarnings("static-access")
					List<Folder> childrens = mr.treeChildList(folders, floder.getId());
					childrens.add(floder);
					if(!childrens.isEmpty()){
						getfolders = folderDao.getFolderLists(childrens,floder.getProjectId());
						if(!getfolders.isEmpty())
						{
							@SuppressWarnings("rawtypes")
							List dataList = new ArrayList();  
							for(Folder ss:getfolders){
								@SuppressWarnings("rawtypes")
								HashMap dataRecord1 = new HashMap();  
								dataRecord1.put("id", ss.getId().toString());
								dataRecord1.put("name", ss.getName());
								dataRecord1.put("remark", ss.getRemark());
								dataRecord1.put("fileType", ss.getFileType()+"");
								if(ss.getFileId()!=null){
									Files file = fileService.getById(ss.getFileId());
									if(file!=null){
										dataRecord1.put("url", file.getUrl());
									}
								}else{
									dataRecord1.put("url", null);
								}
								if(ss.getParrentId()==0){
									dataRecord1.put("parentId", "");
								}else{
									dataRecord1.put("parentId", ss.getParrentId().toString());
								}
								String str=sdf.format(ss.getCreateDate());
								dataRecord1.put("uploadDate", str);
								if(ss.getUserId()!=null){
									User user = userDao.getById(ss.getUserId());
									if(user!=null){
										String userName=user.getUserName();
										dataRecord1.put("userName", userName);
									}else{
										dataRecord1.put("userName", "");
									}
									
								}
								dataList.add(dataRecord1);
							}
							NodeUtil nodeUtil = new NodeUtil();
							String resultString=nodeUtil.getJasonString(dataList);
							if(resultString!=null){
								foldersList.setData(JSONArray.parse(resultString));
							}
							
						}
					}else{
						if(!folders.isEmpty())
						{
							@SuppressWarnings("rawtypes")
							List dataList = new ArrayList();  
							for(Folder ss:folders){
								@SuppressWarnings("rawtypes")
								HashMap dataRecord1 = new HashMap();  
								dataRecord1.put("id", ss.getId().toString());
								dataRecord1.put("name", ss.getName());
								dataRecord1.put("remark", ss.getRemark());
								dataRecord1.put("fileType", ss.getFileType()+"");
								if(ss.getFileId()!=null){
									Files file = fileService.getById(ss.getFileId());
									if(file!=null){
										dataRecord1.put("url", file.getUrl());
									}
								}else{
									dataRecord1.put("url", null);
								}
								if(ss.getParrentId()==0){
									dataRecord1.put("parentId", "");
								}else{
									dataRecord1.put("parentId", ss.getParrentId().toString());
								}
								String str=sdf.format(ss.getCreateDate());
								dataRecord1.put("uploadDate", str);
								if(ss.getUserId()!=null){
									User user = userDao.getById(ss.getUserId());
									if(user!=null){
										String userName=user.getUserName();
										dataRecord1.put("userName", userName);
									}else{
										dataRecord1.put("userName", "");
									}
									
								}
								dataList.add(dataRecord1);
							}
							NodeUtil nodeUtil = new NodeUtil();
							String resultString=nodeUtil.getJasonString(dataList);
							if(resultString!=null){
								foldersList.setData(JSONArray.parse(resultString));
							}
							
						}
					}
				}else{
					foldersList.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
			}
		}else{
			foldersList.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return foldersList;  
		  
	}

	@Override
	public DataWrapper<Void> addFloder(String token, Folder floder) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(floder!=null){
				floder.setCreateDate(new Date());
				floder.setFileType(0);
				floder.setUserId(user.getId());
				if(floder.getParrentId()==null){
					floder.setParrentId((long) 0);
				}
				if(!folderDao.addFloder(floder)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	@Override
	public DataWrapper<Void> updateFloder(String token, String name, Long id) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(id!=null){
				Folder f=folderDao.getById(id);
				if(name!=null){
					f.setName(name);
				}
				
				if(!folderDao.updateFloder(f)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> uploadFloder(String token, Folder floder, MultipartFile[] fileList,
			HttpServletRequest request) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(fileList!=null){
				List<Folder> addList = new ArrayList<Folder>();
				floder.setCreateDate(new Date());
				floder.setUserId(user.getId());
				floder.setFileType(1);
				for(MultipartFile mf : fileList){
					Folder f = new Folder();
					f.setCreateDate(floder.getCreateDate());
					f.setUserId(floder.getUserId());
					f.setFileType(floder.getFileType());
					f.setParrentId(floder.getParrentId());
					f.setProjectId(floder.getProjectId());
					Files files = fileService.uploadFile(filePaths+floder.getProjectId(), mf, 11, request);
					if(files!=null){
						f.setFileId(files.getId());
						f.setName(files.getRealName());
						f.setSize(files.getSize());
					}
					addList.add(f);
				}
				if(!folderDao.addFloderList(addList)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}



	@Override
	public DataWrapper<Void> deleteFloder(String token, String id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(id!=null){
				String[] idsp =id.split(",");
				for(String s:idsp){
					Folder folderP = folderDao.getById(Long.valueOf(s));
					if(folderP!=null){
						if(folderP.getFileType()==1){
							if(!folderDao.deleteFloder(Long.valueOf(s))){
								result.setErrorCode(ErrorCodeEnum.Error);
							}
						}else{
							List<Folder> getall = folderDao.getAllFolder();
							MenuRecursion men = new MenuRecursion();
							List<Folder> childrens = men.treeChildList(getall, Long.valueOf(s));
							if(!childrens.isEmpty()){
								String[] ids =null;
								ids=new String[childrens.size()+1];
								ids[0]=Long.valueOf(s)+"";
								for(int i=0;i<childrens.size();i++){
									ids[i+1]=childrens.get(i).getId()+"";
								}
								if(!folderDao.deleteFloderList(ids)){
									result.setErrorCode(ErrorCodeEnum.Error);
								}
							}else{
								if(!folderDao.deleteFloder(Long.valueOf(s))){
									//result.setErrorCode(ErrorCodeEnum.Error);
								}
							}
						}
					}
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	

	


	@Override
	public DataWrapper<List<FolderPojo>> findFileLists(String token, String name, Long projectId) {
		DataWrapper<List<FolderPojo>> result = new DataWrapper<List<FolderPojo>>();
		List<FolderPojo> resultGets = new ArrayList<FolderPojo>();
		List<Folder> resultGet = new ArrayList<Folder>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(projectId!=null){
				resultGet=folderDao.getFolderListLike(projectId, name);
				if(!resultGet.isEmpty()){
					for(Folder floder:resultGet){
						FolderPojo fp = new FolderPojo();
						fp.setFileId(floder.getFileId());
						fp.setName(floder.getName());
						fp.setRemark(floder.getRemark());
						fp.setId(floder.getId());
						fp.setParrentId(floder.getParrentId());
						fp.setSize(floder.getSize());
						fp.setFileType(floder.getFileType());
						if(fp.getFileId()!=null){
							Files file = fileService.getById(fp.getFileId());
							if(file!=null){
								fp.setUrl(file.getUrl());
							}
						}
						if(floder.getCreateDate()!=null){
							fp.setCreateDate(Parameters.getSdf().format(floder.getCreateDate()));
						}
						resultGets.add(fp);
					}
					result.setData(resultGets);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		if(result.getCallStatus()==CallStatusEnum.SUCCEED && result.getData()==null){
	       	List<FolderPojo> pas= new ArrayList<FolderPojo>();
	       	result.setData(pas);
	    }
		return result;
	}



	@Override
	public List<Folder> selectByIds(String ids) {
		List<Folder> result = new ArrayList<Folder>();
		if(ids!=null){
			result = folderDao.getByIds(ids);
		}
		return result;
	}



	@Override
	public DataWrapper<List<FolderPojo>> getFolderIndexList(String token, Long projectId, Long pid) {
		DataWrapper<List<FolderPojo>> result = new DataWrapper<List<FolderPojo>>();
		List<FolderPojo> resultGets = new ArrayList<FolderPojo>();
		List<Folder> resultGet = new ArrayList<Folder>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(projectId!=null){
				resultGet=folderDao.getFolderIndexList(projectId, pid);
				if(!resultGet.isEmpty()){
					for(Folder floder:resultGet){
						FolderPojo fp = new FolderPojo();
						fp.setFileId(floder.getFileId());
						fp.setName(floder.getName());
						fp.setRemark(floder.getRemark());
						fp.setParrentId(floder.getParrentId());
						fp.setSize(floder.getSize());
						fp.setId(floder.getId());
						fp.setFileType(floder.getFileType());
						if(fp.getFileId()!=null){
							Files file = fileService.getById(fp.getFileId());
							if(file!=null){
								fp.setUrl(file.getUrl());
							}
						}
						if(floder.getCreateDate()!=null){
							fp.setCreateDate(Parameters.getSdf().format(floder.getCreateDate()));
						}
						resultGets.add(fp);
					}
					result.setData(resultGets);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
	    if(result.getCallStatus()==CallStatusEnum.SUCCEED && result.getData()==null){
		 	List<FolderPojo> pas= new ArrayList<FolderPojo>();
	       	result.setData(pas);
	    }
		return result;
	}



	@Override
	public DataWrapper<Void> takeFolderTo(String token, Long id, Long pid) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(id!=null){
				Folder folder = folderDao.getById(id);
				if(folder!=null){
					folder.setParrentId(pid);
					if(!folderDao.updateFloder(folder)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}
				}else{
					result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	public List<Folder> getFolders(List<String> paths,int i,Folder floders,Long fileId,Integer flag){
		if(flag==null){
			flag=0;
		}
		if(floders.getParrentId()==null){
			floders.setParrentId((long)0);
		}
		if(!paths.isEmpty()){
		    floders.setName(paths.get(i));
			List<Folder> result=folderDao.getFolderList(floders);
			if(result.isEmpty()){
				if(flag==0){
					flag=1;
					for(int j=0;j<paths.size();j++){
						floders.setName(paths.get(j));
						floders.setCreateDate(new Date());
						if(floders.getId()==null){
							if(floders.getParrentId()==null){
								floders.setParrentId((long)0);
							}
						}else{
							floders.setParrentId(floders.getId());
						}
						if(j==(paths.size()-1)){
							floders.setFileType(1);
							floders.setFileId(fileId);
							floders.setName(Parameters.getFileName(floders.getName()));
							int k=0;
							int h=0;
							String str=floders.getName();
							do{
								List<Folder> resultss=folderDao.getFolderLists(floders).getData();
								if(resultss.isEmpty()){
									k=1;
								}else{
									h++;
									floders.setName(str+"("+h+")");
								}
							}while(k<=0);
							floders.setRemark(paths.get(j).split("\\.")[1]);
						}else{
							floders.setFileType(0);
						}
						folderDao.addFloder(floders);
					}
				}
			}else{
				floders.setParrentId(result.get(0).getId());
				floders.setId(result.get(0).getId());
			}
			paths.remove(i);
		}else{
			return null;
		}
		return getFolders(paths,i,floders,fileId,flag);
	}
	@Override
	public DataWrapper<Void> uploadFloderFiles(String token, String filePath, MultipartFile file,
			HttpServletRequest request,Long pid,Long projectId) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(file!=null){
				Folder floders = new Folder();
				floders.setParrentId(pid);
				floders.setUserId(user.getId());
				System.out.println(file.getOriginalFilename());
				floders.setProjectId(projectId);
				floders.setSize(file.getSize()+"");
				String[] paths = filePath.split("/");
				List<String> resultss=new ArrayList<String>(paths.length);
				for(String s:paths){
					resultss.add(s);
				}
				Files files =fileService.uploadFile(filePaths+projectId, file, 11, request);
				if(file.getSize()>0){
					getFolders(resultss,0,floders,files.getId(),null);
				}else{
					result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
				
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	public List<Folder> getChildPath(List<Folder> childList,String parrentName,Long pid,List<Folder> childPath){
		for(int i =0;i<childList.size();i++){
			if(childList.get(i).getParrentId().equals(pid)){
				childList.get(i).setRemark(parrentName+"/"+childList.get(i).getName());
				childPath.add(childList.get(i));
				childList.remove(i);
			}
		}
		//getChildPath(childList,)
		return null;
	}
	@Override
	public DataWrapper<Void> batchDownload(String token, String ids,Long pid,Long projectId ,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataWrapper<Void> result = new DataWrapper<Void>();
		List<Folder> alllists = folderDao.getAllFolder();
		
		List<Folder> lists = this.selectByIds(ids);
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String currentTimemils = System.currentTimeMillis()+"";
		String path = rootPath+"/downloadFolderFiles/"+token+currentTimemils+"/";
		File file_path = new File(rootPath+"/downloadFolderFiles/"+token+currentTimemils+"/");
		if(!file_path.exists()){
			file_path.mkdirs();
		}
		//压缩包名字
		String fileName="download.zip";
		List<File> fileLists = new ArrayList<File>();
		String outpaths="";
		if(!lists.isEmpty()){
			DeFiles  defiles = new DeFiles();
			if(defiles.deletefile(rootPath+"/downloadFolderFiles")){
				for(Folder folder:lists){
					if(folder.getFileType()==1){
						Files files=fileService.getById(folder.getFileId());
						if(files!=null){
							String[] test = rootPath.split("\\\\");
							String[] test1 = files.getUrl().split("/");
							String pathss="";
							String outpaths2="";
							for(String s:test){
								if(pathss.equals("")){
									pathss=s;
								}else{
									pathss=pathss+"\\"+"\\"+s;
								}
							}
							for(String sb:test1){
								if(pathss.equals("")){
									pathss=sb;
								}else{
									pathss=pathss+"\\"+"\\"+sb;
								}
							}
							File source = new File(pathss);
							
							for(String ss:test){
								if(outpaths2.equals("")){
									outpaths2=ss;
								}else{
									outpaths2=outpaths2+"\\"+"\\"+ss;
								}
							}
							String strname = token+currentTimemils;
							String downloadUrl =outpaths2+"\\"+"\\"+"downloadFolderFiles\\\\"+strname; 
							outpaths2=outpaths2+"\\"+"\\"+"downloadFolderFiles\\\\"+strname+"\\\\"+folder.getName()+"."+folder.getRemark();
							File downs = new File(downloadUrl);
							downs.mkdirs();
							File dest = new File(outpaths2);
							CopyFilesExample.copyFileUsingFileChannels(source, dest);
							fileLists.add(dest);
						}
					}else{
						/////获取单个文件夹的所有子
						//Folder father = MenuRecursion.treeParrentList(alllists, folder.getParrentId());
						List<Folder> childrens = new ArrayList<Folder>();
						MenuRecursion men = new MenuRecursion();
						childrens=men.treeChildList(alllists, folder.getId(),folder.getName());
						if(!childrens.isEmpty()){
							childrens.add(folder);
							for(int i=0;i<childrens.size();i++){
								File file=null;
								if(childrens.get(i).getFileType()==1){
									Files files=fileService.getById(childrens.get(i).getFileId());
									if(files!=null){
										String[] test = rootPath.split("\\\\");
										String[] test1 = files.getUrl().split("/");
										String pathss="";
										outpaths="";
										for(String s:test){
											if(pathss.equals("")){
												pathss=s;
											}else{
												pathss=pathss+"\\"+"\\"+s;
											}
										}
										for(String sb:test1){
											if(pathss.equals("")){
												pathss=sb;
											}else{
												pathss=pathss+"\\"+"\\"+sb;
											}
										}
										File source = new File(pathss);
									
										for(String ss:test){
											if(outpaths.equals("")){
												outpaths=ss;
											}else{
												outpaths=outpaths+"\\"+"\\"+ss;
											}
										}
										outpaths=outpaths+"\\"+"\\"+"downloadFolderFiles\\\\"+token+currentTimemils+"\\\\";
										String[] out2 = childrens.get(i).getRemark().split("/");
										String outFiles="";
										outFiles=outpaths;
										for(String sb1:out2){
											if(outFiles.equals("")){
												outFiles=sb1;
											}else{
												outFiles=outFiles+"\\"+"\\"+sb1;
											}
										}
										file = new File(outFiles);
										if(file.exists()){
											file.delete();
										}else{
											String[] trys = outFiles.split("\\\\");
							        		String tests="";
							        		for(int p=0;p<trys.length-1;p++){
							        			if(tests.equals("")){
							        				tests=trys[p];
							        			}else{
							        				tests=tests+"\\\\"+trys[p];
							        			}
							        			
							        		}
							        		File stry = new File(tests);
							        		stry.mkdirs();
										}
										CopyFilesExample.copyFileUsingFileChannels(source, file);
										fileLists.add(file);
									}
								}else{
									if(childrens.size()==1 || (childrens.size()>1 && i==(childrens.size()-1))){
										file = new File(path+childrens.get(i).getName());
										if(!file.exists() && !file.isDirectory()) {
										    file.mkdirs();
										}
									}else{
										file = new File(path+childrens.get(i).getRemark());
										if(!file.exists() && !file.isDirectory()) {
										    file.mkdirs();
										}
									}
									
								}
								//如果路径不存在，新建
								fileLists.add(file);
							}
							}
						}
						}
					}
			}
			if(lists.size()==1){
    		   fileName =lists.get(0).getName()+".zip";
			}
			ScatterSampleTest test = new ScatterSampleTest();
			if(outpaths.equals("")){
			String[] test1 = path.split("/");
			String pathss="";
			for(String s:test1){
				if(pathss.equals("")){
					pathss=s;
				}else{
					pathss=pathss+"\\"+"\\"+s;
				}
			}
			outpaths=pathss;
		}
	    File results = new File(outpaths+"\\"+fileName);  
	    test.createZipFile(outpaths, results);  
	    if(CustomFileUtil.downloadFile(results, response, true)){
	    	
	    }
        return result;
	}
	public List<File> getFileLists(File file){
		List<File> files = new ArrayList<File>();
		if(file.exists()) {
	           File[] fileArr = file.listFiles();
	           for (File file2 : fileArr) {
	               files.add(file2);
	           }
		}else{
			file.mkdirs();
		}
		return files;
	}
	public DataWrapper<Void> testDownloadZip(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataWrapper<Void> result = new DataWrapper<Void>();
		long begin = System.currentTimeMillis();  
		ScatterSampleTest test = new ScatterSampleTest();
	    final File results = new File("C:\\Users\\Han\\Desktop\\downloadTest\\test.zip");  
	    test.createZipFile("C:\\Users\\Han\\Desktop\\施工规范", results);  
	   
	    long end = System.currentTimeMillis();  
	    System.out.println("用时：" + (end - begin) + " ms");  
	    File files = new File("C:\\Users\\Han\\Desktop\\downloadTest\\test.zip");
	    CustomFileUtil.downloadFile(files, response, true); 
		return result;
	}
	
	
}
