package com.spring.ex02.service;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.spring.ex02.vo.MemberVO;
import com.spring.ex02.vo.ProfileVO;

public interface MemberService {
	public int memberLogin(MemberVO member) throws Exception; //로그인
	public int memberJoin(MemberVO member) throws Exception; //회원가입
	public MemberVO selectById(String id) throws Exception; //id 로 멤버 정보 가져오기
	public ProfileVO selectProfile(String id) throws Exception; //프로필 가져오기
	public int updateProfile(ProfileVO profile, MultipartFile[] file) throws Exception; //프로필업데이트
	public int editMember(MemberVO member, String user) throws Exception;
	public int editPassword(String id, String pw, String new_pw) throws Exception;
}
