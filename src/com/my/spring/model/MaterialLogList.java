package com.my.spring.model;

import java.util.List;

public class MaterialLogList {
	private List<MaterialLog> mls ;
	public MaterialLogList(List<MaterialLog> mls) {
	        super();
	        this.setMls(mls);
	}
    public MaterialLogList() {
        super();
    }
	public List<MaterialLog> getMls() {
		return mls;
	}
	public void setMls(List<MaterialLog> mls) {
		this.mls = mls;
	}
}
