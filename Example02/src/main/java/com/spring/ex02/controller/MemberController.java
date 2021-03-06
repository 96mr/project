package com.spring.ex02.controller;

import java.util.List;
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
	
	@Resource(name="MemberService") private MemberService service;
	
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
	public String join(@Valid @ModelAttribute("vo") MemberVO member, BindingResult result,  
						Model model, RedirectAttributes rttr) throws Exception{
		logger.info("join : ");
		
		if(result.hasErrors()) {	//validate
			return "register"; 
		}
		
		if(!member.getPw_chk().equals(member.getPassword())) {	//비밀번호 체크 일치여부 확인
			model.addAttribute("fail_msg", "비밀번호가 일치하지 않습니다");
			return "register";
		}
		
		int joinResult = service.memberJoin(member); // 받아온 회원 정보를 서비스로 넘김
		if(joinResult == 1) {
			rttr.addFlashAttribute("msg", "성공적으로 가입되었습니다!");
			return "redirect:/login";
		}else {
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
	public String login(HttpSession session) {
		if((String)session.getAttribute("sessionID") != null) {
			return "redirect:/";
		}
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST) //로그인 시도
	public String login(@ModelAttribute("member") MemberVO member,
					   HttpSession session, HttpServletResponse response, RedirectAttributes rttr) throws Exception {
		logger.info("login: " + member.toString());
		if((String)session.getAttribute("sessionID") != null) {
			return "redirect:/";
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
	
	
	@RequestMapping(value = "/settings", method = RequestMethod.GET) 
	public String settings() throws Exception {
		return "settings/settings";
	}
	
	@RequestMapping(value = "/settings/account", method = RequestMethod.GET) 
	public String edit_account(HttpSession session, Model model) throws Exception {
		String id = (String) session.getAttribute("sessionID");
		MemberVO vo = service.selectById(id);
		vo.setPassword(null);
		model.addAttribute("vo", vo);
		return "settings/edit_account";
	}
	
	@RequestMapping(value = "/settings/account", method = RequestMethod.POST) 
	public String edit_account(@Valid @ModelAttribute("vo") MemberVO member, BindingResult result, 
									HttpSession session, RedirectAttributes rttr, Model model) throws Exception {
		logger.info("edit account :"+ member.toString());
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
			model.addAttribute("fail_msg", "비밀번호를 확인해주세요");
			return "settings/edit_account";
		}
	}
	
	@RequestMapping(value = "/settings/password", method = RequestMethod.GET) 
	public String edit_password(HttpSession session, Model model) throws Exception {
		return "settings/edit_password";
	}
	
	@RequestMapping(value = "/settings/password", method = RequestMethod.POST)
	public String edit_password(@RequestParam("pw") String pw,@RequestParam("new_pw") String new_pw, @RequestParam("new_pwChk") String pwChk, 
								HttpSession session, Model model, RedirectAttributes rttr) throws Exception {
		logger.info("edit password");
		
		String user = (String) session.getAttribute("sessionID"); //현재 로그인한 회원 아이디
			
		String pwReg = "^[a-z0-9]{8,30}$";
		Pattern p = Pattern.compile(pwReg);
		Matcher m = p.matcher(new_pw);
		if(!m.matches()) {	     //새 비밀번호 정규식 x
			model.addAttribute("fail_msg", "새 비밀번호 형식이 올바르지 않습니다");
			return "settings/edit_password";
		}	
		if(!new_pw.equals(pwChk)) {
			model.addAttribute("fail_msg", "새 비밀번호 확인이 일치하지 않습니다");
			return "settings/edit_password";
		}

		int joinResult = service.updatePassword(user, pw, new_pw);
		
		if(joinResult == 1) {
			rttr.addFlashAttribute("msg", "비밀번호가 변경되었습니다!");
			return "redirect:/settings";
		}else {
			model.addAttribute("fail_msg", "비밀번호가 일치하지 않습니다");
			return "settings/edit_password";
		}
	}
	
	@RequestMapping(value = "/find/id", method = RequestMethod.GET) 
	public String find_id(Model model) throws Exception {
		logger.info("find id get");
		return "find_id";
	}
	
	@RequestMapping(value = "/find/id", method = RequestMethod.POST) 
	public String find_id(@RequestParam(value="email",required=false) String email, Model model) throws Exception {
		logger.info("find id post");
		
		List<String> find_result = service.idFindByEmail(email);
		model.addAttribute("result_count", find_result.size())
			 .addAttribute("result", find_result);
		return "find_id_result";
	}
	
	@RequestMapping(value = "/find/password", method = RequestMethod.GET) 
	public String find_pw(Model model) throws Exception {
		logger.info("find pw get");
		return "find_password";
	}
	
	@RequestMapping(value = "/find/password", method = RequestMethod.POST) 
	public String find_pw(@RequestParam(value="id",required=false) String id, 
						  Model model) throws Exception {
		logger.info("find pw post id :" + id);
		MemberVO vo = service.selectById(id);
		if(vo == null) {
			model.addAttribute("send_failed", "존재하지 않는 아이디입니다!");
			return "find_password";
		}
		
		model.addAttribute("to_email", vo.getEmail())
			 .addAttribute("to_phone", vo.getPhone())
			 .addAttribute("user_id", id);
		return "find_password_how";
	}
	
	@RequestMapping(value = "/find/password/how", method = RequestMethod.POST) 
	public String find_pw_how(@RequestParam("how") String how, @RequestParam("where") String where,
							  @RequestParam("user_id") String who,Model model) throws Exception {
		logger.info("find pw how :"+how +", where :"+ where +", who :"+ who);
		int result = service.sendRandomPW(how, where, who); // 임시 비밀번호 전송
		
		if(result == 1) {
			return "find_password_result";
		}else {
			return "find/password/how";
		}
	}
}
