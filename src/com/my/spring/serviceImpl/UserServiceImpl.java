package com.my.spring.serviceImpl;

import com.my.spring.DAO.TokenDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.TokenEntity;
import com.my.spring.model.UserEntity;
import com.my.spring.service.UserService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    TokenDao tokenDao;
    @Override
    public DataWrapper<Void> addUser(UserEntity user) {
    	DataWrapper<Void> retDataWrapper = new DataWrapper<Void>();
        UserEntity usernew = userDao.findByUserName(user.getUserName());
        if(usernew == null && (user.getType() == 4 || user.getType() == 3)) {
            if(user != null && user.getUserName() != null && user.getPassword() != null) {
                user.setRegisterDate(new java.sql.Date(System.currentTimeMillis()));
                user.setType(user.getType());
                if(userDao.addUser(user))
                    retDataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
                else retDataWrapper.setErrorCode(ErrorCodeEnum.Error);

            } else retDataWrapper.setErrorCode(ErrorCodeEnum.Error);

        } else retDataWrapper.setErrorCode(ErrorCodeEnum.Register_Error);

        return retDataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteUser(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!userDao.deleteUser(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateUser(UserEntity user) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!userDao.updateUser(user)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<UserEntity>> getUserList() {
        return userDao.getUserList();
    }
    @Override
    public DataWrapper<Void> login(String userName, String password) {
        DataWrapper<Void> retDataWrapper = new DataWrapper<Void>();
       UserEntity user = userDao.findByUserName(userName);
        if(user != null) {
            if(user.getPassword().equals(MD5Util.getMD5String(password))) {
                String tokenString = MD5Util.getMD5String(userName + new Date().toString() + password);
                TokenEntity token = tokenDao.findByUserId(user.getId());
                boolean loginState = false;
                if (token == null) {
                    token = new TokenEntity();
                    System.out.println(tokenString);
                    token.setToken(tokenString);
                    token.setLoginDate(new Timestamp(System.currentTimeMillis()));
                    token.setUserId(user.getId());
                    loginState = tokenDao.addToken(token);
                } else {
                    token.setToken(tokenString);
                    token.setLoginDate(new Timestamp(System.currentTimeMillis()));
                    loginState = tokenDao.updateToken(token);
                }
                if(loginState) {
                    retDataWrapper.setToken(tokenString);
                } else {
                    retDataWrapper.setErrorCode(ErrorCodeEnum.Error);
                }

            } else {
                retDataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
        } else {
            retDataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }

        return retDataWrapper;
    }
}
