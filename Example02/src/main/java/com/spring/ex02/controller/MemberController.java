package com.spring.ex02.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.ex02.common.MemberValidator;
import com.spring.ex02.service.MemberService;
import com.spring.ex02.vo.MemberVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Resource(name="MemberService")
	private MemberService service;
	
	@InitBinder
	protected void initbinder(WebDataBinder binder) {
		// TODO Auto-generated method stub
		binder.addValidators(new MemberValidator());
	}
	
	
	@RequestMapping(value = "/join", method = RequestMethod.GET) //회원가입 폼
	public String join(Model model) {
		model.addAttribute("vo", new MemberVO());
		return "register";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@Valid @ModelAttribute("vo") MemberVO member, @RequestParam("pwChk") String pwChk, BindingResult result, HttpServletResponse response, RedirectAttributes rttr) throws Exception{
		if(result.hasErrors())
			return "register";
		
		if(!pwChk.equals(member.getPassword())) {
			rttr.addFlashAttribute("msg","비밀번호를 확인해주세요.");
			return "register";
		}
		
		int joinResult = service.memberJoin(member); // 받아온 회원 정보를 서비스로 넘겨줌
		if(joinResult == 1) {
			rttr.addFlashAttribute("msg", "성공적으로 가입되었습니다!");
			return "redirect:/login";
		}else {
			rttr.addFlashAttribute("msg","가입에 실패하였습니다.");
			return "register";
		}
	}
	
	/*아이디, 패스워드 중복, 유효성 검사 ajax*/
	@ResponseBody
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public int idCheck(@RequestParam("id") String id) throws Exception {
		MemberVO vo = service.selectById(id);
		String idReg = "^[a-z0-9]{4,20}$";
		Pattern p = Pattern.compile(idReg);
		Matcher m = p.matcher(id);
		if(!m.matches())	//정규식 x
			return 0;
		
		if(vo == null)	//사용가능(아이디 중복x)
			return 1;
		else
			return -1;	//사용불가능(아이디 중복o)
	}
	
	@ResponseBody
	@RequestMapping(value = "/pwCheck", method = RequestMethod.POST)
	public int pwCheck(@RequestParam("pw") String pw) throws Exception {
		String pwReg = "^[a-z0-9]{8,30}$";
		Pattern p = Pattern.compile(pwReg);
		Matcher m = p.matcher(pw);
		if(!m.matches())	//정규식 x
			return 0;
		return 1;
	}
	
	
	/*로그인 부분*/
	@RequestMapping(value = "/login", method = RequestMethod.GET) //로그인 페이지 이동
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST) //로그인 시도
	public String login(@ModelAttribute("member") MemberVO member,HttpSession session, RedirectAttributes rttr) throws Exception {
		if((String)session.getAttribute("sessionID") != null) {
			return "home";
		}
		
		int result = service.memberLogin(member); //1: 성공, -1 : 실패

		// 로그인 성공시
		if(result == 1) {
			session.setAttribute("sessionID", member.getId());	
			String prev_url = (String) session.getAttribute("prev_url"); //이전 페이지가 있는가?(인터셉터)
			if(prev_url == null) prev_url = "/";						 //없다면 home으로
			return "redirect:"+prev_url;
		}
		//로그인 실패시
		else {
			rttr.addFlashAttribute("msg","아이디 또는 비밀번호가 일치하지 않습니다.");
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, Model model) throws Exception {
		session.invalidate();
		return "redirect:/";
	}
	
	
	@RequestMapping(value = "/settings", method = RequestMethod.GET) //로그인 페이지 이동
	public String settings() throws Exception {
		return "settings/settings";
	}
	
	@RequestMapping(value = "/settings/account", method = RequestMethod.GET) //로그인 페이지 이동
	public String edit_account(HttpSession session, Model model) throws Exception {
		String id = (String) session.getAttribute("sessionID");
		MemberVO vo = service.selectById(id);
		vo.setPassword(null);
		model.addAttribute("vo", vo);
		return "settings/edit_account";
	}
	
	@RequestMapping(value = "/settings/account", method = RequestMethod.POST) //로그인 페이지 이동
	public String edit_account(@Valid @ModelAttribute("vo") MemberVO member, BindingResult result, 
									HttpSession session, RedirectAttributes rttr) throws Exception {
		if(result.hasErrors()) {
			return "settings/edit_account";
		}
		
		String user = (String) session.getAttribute("sessionID");	
		int joinResult = service.updateMember(member, user); //회원정보수정
		
		if(joinResult == 1) {
			if(!user.equals(member.getId())) {
				session.setAttribute("sessionID", member.getId());  //세션 아이디 변경
			}
			rttr.addFlashAttribute("msg", "회원정보가 수정되었습니다!");
			return "redirect:/settings";
		}else {
			rttr.addFlashAttribute("msg","회원정보 수정에 실패하였습니다.");
			return "settings/edit_account";
		}
	}
	
	@RequestMapping(value = "/settings/password", method = RequestMethod.GET) //로그인 페이지 이동
	public String edit_password(HttpSession session, Model model) throws Exception {
		return "settings/edit_password";
	}
	
	@RequestMapping(value = "/settings/password", method = RequestMethod.POST) //로그인 페이지 이동
	public String edit_password(@RequestParam("pw") String pw,@RequestParam("new_pw") String new_pw, @RequestParam("new_pwChk") String pwChk, 
								HttpSession session, RedirectAttributes rttr) throws Exception {
	
		String user = (String) session.getAttribute("sessionID");
			
		String pwReg = "^[a-z0-9]{8,30}$";
		Pattern p = Pattern.compile(pwReg);
		Matcher m = p.matcher(new_pw);
		if(!m.matches() || !new_pw.equals(pwChk)) {	     //새 비밀번호 정규식 x
			return "settings/edit_password";
		}

		int joinResult = service.updatePassword(user, pw, new_pw);
		
		if(joinResult == 1) {
			rttr.addFlashAttribute("msg", "비밀번호가 변경되었습니다!");
			return "redirect:/settings";
		}else {
			return "settings/edit_password";
		}
	}
}
