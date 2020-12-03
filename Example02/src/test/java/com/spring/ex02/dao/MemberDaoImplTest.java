package com.spring.ex02.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.ex02.vo.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration( locations ={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MemberDaoImplTest {
	@Autowired
	MemberDao dao;
	
	public MemberDaoImplTest() {
	}

	@Test 
	public void testSelectById() throws Exception{
		MemberVO vo = dao.selectById("ex123");
		System.out.println(vo);
	}
	
}
