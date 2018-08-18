package com.sjq.zmkm.dao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.sjq.zmkm.exception.EnumValue;

@DynamicInsert(true)
@DynamicUpdate(true)
@Entity
@Table(name = "card",uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Card implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer pkid;

    private String name;    //门卡名字,或者地区名字

    private Integer parentId;	//父id
    
    @EnumValue(value="area,card",defaultValue="card")
    public Integer type;    //0地区,1门卡
    //枚举对象 瞬态值
    public enum TypeEnum{AREA,CARD};

    private String macAddress;	//门卡 物理地址
    
    private String openCmd;	//开门指令

    private Integer status;	//门卡状态 0启用 1禁用
    
    public enum StatusEnum{ENABLE, DISABLE};
    
    private String createTime;
    
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
	@Column(name = "parentId", nullable = false)
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	@Column(name = "type", nullable = false)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "macAddress", nullable = false, length = 64)
	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
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
	@Column(name = "open_cmd", nullable = false, length = 64)
	public String getOpenCmd() {
		return openCmd;
	}

	public void setOpenCmd(String openCmd) {
		this.openCmd = openCmd;
	}
}