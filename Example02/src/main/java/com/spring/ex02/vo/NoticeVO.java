package com.spring.ex02.vo;

import java.util.Date;

public class NoticeVO {
	private int noteno;
	private int m_id;
	private int t_id;
	private String notetype;
	private int bno;
	private String content;
	private String noteurl;
	private Date regdate;
	private Date chkdate;
	private String del_chk;
	private MemberVO member;
	
	public NoticeVO() {
		// TODO Auto-generated constructor stub
	}
	
	public NoticeVO(int m_id, int t_id, String notetype) {
		this.m_id = m_id;
		this.t_id = t_id;
		this.notetype = notetype;
	}

	public int getNoteno() {
		return noteno;
	}
	public void setNoteno(int noteno) {
		this.noteno = noteno;
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
	public String getNotetype() {
		return notetype;
	}
	public void setNotetype(String notetype) {
		this.notetype = notetype;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNoteurl() {
		return noteurl;
	}

	public void setNoteurl(String noteurl) {
		this.noteurl = noteurl;
	}

	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Date getChkdate() {
		return chkdate;
	}
	public void setChkdate(Date chkdate) {
		this.chkdate = chkdate;
	}

	public String getDel_chk() {
		return del_chk;
	}

	public void setDel_chk(String del_chk) {
		this.del_chk = del_chk;
	}

	public MemberVO getMember() {
		return member;
	}

	public void setMember(MemberVO member) {
		this.member = member;
	}
}
