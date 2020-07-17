package com.spring.ex02.vo;

import java.util.List;

public class BoardVO {
	private int bno;
	private String content;//1000
	private int writer_id; //fk : member의 user_id
	private String regdate;
	private int hit;
	private String del_chk;
	private List<FileVO> files;
	private MemberVO member;
	private List<ReplyVO> reply;
	private List<MemberVO> liker_list;
	private int islike;
	
	public MemberVO getMember() {
		return member;
	}
	public void setMember(MemberVO member) {
		this.member = member;
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
	public int getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(int writer_id) {
		this.writer_id = writer_id;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getDel_chk() {
		return del_chk;
	}
	public void setDel_chk(String del_chk) {
		this.del_chk = del_chk;
	}
	public List<FileVO> getFiles() {
		return files;
	}
	public void setFiles(List<FileVO> files) {
		this.files = files;
	}
	public List<MemberVO> getLiker_list() {
		return liker_list;
	}	
	public List<ReplyVO> getReply() {
		return reply;
	}
	public void setReply(List<ReplyVO> reply) {
		this.reply = reply;
	}
	public void setLiker_list(List<MemberVO> liker_list) {
		this.liker_list = liker_list;
	}
	public int getIslike() {
		return islike;
	}
	public void setIslike(int islike) {
		this.islike = islike;
	}
}
