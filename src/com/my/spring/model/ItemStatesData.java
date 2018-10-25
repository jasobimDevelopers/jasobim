package com.my.spring.model;

import java.util.List;

/**
* @author 徐雨祥
* @version 创建时间：2018年10月25日 上午9:32:16
* 类说明
*/
public class ItemStatesData {
    private List<String> professionDatas;   //构件名称
    private String finished;//完成数（0-100）
	
	public String getFinished() {
		return finished;
	}
	public void setFinished(String finished) {
		this.finished = finished;
	}
	public List<String> getProfessionDatas() {
		return professionDatas;
	}
	public void setProfessionDatas(List<String> professionDatas) {
		this.professionDatas = professionDatas;
	}
    

    
}
