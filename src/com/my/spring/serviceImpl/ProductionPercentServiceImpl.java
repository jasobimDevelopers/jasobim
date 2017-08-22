package com.my.spring.serviceImpl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.ProductionPercentDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.ProductionPercent;
import com.my.spring.model.ProductionPercentPojo;
import com.my.spring.model.User;
import com.my.spring.service.ProductionPercentService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;


@Service("ProductionPercentService")
public class ProductionPercentServiceImpl implements ProductionPercentService {
	@Autowired
	ProductionPercentDao ProductionPercentDao;
	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<Void> addProductionPercent(String content, String token, String tel) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DataWrapper<Void> deleteProductionPercentPercent(String ids, String token) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DataWrapper<List<ProductionPercentPojo>> getProductionPercentList(Integer pageIndex, Integer pageSize,
			ProductionPercent productionPercent, String token) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
