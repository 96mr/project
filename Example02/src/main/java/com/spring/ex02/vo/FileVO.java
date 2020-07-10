package com.spring.ex02.vo;

import java.util.Date;

public class FileVO {
	private int fno;
	private String org_name;
	private String save_name;
	private int f_size;
	private Date regdate;
	private int reg_id;
	
	public int getFno() {
		return fno;
	}
	public void setFno(int fno) {
		this.fno = fno;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getSave_name() {
		return save_name;
	}
	public void setSave_name(String save_name) {
		this.save_name = save_name;
	}
	public int getF_size() {
		return f_size;
	}
	public void setF_size(int f_size) {
		this.f_size = f_size;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getReg_id() {
		return reg_id;
	}
	public void setReg_id(int reg_id) {
		this.reg_id = reg_id;
	}
	
	
}
