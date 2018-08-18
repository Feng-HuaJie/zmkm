package com.sjq.zmkm.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert(true)
@DynamicUpdate(true)
@Entity
@Table(name = "visitors", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_card_mapping_id","user_id"})})

public class Visitors {
	
	private Integer pkid;
	
	private String startTime;	//起效时间   当前时间
	
	private String endTime;		//结束时间  第二天晚上12点  用于判断邀请通行码是否过期
	
	private Integer num;
	
	//private Integer status;		//有效无效，用于一次性访客码是否有效，使用过后设置无效
	
	private Integer userCardMappingId;
	
	private UserCardMapping userCardMapping;
	
	private Integer userId;
    
    private User user;
	@Id
	@Column(name = "pkid", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getPkid() {
		return pkid;
	}

	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	
	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "pkid", insertable = false, updatable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name = "user_card_mapping_id", nullable = false)
	public Integer getUserCardMappingId() {
		return userCardMappingId;
	}

	public void setUserCardMappingId(Integer userCardMappingId) {
		this.userCardMappingId = userCardMappingId;
	}
	@ManyToOne
	@JoinColumn(name = "user_card_mapping_id", referencedColumnName = "pkid", insertable = false, updatable = false)
	public UserCardMapping getUserCardMapping() {
		return userCardMapping;
	}

	public void setUserCardMapping(UserCardMapping userCardMapping) {
		this.userCardMapping = userCardMapping;
	}
	
	@Column(name = "start_time",nullable = false, length = 30)
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(name = "end_time",nullable = false, length = 30)
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
//	@Column(name = "status", nullable = false)
//	public Integer getStatus() {
//		return status;
//	}
//
//	public void setStatus(Integer status) {
//		this.status = status;
//	}

	@Column(name = "num", nullable = false)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
}
