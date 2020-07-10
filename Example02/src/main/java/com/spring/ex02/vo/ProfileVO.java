package com.spring.ex02.vo;

public class ProfileVO {
	private int user_no;
	private String name;
	private int image;
	private String introduce;
	private FileVO image_file;
	
	public ProfileVO() {
		
	}
	public ProfileVO(int user_no, String name, int image, String introduce) {
		this.user_no = user_no;
		this.name = name;
		this.image = image;
		this.introduce = introduce;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public FileVO getImage_file() {
		return image_file;
	}
	public void setImage_file(FileVO image_file) {
		this.image_file = image_file;
	}
	
}
