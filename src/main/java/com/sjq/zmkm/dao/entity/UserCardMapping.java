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
@Table(name = "user_card_mapping", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"card_id","user_id"})})

public class UserCardMapping {
	
	private Integer pkid;
	
	private Integer cardId;
	
	private Card card;
	
	private Integer userId;
	
	private User user;
	
	//无意义 如果知道用户住几年 还可以自动失效，不知道的话还是业主自己移除
//	private String createTime;	//起效时间   当前时间
//	
//	private String endTime;		//结束时间 两年
	
	//private Integer status;		//有效无效，两年后失效
	@Id
	@Column(name = "pkid", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getPkid() {
		return pkid;
	}

	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	
	@Column(name = "card_id", nullable = false)
	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	@ManyToOne
	@JoinColumn(name = "card_id", referencedColumnName = "pkid", insertable = false, updatable = false)
	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
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
//	@Column(name = "create_time",nullable = false, length = Constant.DB.STRING_LEN_30)
//	public String getCreateTime() {
//		return createTime;
//	}

//	public void setCreateTime(String createTime) {
//		this.createTime = createTime;
//	}
//	@Column(name = "end_time",nullable = false, length = Constant.DB.STRING_LEN_30)
//	public String getEndTime() {
//		return endTime;
//	}
//
//	public void setEndTime(String endTime) {
//		this.endTime = endTime;
//	}
//	@Column(name = "status", nullable = false)
//	public Integer getStatus() {
//		return status;
//	}
//
//	public void setStatus(Integer status) {
//		this.status = status;
//	}
}
