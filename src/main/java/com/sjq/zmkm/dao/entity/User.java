package com.sjq.zmkm.dao.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.sjq.zmkm.controller.resDto.VisitorsCardDto;

@DynamicInsert(true)
@DynamicUpdate(true)
@Entity
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(columnNames = "phone")})
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer pkid;

    private String name;

    private Integer type;	//用户类型 0  住户租户 1访客

    public enum TypeEnum{ZHUHU,FANGKE};
    
    private String phone;

    private Integer sex;
    
    public enum SexEnum{MALE,FEMALE,UNKNOW};

    private String image;

    private Integer status;	
    
    public enum StatusEnum{ENABLE, DISABLE};
    
    private String createTime;
   
    //瞬态 改为dto实现 不在采用瞬态属性
    private List<Card> cards;
    
    //瞬态
    private List<VisitorsCardDto> visitorsCards;
    //爱好标签
    @Id
    @Column(name = "pkid", unique = true, nullable = false, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getPkid() {
		return pkid;
	}

	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}

	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "type", nullable = false)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "phone", nullable = false, length = 30)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "sex", nullable = false)
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	@Column(name = "image", nullable = true, length = 30)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "create_time",  nullable = false, length = 30)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Transient
	public List<Card> getCards() {
		return cards;
	}
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	@Transient
	public List<VisitorsCardDto> getVisitorsCards() {
		return visitorsCards;
	}

	public void setVisitorsCards(List<VisitorsCardDto> visitorsCards) {
		this.visitorsCards = visitorsCards;
	}
}