package com.my.spring.model;

import java.sql.Date;

import javax.persistence.*;


/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "message")
public class Message {
    private Long id;
    private int userid;
    private String name;
    private int type;     //问题类型
    private String trades;//所属专业
    private String desc;  //问题描述
    private String state;//问题的状态（待解决，已解决）
    private String priority;//问题的级别
    private String code_information;//二维码信息
    private String picone;//问题图片
    private String pictwo;//问题解决后图片
    private Date time;//问题状态对应的时间
    

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

    public void setName(String Name) {
        this.name = Name;
    }

    @Basic
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "trades")
    public String getTrades() {
        return trades;
    }

    public void setTrades(String trades) {
        this.trades = trades;
    }

    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    @Basic
    @Column(name = "priority")
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    @Basic
    @Column(name = "code_information")
    public String getCode_information() {
        return code_information;
    }

    public void setCode_information(String code_information) {
        this.code_information = code_information;
    }
    @Basic
    @Column(name = "picone")
    public String getPicone() {
        return picone;
    }

    public void setPicone(String picone) {
        this.picone = picone;
    }
    @Basic
    @Column(name = "pictwo")
    public String getPictwo() {
        return pictwo;
    }

    public void setPictwo(String pictwo) {
        this.pictwo = pictwo;
    }
    @Basic
    @Column(name = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
   
}
