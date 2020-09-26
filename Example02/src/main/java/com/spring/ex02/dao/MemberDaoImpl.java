package com.spring.ex02.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.ex02.mapper.MemberMapper;
import com.spring.ex02.vo.FileVO;
import com.spring.ex02.vo.MemberVO;
import com.spring.ex02.vo.ProfileVO;

@Repository
public class MemberDaoImpl implements MemberDao{
	@Autowired
	private MemberMapper mapper;

	@Override
	public int insertMember(MemberVO member) throws Exception{
		mapper.insertMember(member);
		return member.getUser_no();
	}
	@Override
	public void insertProfile(ProfileVO vo) throws Exception {
		mapper.insertProfile(vo);
	}
	@Override
	public int insertProfileImageFile(FileVO vo) throws Exception {
		mapper.insertProfileImageFile(vo);
		return vo.getFno();
	}
	@Override
	public MemberVO selectById(String id) throws Exception{
		return mapper.selectById(id);
	}
	@Override
	public MemberVO selectProfileList(int no) throws Exception{
		return mapper.selectProfileList(no);
	}
	@Override
	public ProfileVO selectProfile(int user_no) throws Exception {
		return mapper.selectProfile(user_no);
	}
	@Override
	public void updateProfile(ProfileVO profile) throws Exception{
		mapper.updateProfile(profile);
	}
	@Override
	public FileVO selectProfileImage(int user_no) throws Exception {
		return mapper.selectProfileImage(user_no);
	}
	@Override
	public void updateProfileImage(FileVO vo) throws Exception {
		mapper.updateProfileImage(vo);
	}
	@Override
	public void updateMember(MemberVO member) throws Exception {
		mapper.updateMember(member);
	}
	@Override
	public void updatePassword(MemberVO member) throws Exception {
		mapper.updatePassword(member);
	}
	@Override
	public List<String> searchByEmail(String email) throws Exception {
		return mapper.searchByEmail(email);
	}
}
