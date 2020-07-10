package com.spring.ex02.vo;

import java.util.Date;

public class LikeVO {
	private int bno;
	private int like_id;
	private Date regdate;
	
	public LikeVO() {
		
	}
	public LikeVO(int bno, int like_id) {
		this.bno = bno;
		this.like_id = like_id;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getLike_id() {
		return like_id;
	}
	public void setLike_id(int like_id) {
		this.like_id = like_id;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
}
