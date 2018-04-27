package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name= "budget")
public class Budget {
	private Long id;
	private String fileName;
	private String fileSize;
	private String fileId;
	private Date uploadDate;
	
}
