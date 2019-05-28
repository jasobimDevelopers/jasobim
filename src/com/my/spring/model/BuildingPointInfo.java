package com.my.spring.model;

import java.util.Date;

public class BuildingPointInfo {
	private Long id;
	private String buildingInfo;//楼栋名称
	private Long projectId;
	private Long paperPointId;
	private Date createDate;
	private Long createUser;
	private Integer rightOr;//0、正确 1、爆点
}
