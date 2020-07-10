package com.spring.ex02.vo;

import java.util.Date;

public class MemberVO {
	private int user_no;
	private String id;
	private String password;
	private Date regdate;
	private String birth;
	private String phone;
	private String Email;
	private String privacy;
	private ProfileVO profile;
	
	//생성자
	public MemberVO() {
		
	}
	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public ProfileVO getProfile() {
		return profile;
	}
	public void setProfile(ProfileVO profile) {
		this.profile = profile;
	}
}
