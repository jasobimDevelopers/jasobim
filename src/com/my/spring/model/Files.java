package com.my.spring.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "file")
public class Files {
    private Long id;
    private String name;
    private String intro;
    private Integer fileType;//////0.模型文件 1.图纸文件  2.问题文件 3.留言文件  4.交底 5.图片
    private String url;
    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Basic
    @Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Basic
    @Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Basic
	@Column(name = "desc")
	public String getDesc() {
		return intro;
	}

	public void setDesc(String desc) {
		this.intro = desc;
	}
	@Basic
	@Column(name = "file_type")
	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
   
    

}
