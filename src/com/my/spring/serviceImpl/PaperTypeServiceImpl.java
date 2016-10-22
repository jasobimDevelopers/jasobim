package com.my.spring.serviceImpl;

import com.my.spring.DAO.PaperTypeDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.PaperType;
import com.my.spring.service.PaperTypeService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("paperTypeService")
public class PaperTypeServiceImpl implements PaperTypeService {
    @Autowired
    PaperTypeDao PaperTypeDao;
    @Override
    public DataWrapper<Void> addPaperType(PaperType paperType) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!PaperTypeDao.addPaperType(paperType)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deletePaperType(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!PaperTypeDao.deletePaperType(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updatePaperType(PaperType paperType) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!PaperTypeDao.updatePaperType(paperType)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<PaperType>> getPaperTypeList() {
        return PaperTypeDao.getPaperTypeList();
    }
}
