package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name= "budget")
public class Budget {
	private Long id;
	private String projectCode;
	private String projectName;
	private String projectDescription;
	private String unit;
	private Double quantity;
	private Double onePrice;
	private Double priceNum;
	private Long projectId;
	private Long userId;
	private Date uploadDate;
	
}
