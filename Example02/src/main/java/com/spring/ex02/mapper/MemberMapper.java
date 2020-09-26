package com.spring.ex02.mapper;

import java.util.List;

import com.spring.ex02.vo.FileVO;
import com.spring.ex02.vo.MemberVO;
import com.spring.ex02.vo.ProfileVO;

public interface MemberMapper {
	public int insertMember(MemberVO vo) throws Exception;
	public void insertProfile(ProfileVO vo) throws Exception;
	public int insertProfileImageFile(FileVO vo) throws Exception;
	
	public MemberVO selectById(String id) throws Exception;
	public ProfileVO selectProfile(int user_no) throws Exception;
	public FileVO selectProfileImage(int user_no) throws Exception;
	public MemberVO selectProfileList(int no) throws Exception;
	
	public void updateProfile(ProfileVO profile) throws Exception;
	public void updateProfileImage(FileVO vo) throws Exception;
	public void updateMember(MemberVO vo) throws Exception;
	public void updatePassword(MemberVO vo) throws Exception;
	
	public List<String> searchByEmail(String email) throws Exception;
}
