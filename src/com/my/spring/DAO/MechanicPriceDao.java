package com.my.spring.DAO;

import java.util.Date;
import java.util.List;

import com.my.spring.model.MechanicDataOfHour;
import com.my.spring.model.MechanicDataPeople;
import com.my.spring.model.MechanicPrice;
import com.my.spring.model.MechanicPriceNum;
import com.my.spring.utils.DataWrapper;

public interface MechanicPriceDao {
	 boolean addMechanicPrice(MechanicPrice am);
     boolean deleteMechanicPrice(Long id);
     boolean updateMechanicPrice(MechanicPrice am);
	 DataWrapper<List<MechanicPrice>> getMechanicPriceList(Integer pageIndex, Integer pageSize, MechanicPrice am);
	 DataWrapper<Void> deleteMechanicPriceByMechanicId(Long id);
	MechanicPrice getMechanicPriceById(Long id);
	boolean addMechanicPriceList(List<MechanicPrice> am);
	MechanicPrice getMechanicPriceLists(Integer pageIndex, Integer pageSize, MechanicPrice am);
	List<MechanicPrice> getMechanicPriceListByMechanicId(Integer i, Integer j, Long id, Date date, Date date2);
	List<MechanicPriceNum> getMechanicPriceNumByProjectId(String startday, String endday, Long projectId);
	MechanicPrice getMechanicPriceListByInfos(Long id, Long projectId, Date format);
	List<MechanicDataOfHour> getMechanicHourByProjectId(String startday, String endday, Long projectId);
	List<MechanicDataPeople> getMechanicPeopleByProjectId(String startday, String endday, Long projectId);
}
