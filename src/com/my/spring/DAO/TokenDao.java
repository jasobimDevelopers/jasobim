package com.my.spring.DAO;

import com.my.spring.model.TokenEntity;

public interface TokenDao {
	boolean addToken(TokenEntity token);
    boolean deleteToken(TokenEntity token);
    boolean updateToken(TokenEntity token);
    TokenEntity findByTokenString(String token);
    TokenEntity findByUserId(Long userId);

}
