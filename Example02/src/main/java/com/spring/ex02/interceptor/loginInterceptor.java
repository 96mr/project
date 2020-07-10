package com.spring.ex02.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class loginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath(); //인터셉터 된경로
		String query = request.getQueryString(); //쿼리스트링
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("sessionID");
		
		if(obj == null) {
			 if(isAjaxRequest(request)){	//ajax인가?
                 response.sendError(500);
                 return false;
             }else {
            	if(query !=null) {
            		session.setAttribute("prev_url", path + "?" + query);
            	}
            	else {
            		session.setAttribute("prev_url", path );
            	}
            	response.sendRedirect("/login");
            	return false;
             }
		}
		
		return true;
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		 String header = request.getHeader("x-requested-with");
		 if(header == null) {
			 return false;
		 }
	     if (header.equals("XMLHttpRequest")){
	         return true;
	     }else{
	         return false;
	     }
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

}
