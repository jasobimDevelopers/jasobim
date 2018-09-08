package com.my.spring.serviceImpl;
import com.my.spring.DAO.ProductionRecordsDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ProductionRecords;
import com.my.spring.model.User;
import com.my.spring.service.ProductionRecordsService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("productionRecordsService")
public class ProductionRecordsServiceImpl implements ProductionRecordsService {

    @Autowired
    ProductionRecordsDao productionRecordsDao;
	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<Void> deleteProductionRecordsById(Long id,String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			productionRecordsDao.deleteProductionRecords(id);
		}else{
			result.setErrorCode(ErrorCodeEnum.AUTH_Error);
		}
		return result;
	}

	@Override
	public DataWrapper<ProductionRecords> addProductionRecords(ProductionRecords constructPart,String token) {
		DataWrapper<ProductionRecords> result = new DataWrapper<ProductionRecords>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			constructPart.setCreateDate(new Date());
			constructPart.setCreateUserId(userInMemory.getId());
			if(!productionRecordsDao.addProductionRecords(constructPart)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.AUTH_Error);
		}
		return result;
	}

	@Override
	public DataWrapper<List<ProductionRecords>> getProductionRecordsList(String token,Long constructionLogId) {
		DataWrapper<List<ProductionRecords>> result = new DataWrapper<List<ProductionRecords>>();
		List<ProductionRecords> getList = new ArrayList<ProductionRecords>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			getList = productionRecordsDao.getProductionRecordsList(constructionLogId);
			result.setData(getList);
		}else{
			result.setErrorCode(ErrorCodeEnum.AUTH_Error);
		}
		return result;
	}

	@Override
	public DataWrapper<ProductionRecords> getProductionRecordsById(String token, Long id) {
		// TODO Auto-generated method stub
		DataWrapper<ProductionRecords> result = new DataWrapper<ProductionRecords>();
		ProductionRecords get = new ProductionRecords();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			get = productionRecordsDao.getById(id);
			result.setData(get);
		}else{
			result.setErrorCode(ErrorCodeEnum.AUTH_Error);
		}
		return result;
	}



}
