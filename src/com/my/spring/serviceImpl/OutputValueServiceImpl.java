package com.my.spring.serviceImpl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.spring.DAO.OutputValueDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.OutputValue;
import com.my.spring.model.OutputValuePojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.OutputValueService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("outputValueService")
public class OutputValueServiceImpl implements OutputValueService  {
	@Autowired
	OutputValueDao OutputValueDao;
	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<Void> deleteOutputValueById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!OutputValueDao.deleteOutputValue(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<OutputValue> addOutputValue(String token,OutputValue role) {
		DataWrapper<OutputValue> result = new DataWrapper<OutputValue>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				if(role.getProjectId()!=null){
					OutputValue old = new OutputValue();
					old = OutputValueDao.getOutputValueLists(role);
					if(old!=null){
						result.setErrorCode(ErrorCodeEnum.Target_Already_Existed);
						return result;
					}
					role.setCreateUser(user.getId());
					role.setCreateDate(new Date());
					String dates="";
					dates=dates+role.getYear();
					if(role.getMonth()<10){
						dates=dates+"-0"+role.getMonth()+"-01";
					}else{
						dates=dates+"-"+role.getMonth()+"-01";
					}
					try {
						role.setValueDate(Parameters.getSdfs().parse(dates));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					/*role.setLastMonthArmourNum(0.00);
					role.setLastMonthOperatingIncomeNum(0.00);
					role.setAllOpeArmourNum(role.getArmourNum());
					role.setAllOperatingIncomeNum(role.getOperatingIncomeNum());
					Double lastMonthArmourNum=0.00;
					Double lastMonthOperatingIncomeNum=0.00;
					Double allOperatingIncomeNum=0.00;
					Double allOpeArmourNum=0.00;
					List<OutputValue> gets=OutputValueDao.getOutputValueLists(role);
					List<OutputValue> getsNow=OutputValueDao.getBeOutputValueLists(role);
					if(!gets.isEmpty()){
						for(int i=0;i<gets.size();i++){
							if(gets.get(i).getMonth()<role.getMonth()){
								lastMonthArmourNum=lastMonthArmourNum+gets.get(i).getArmourNum();
								lastMonthOperatingIncomeNum=lastMonthOperatingIncomeNum+gets.get(i).getOperatingIncomeNum();
								allOpeArmourNum=allOpeArmourNum+gets.get(i).getArmourNum();
								allOperatingIncomeNum=allOperatingIncomeNum+gets.get(i).getOperatingIncomeNum();
							}
						}
					}
					if(!getsNow.isEmpty()){
						for(int j=0;j<getsNow.size();j++){
							allOpeArmourNum=allOpeArmourNum+getsNow.get(j).getArmourNum();
							allOperatingIncomeNum=allOperatingIncomeNum+getsNow.get(j).getOperatingIncomeNum();
						}
					}
					if(lastMonthArmourNum!=0.00){
						role.setLastMonthArmourNum(lastMonthArmourNum);
					}
					if(lastMonthOperatingIncomeNum!=0.00){
						role.setLastMonthOperatingIncomeNum(lastMonthOperatingIncomeNum);
					}
					if(allOperatingIncomeNum!=0.00){
						role.setAllOperatingIncomeNum(role.getOperatingIncomeNum()+allOperatingIncomeNum);
					}
					if(allOpeArmourNum!=0.00){
						role.setAllOpeArmourNum(role.getArmourNum()+allOpeArmourNum);
					}*/
					if(!OutputValueDao.addOutputValue(role)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}else{
						result.setData(role);
					}
				}else{
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}



	@Override
	public DataWrapper<List<OutputValuePojo>> getOutputValueList(Integer pageIndex, Integer pageSize, OutputValue OutputValue,
			String token,String dateStart,String dateFinished) {
		DataWrapper<List<OutputValuePojo>> dpPojo = new DataWrapper<List<OutputValuePojo>>();
		List<OutputValuePojo> result = new ArrayList<OutputValuePojo>();
		DataWrapper<List<OutputValue>> dp = new DataWrapper<List<OutputValue>>();
		//List<OutputValue> dpp = new ArrayList<OutputValue>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			Date start=null;
			Date end=null;
			if(dateStart!=null){
				try {
					start=Parameters.getSdfs().parse(dateStart+"-01");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(dateFinished!=null){
				try {
					end=Parameters.getSdfs().parse(dateFinished+"-01");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dp = OutputValueDao.getOutputValueList(pageIndex,pageSize,OutputValue,start,end);
			List<OutputValue> dps = OutputValueDao.getBeOutputValueLists(OutputValue);
			if(dp.getData()!=null){
				if(!dp.getData().isEmpty()){
					for(int i=0;i<dp.getData().size();i++){
						OutputValuePojo re = new OutputValuePojo();
						re.setId(dp.getData().get(i).getId());
						re.setArmourNum(dp.getData().get(i).getArmourNum());
						re.setOperatingIncomeNum(dp.getData().get(i).getOperatingIncomeNum());
						re.setCreateDate(Parameters.getSdf().format(dp.getData().get(i).getCreateDate()));
						re.setCreateUser(dp.getData().get(i).getCreateUser());
						re.setMonth(dp.getData().get(i).getMonth());
						re.setYear(dp.getData().get(i).getYear());
						re.setProjectId(dp.getData().get(i).getProjectId());
						Double lastMonthArmourNum=0.00;
						Double lastMonthOperatingIncomeNum=0.00;
						Double allOperatingIncomeNum=0.00;
						Double allOpeArmourNum=0.00;
						for(int j=0;j<dps.size();j++){
							if(dp.getData().get(i).getYear()>dps.get(j).getYear()){
								lastMonthArmourNum=lastMonthArmourNum+dps.get(j).getArmourNum();
								lastMonthOperatingIncomeNum=lastMonthOperatingIncomeNum+dps.get(j).getOperatingIncomeNum();
								allOperatingIncomeNum=allOperatingIncomeNum+dps.get(j).getOperatingIncomeNum();
								allOpeArmourNum=allOpeArmourNum+dps.get(j).getArmourNum();
							}
							if(dp.getData().get(i).getYear().equals(dps.get(j).getYear())){
								if(dp.getData().get(i).getMonth()>dps.get(j).getMonth()){
									lastMonthArmourNum=lastMonthArmourNum+dps.get(j).getArmourNum();
									lastMonthOperatingIncomeNum=lastMonthOperatingIncomeNum+dps.get(j).getOperatingIncomeNum();
									allOperatingIncomeNum=allOperatingIncomeNum+dps.get(j).getOperatingIncomeNum();
									allOpeArmourNum=allOpeArmourNum+dps.get(j).getArmourNum();
								}
							}
						}
						re.setLastMonthArmourNum(lastMonthArmourNum);
						re.setLastMonthOperatingIncomeNum(lastMonthOperatingIncomeNum);
						re.setAllOpeArmourNum(allOpeArmourNum+dp.getData().get(i).getArmourNum());
						re.setAllOperatingIncomeNum(allOperatingIncomeNum+dp.getData().get(i).getOperatingIncomeNum());
						result.add(re);
					}
				}
			}
			dpPojo.setData(result);
			dpPojo.setTotalNumber(dp.getTotalNumber());
			dpPojo.setTotalPage(dp.getTotalPage());
			dpPojo.setNumberPerPage(dp.getNumberPerPage());
			dpPojo.setCurrentPage(dp.getCurrentPage());
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dpPojo;
	}

	@Override
	public DataWrapper<OutputValue> getOutputValueById(String token, Long id) {
		DataWrapper<OutputValue> dp = new DataWrapper<OutputValue>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			OutputValue dt = OutputValueDao.getById(id);
			dp.setData(dt);
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}

	@Override
	public DataWrapper<Void> updateOutputValue(String token, OutputValue outputValue) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(outputValue!=null){
				if(outputValue.getId()!=null){
					OutputValue get = OutputValueDao.getById(outputValue.getId());
					if(get!=null){
						if(outputValue.getArmourNum()!=null){
							get.setArmourNum(outputValue.getArmourNum());
						}
						if(outputValue.getOperatingIncomeNum()!=null){
							get.setOperatingIncomeNum(outputValue.getOperatingIncomeNum());
						}
						OutputValueDao.updateOutputValue(get);
					}
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.Error);
		}
		return null;
	}


	
}
