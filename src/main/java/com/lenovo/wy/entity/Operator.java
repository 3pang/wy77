package com.lenovo.wy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ww_operator")
public class Operator implements Serializable {
	@Id
	@Column(name="oper_id")
	private int oper_id;
	@Column(name="oper_name")
	private String oper_name;
	@Column(name="oper_addr")
	private String oper_addr;
	@Column(name="oper_ext")
	private int oper_ext;
	public int getOper_id() {
		return oper_id;
	}
	public void setOper_id(int oper_id) {
		this.oper_id = oper_id;
	}
	public String getOper_name() {
		return oper_name;
	}
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	public String getOper_addr() {
		return oper_addr;
	}
	public void setOper_addr(String oper_addr) {
		this.oper_addr = oper_addr;
	}
	public int getOper_ext() {
		return oper_ext;
	}
	public void setOper_ext(int oper_ext) {
		this.oper_ext = oper_ext;
	}

}
