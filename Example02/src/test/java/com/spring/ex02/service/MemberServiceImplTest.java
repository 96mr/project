package com.spring.ex02.service;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration( locations ={"classpath:*.xml", "file:WEB-INF/spring/**/*.xml"})
public class MemberServiceImplTest {
	@Inject
	private MemberService service;
	
	@Test
	public void testSelectById() throws Exception {
		System.out.println(service.selectById("hello"));
	}

}
