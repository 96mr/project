package com.spring.ex02.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.ex02.vo.FileVO;
import com.spring.ex02.vo.MemberVO;
import com.spring.ex02.vo.ProfileVO;

@Repository
public class MemberDaoImpl implements MemberDao{
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace="com.spring.ex02.mapper.MemberMapper";
	
	@Override
	public MemberVO selectById(String id) throws Exception{
		return sqlSession.selectOne(namespace+".selectById",id);
	}
	@Override
	public MemberVO ProfileList(int no) throws Exception{
		return sqlSession.selectOne(namespace+".ProfileList",no);
	}
	@Override
	public int insertMember(MemberVO member) throws Exception{
		sqlSession.insert(namespace+".insertMember", member);
		return member.getUser_no();
	}
	
	@Override
	public void insertProfile(ProfileVO vo) throws Exception {
		sqlSession.insert(namespace+".insertProfile", vo);
	}
	
	@Override
	public ProfileVO selectProfile(int user_no) throws Exception {
		return sqlSession.selectOne(namespace+".selectProfile", user_no);
	}
	@Override
	public void updateProfile(ProfileVO profile) throws Exception{
		sqlSession.update(namespace+".updateProfile", profile);
	}
	@Override
	public FileVO selectProfileImage(int user_no) throws Exception {
		return sqlSession.selectOne(namespace+".selectProfileImage", user_no);
	}
	@Override
	public int insertProfileImageFile(FileVO vo) throws Exception {
		sqlSession.insert(namespace+".insertProfileImageFile", vo);
		return vo.getFno();
	}
	@Override
	public void updateProfileImage(FileVO vo) throws Exception {
		sqlSession.update(namespace+".updateProfileImage", vo);
	}
	@Override
	public void updateMember(MemberVO member) throws Exception {
		sqlSession.update(namespace+".updateMember", member);
	}
	@Override
	public void updatePassword(MemberVO member) throws Exception {
		sqlSession.update(namespace+".updatePassword", member);
	}
	
}
