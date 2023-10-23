package com.edu.blooming.service;

import javax.servlet.http.HttpSession;

import com.edu.blooming.domain.MemberVO;

// CRUD(Create, Read, Update, Delete)
public interface MemberService {
	int create(MemberVO vo);
	int emailCheck(String memberEmail) throws Exception;
	public MemberVO memberLogin(MemberVO vo) throws Exception;	// 로그인
	
	public void logout(HttpSession session);

} // end MemberService
