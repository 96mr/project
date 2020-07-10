package com.spring.ex02.vo;

import java.util.Date;

public class FollowVO {
	private int m_id;
	private int t_id;
	private Date regdate;
	private MemberVO member;

	public FollowVO() {
	}
	public FollowVO(int m_id, int t_id) {
		this.m_id = m_id;
		this.t_id = t_id;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public MemberVO getMember() {
		return member;
	}
	public void setMember(MemberVO member) {
		this.member = member;
	}
	
	
	
}
