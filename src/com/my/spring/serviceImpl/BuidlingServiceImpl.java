package com.my.spring.serviceImpl;

import com.my.spring.DAO.BuildingDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.BuildingEntity;
import com.my.spring.service.BuildingService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("buildingService")
public class BuidlingServiceImpl implements BuildingService {
    @Autowired
    BuildingDao BuildingDao;
    @Override
    public DataWrapper<Void> addBuilding(BuildingEntity Building) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!BuildingDao.addBuilding(Building)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteBuilding(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!BuildingDao.deleteBuilding(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateBuilding(BuildingEntity Building) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!BuildingDao.updateBuilding(Building)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<BuildingEntity>> getBuildingList() {
        return BuildingDao.getBuildingList();
    }
}
