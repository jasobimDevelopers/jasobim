package com.my.spring.model;
import javax.persistence.*;
@Entity
@Table(name = "file")
public class Files {
    private Long id;
    private String name;
    private String intro;
    private Integer fileType;//////0.模型文件 1.图纸文件  2.问题文件 3.留言文件  4.交底 5.图片 6.构件excel 7.预制化文件 8.要生成二维码的文件9.项目工程资料
    private String url;
    private String realName;//////文件原名称
    @Id
    @GeneratedValue///
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
    @Column(name = "real_name")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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
	@Column(name = "intro")
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
