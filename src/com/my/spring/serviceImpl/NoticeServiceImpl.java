package com.my.spring.serviceImpl;

import com.my.spring.DAO.BuildingDao;
import com.my.spring.DAO.ItemDao;
import com.my.spring.DAO.MinItemDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QuantityDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Building;
import com.my.spring.model.Item;
import com.my.spring.model.MinItem;
import com.my.spring.model.MinItemPojo;
import com.my.spring.model.Notice;
import com.my.spring.model.Project;
import com.my.spring.model.Quantity;
import com.my.spring.model.QuantityPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.ProjectDatas;
import com.my.spring.service.FileService;
import com.my.spring.service.ItemService;
import com.my.spring.service.NoticeService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.QRCodeUtil2;
import com.my.spring.utils.SessionManager;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

	@Override
	public DataWrapper<Notice> getById(String token, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<Void> deleteNoticeById(String token, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<Void> deleteNoticeByIdList(String token, String[] id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<Void> addNotice(String token, Notice file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<JSONArray> getMenuListMapByIdList(List<Notice> menu) {
		// TODO Auto-generated method stub
		return null;
	}
   

}
