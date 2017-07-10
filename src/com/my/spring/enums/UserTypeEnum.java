package com.my.spring.enums;
import java.io.Serializable;
public enum UserTypeEnum implements Serializable {
	Admin(0),User(1);
	private Integer type;
	UserTypeEnum() {
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	UserTypeEnum(Integer type) {
		this.type = type;
	}
}
