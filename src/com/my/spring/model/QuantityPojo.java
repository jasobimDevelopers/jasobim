package com.my.spring.model;



//@Entity
//@Table(name="")
public class QuantityPojo {
	
    private Long num;
	private Long project_id;
	private Integer building_num;
	private Integer unit_num;
	private Integer floor_num;
	private Integer household_num;
	private String size;
	private String service_type;
	private String system_type;
	private String family_and_type;
	private double lengthnum;
	private double areanum;
	private String material;
	private String name;
	private String type_name;
	private Integer profession_type;
	public Long getNum() {
		return num;
	}
	public void setNum(Long value) {
		this.num = value;
	}
	public Long getProject_id() {
		return project_id;
	}
	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}
	public Integer getBuilding_num() {
		return building_num;
	}
	public void setBuilding_num(Integer building_num) {
		this.building_num = building_num;
	}
	public Integer getUnit_num() {
		return unit_num;
	}
	public void setUnit_num(Integer unit_num) {
		this.unit_num = unit_num;
	}
	public Integer getFloor_num() {
		return floor_num;
	}
	public void setFloor_num(Integer floor_num) {
		this.floor_num = floor_num;
	}
	public Integer getHousehold_num() {
		return household_num;
	}
	public void setHousehold_num(Integer household_num) {
		this.household_num = household_num;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getSystem_type() {
		return system_type;
	}
	public void setSystem_type(String system_type) {
		this.system_type = system_type;
	}
	public String getFamily_and_type() {
		return family_and_type;
	}
	public void setFamily_and_type(String family_and_type) {
		this.family_and_type = family_and_type;
	}
	public double getLengthnum() {
		return lengthnum;
	}
	public void setLengthnum(double lengthnum) {
		this.lengthnum = lengthnum;
	}
	public double getAreanum() {
		return areanum;
	}
	public void setAreanum(double areanum) {
		this.areanum = areanum;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public QuantityPojo(){
		
	}
	public QuantityPojo(Long num,Long project_id, Integer building_num,
    Integer unit_num,Integer floor_num,Integer household_num,String size,
	 String service_type,String system_type,String family_and_type,
	 double lengthnum,double areanum,String material,String name,String type_name) {
		this.num=num;
		this.project_id=project_id;
		this.building_num=building_num;
		this.unit_num=unit_num;
		this.floor_num=floor_num;
		this.household_num=household_num;
		this.size=size;
		this.service_type=service_type;
		this.system_type=system_type;
		this.family_and_type=family_and_type;
		this.lengthnum=lengthnum;
		this.areanum=areanum;
		this.material=material;
		this.name=name;
		this.type_name=type_name;
	}
	public Integer getProfession_type() {
		return profession_type;
	}
	public void setProfession_type(Integer profession_type) {
		this.profession_type = profession_type;
	}
}
