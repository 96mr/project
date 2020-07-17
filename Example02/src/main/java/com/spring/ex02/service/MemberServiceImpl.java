package com.spring.ex02.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.ex02.common.FileUtils;
import com.spring.ex02.common.MailHandler;
import com.spring.ex02.common.Tempkey;
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
	
	@Inject
	private JavaMailSender mailSender;
	
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
		MemberVO result = new MemberVO();
		result = dao.selectById(id);
		return result;
	}
	
	@Override
	@Transactional
	public ProfileVO selectProfile(String id) throws Exception {
		ProfileVO result = new ProfileVO();
		MemberVO member = dao.selectById(id);
		if(member!= null) {
			int no = member.getUser_no();
			result =  dao.selectProfile(no);
		}
		return result;
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
		if(passwordEncoder.matches(member.getPassword(), vo.getPassword())) {
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

	@Override
	public List<String> idFindByEmail(String email) throws Exception {
		List<String> result = new ArrayList<String>();
		int index = 0;
		for(String id:dao.searchByEmail(email)){
			char[] str = id.toCharArray();
			for(int i = 3 ; i < str.length;i++) {
				str[i] = '*';
			}
			result.add(index, new String(str));	
			index++;
		}
	
		return result;
	}

	@Override
	@Transactional
	public int sendRandomPW(String how, String where, String who) throws Exception {
		if(who == null) {
			return 0;
		}
		if(how.equals("email")) {
			MemberVO user = dao.selectById(who);
			String key = new Tempkey().getKey(10,false);
			user.setPassword(passwordEncoder.encode(key));
			dao.updatePassword(user);
			MailHandler sendMail = new MailHandler(mailSender);
			sendMail.setSubject("test)임시비밀번호가 발급되었습니다");
			sendMail.setText(new StringBuffer().append("<h1>[회원님의 임시 비밀번호입니다]</h1>")
					.append("<p>임시 발급된 비밀번호는 <b>")
					.append(key)
					.append("</b> 입니다. 임시비밀번호로 로그인하여 비밀번호를 변경해주시기 바랍니다. </p>").toString());
			sendMail.setFrom("exmerang@gmail.com", "me rang");
			sendMail.setTo(where);
			sendMail.send();
			
			return 1;
		}else if(how.equals("phone")){
			return 1;
		}
		return 0;
	}
	
	
	
}
