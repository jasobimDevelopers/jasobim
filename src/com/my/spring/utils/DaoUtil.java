package com.my.spring.utils;

public class DaoUtil {
	public static int getTotalPageNumber(int totalItemNum, int pageSize) {
        if (totalItemNum % pageSize == 0) {
            return (int) totalItemNum / pageSize;
        } else {
            return (int) totalItemNum / pageSize + 1;
        }
    }
}
