package test;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import com.wordnik.swagger.annotations.ApiModelProperty;

import base.constant.DateProcessUtil;
import base.constant.XaConstant;



/**
 * 基础Entity实体类
 * @author zj
 *
 */
@MappedSuperclass
public abstract class DBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * ID
	 */
	private String tid;

	@ApiModelProperty(value = "状态，0为无效，1为正常,3删除 参看XaConstant.Status")
	private Integer status;

	@ApiModelProperty(value = "版本,hibernate维护")
	private Integer version;

	@ApiModelProperty(value = "@Fields createUser : 创建者")
	private Long createUser;

	@ApiModelProperty(value = "@Fields createTime : 创建时间")
	private String createTime;

	@ApiModelProperty(value = "@Fields modifyUser : 修改者")
	private Long modifyUser;

	@ApiModelProperty(value = "@Fields modifyTime : 修改时间")
	private String modifyTime;

	@ApiModelProperty(value = "@Fields modifyDescription : 修改描述")
	private String modifyDescription;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
	@Column(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	/**
	 * 为确保赋值增加默认值1:正常
	 */
	@Column(nullable = false, columnDefinition = "int default 1")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "createUser", updatable=false)
	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	@Column(name = "createTime")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modifyUser")
	public Long getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Long modifyUser) {
		this.modifyUser = modifyUser;
	}

	@Column(name = "modifyTime")
	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "modifyDescription", length = 500)
	public String getModifyDescription() {
		return modifyDescription;
	}

	public void setModifyDescription(String modifyDescription) {
		this.modifyDescription = modifyDescription;
	}

	/**
	 * 数据插入前的操作
	 */
	@PrePersist
	public void setInsertBefore() {
		this.createTime = DateProcessUtil.getStrDate(new Date(), DateProcessUtil.YYYYMMDDHHMMSS);
		if(status == null){
			this.status = XaConstant.Status.valid;
		}
	}

	/**
	 * 数据修改前的操作
	 */
	@PreUpdate
	public void setUpdateBefore() {
		this.modifyTime = DateProcessUtil.getStrDate(new Date(), DateProcessUtil.YYYYMMDDHHMMSS);
	}

	public static String getLongToString(Long longTime) {
		if (longTime == null) {
			return null;
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(longTime);
		return sf.format(date);
	}
}
