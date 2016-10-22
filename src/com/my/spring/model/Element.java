package com.my.spring.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "element")
public class Element {
    private Long id;
    private String location;//构件所属具体位置（项目A-楼号B-单元C-楼层D-户E）
    private String bottom_elevation;//底部高程
    private String size;      //尺寸
    private float length;    //长度
    private String service_type;//设备类型
    private String familyandtype;
    private String level;    //标高
    private String offset;   //偏移量
    private String area;    //面积
    private String system_type;//系统类型
    private String material;//材质
    private String selfid;  //模型中对应的id
    private String name;   //构件名称
    private String type_name;//类型名

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
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "bottom_elevation")
    public String getBottom_elevation() {
        return bottom_elevation;
    }

    public void setBottom_elevation(String bottom_elevation) {
        this.bottom_elevation = bottom_elevation;
    }

    @Basic
    @Column(name = "size")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Basic
    @Column(name = "length")
	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	@Basic
    @Column(name = "service_type")
	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	@Basic
    @Column(name = "familyandtype")
	public String getFamilyandtype() {
		return familyandtype;
	}

	public void setFamilyandtype(String familyandtype) {
		this.familyandtype = familyandtype;
	}
	@Basic
    @Column(name = "level")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Basic
    @Column(name = "offset")
	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	@Basic
    @Column(name = "area")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Basic
    @Column(name = "system_type")
	public String getSystem_type() {
		return system_type;
	}

	public void setSystem_type(String system_type) {
		this.system_type = system_type;
	}
	
	@Basic
    @Column(name = "material")
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	@Basic
    @Column(name = "selfid")
	public String getSelfid() {
		return selfid;
	}

	public void setSelfid(String selfid) {
		this.selfid = selfid;
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
    @Column(name = "type_name")
	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
    
    

}
