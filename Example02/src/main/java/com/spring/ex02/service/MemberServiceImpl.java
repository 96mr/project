package com.spring.ex02.service;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.ex02.common.FileUtils;
import com.spring.ex02.dao.MemberDao;
import com.spring.ex02.vo.FileVO;
import com.spring.ex02.vo.MemberVO;
import com.spring.ex02.vo.ProfileVO;

@Service("MemberService")
public class MemberServiceImpl implements MemberService {
	@Inject
	private MemberDao dao;
	
	@Inject
	BCryptPasswordEncoder passwordEncoder;
	
	@Resource(name="fileUtils")
	private FileUtils fileutils;
	//로그인
	@Override
	public int memberLogin(MemberVO member) throws Exception{
		MemberVO vo = dao.selectById(member.getId());
		int result = 0;
		if(vo!=null) {	//아이디 존재
			if(passwordEncoder.matches(member.getPassword(), vo.getPassword())) { //비밀번호 일치
				result = 1;
			}else {	
				result = -1; //불일치
			}
		}else {
			result = -1;
		}
		return result;
	}

	//회원가입
	@Override
	@Transactional
	public int memberJoin(MemberVO member) throws Exception {
		String id = member.getId();
		MemberVO vo = dao.selectById(id);
		if(vo == null) {
			member.setPassword(passwordEncoder.encode(member.getPassword()));
			int user_no = dao.insertMember(member); //회원등록
			
			FileVO fvo = new FileVO();
			fvo.setReg_id(user_no);
			
			int fno = dao.insertProfileImageFile(fvo);	//회원 프로필 파일번호
			
			ProfileVO pvo = new ProfileVO(user_no, member.getId(), fno,"");
			dao.insertProfile(pvo);	//회원프로필
			return 1;	//가입 성공
		}else {
			return 0; //가입 실패
		}
	}

	//id로 회원정보
	@Override
	public MemberVO selectById(String id) throws Exception{
		return dao.selectById(id);
	}
	
	@Override
	@Transactional
	public ProfileVO selectProfile(String id) throws Exception {
		int no = dao.selectById(id).getUser_no();
		return dao.selectProfile(no);
	}
	//
	@Override
	@Transactional
	public int updateProfile(ProfileVO profile, MultipartFile[] file) throws Exception{
		int user_no = profile.getUser_no();
		String name = profile.getName();
		
		if(name!=null && name.trim().length() != 0) { //이름이 비어있지 않은가?
			dao.updateProfile(profile);
			List<FileVO> vo = fileutils.parseFileInfo(user_no, file);
			for(int i = 0 ; i < vo.size();i++) {
				FileVO fvo = vo.get(i);
				fvo.setFno(dao.selectProfile(user_no).getImage()); //프로필 파일 번호
				dao.updateProfileImage(fvo);
			}
			return 1;
		}else {
			return 0;	
		}
	}

	@Override
	@Transactional
	public int updateMember(MemberVO member, String user) throws Exception {
		MemberVO vo = dao.selectById(user);
		if(member.getPassword().equals(vo.getPassword())) {
			member.setUser_no(vo.getUser_no());
			dao.updateMember(member);
			return 1;
		}else {
			return 0; // 현재 비밀번호가 일치하지 않음
		}
		
	}

	@Override
	@Transactional
	public int updatePassword(String id,String pw, String new_pw) throws Exception {
		MemberVO user = dao.selectById(id);
		
		if(passwordEncoder.matches(pw, user.getPassword())) {
			new_pw = passwordEncoder.encode(new_pw); //새 비밀번호 암호화
			user.setPassword(new_pw);
			dao.updatePassword(user);
			return 1;
		}else {
			return 0; // 현재 비밀번호가 일치하지 않음
		}
	}
	
	
}
