package com.my.spring.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.DAO.FileDao;
import com.my.spring.model.Files;
import com.my.spring.service.FileService;
import com.my.spring.service.ItemService;
import com.my.spring.utils.MD5Util;

@Service("fileService")
public class FileServiceImpl implements FileService  {
	@Autowired
	ItemService itemService;
	@Autowired
	FileDao fileDao;
	@SuppressWarnings("unused")
	@Override
	public String uploadFile(String filePath, MultipartFile file, Integer fileType) {
		
		if (file == null || filePath == null || filePath.equals("")) {
			return null;
		}
        String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //批量导入。参数：文件名，文件。
//        boolean b = itemService.batchImport(newFileName,file);
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(filePath + "/"
                    + newFileName);
                // 写入文件
            out.write(file.getBytes());
            out.flush();
            out.close();
            
            Files files=new Files();
            files.setDesc((new Date()).toString());
            String realPath=filePath + "/"+ newFileName;
            files.setName(newFileName);//////构件的url
            files.setUrl(realPath);
            files.setFileType(fileType);
            fileDao.addFiles(files);
            

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return newFileName;
	}

	@Override
	public boolean deleteFileByPath(String filePathAndName) {
		// TODO Auto-generated method stub
		boolean flag = false;
		File file = new File(filePathAndName);
        try {
            flag = file.delete();
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return flag;
	}
	


}
