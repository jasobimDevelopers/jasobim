package com.my.spring.serviceImpl;

import com.my.spring.DAO.PapersDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Papers;
import com.my.spring.service.PapersService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("papersService")
public class PapersServiceImpl implements PapersService {
    @Autowired
    PapersDao PapersDao;
    @Override
    public DataWrapper<Void> addPapers(Papers papers) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!PapersDao.addPapers(papers)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deletePapers(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!PapersDao.deletePapers(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updatePapers(Papers papers) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!PapersDao.updatePapers(papers)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<Papers>> getPapersList() {
        return PapersDao.getPapersList();
    }
}
