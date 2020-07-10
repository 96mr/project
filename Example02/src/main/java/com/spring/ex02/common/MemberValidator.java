package com.spring.ex02.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.ex02.vo.MemberVO;

@Component
public class MemberValidator implements Validator {

	private static final String idRegExp = "^[a-zA-z0-9]{4,20}$";
	private static final String pwRegExp = "^[a-zA-z0-9]{8,30}$";
//	private static final String emailRegExp ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
 //           									"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private Pattern pattern = null;
	private Matcher matcher = null;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return MemberVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		MemberVO vo = (MemberVO) target;	//받아온 것의 정규표현식을 확인하기 위해 vo객체에 넣음
		String id = vo.getId();				// id의 유효성 검사를 위한 변수
		String pw = vo.getPassword();		// pw 유효성 검사 위한 변수

		if(id == null || id.trim().isEmpty()) {		//id 값이 null이거나 공백일때
			errors.rejectValue("id", "required", "아이디를 입력해주세요");	
		}else {
			pattern = Pattern.compile(idRegExp);
			matcher = pattern.matcher(id);
			if(!matcher.matches())
				errors.rejectValue("id", "bad", "비밀번호는 영문자와 숫자로만 이루어져야 합니다(4~20자)");
		}
		if(pw == null || pw.trim().isEmpty())	//pw null이거나 공백일때
			errors.rejectValue("password", "required", "비밀번호를 입력해주세요");
		else {
			pattern = Pattern.compile(pwRegExp);
			matcher = pattern.matcher(pw);
			if(!matcher.matches())
				errors.rejectValue("password", "bad", "비밀번호는 영문자와 숫자로만 이루어져야 합니다(8~30자)");
		}
		
	}

}
