package com.my.spring.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.service.FileService;
import com.my.spring.utils.MD5Util;

@Service("fileService")
public class FileServiceImpl implements FileService  {

	@Override
	public String uploadFile(String filePath, MultipartFile file) {
		if (file == null || filePath == null || filePath.equals("")) {
			return null;
		}
        String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

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

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return newFileName;
	}

	@Override
	public boolean deleteFile(String filePathAndName) {
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
