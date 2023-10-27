package com.edu.blooming.service;

import javax.servlet.http.HttpSession;

import com.edu.blooming.domain.MemberVO;

// CRUD(Create, Read, Update, Delete)
public interface MemberService {
	
	int create(MemberVO vo);	// 회원가입
	int emailCheck(String memberEmail) throws Exception;	// 이메일 중복검사
	public MemberVO memberLogin(MemberVO loginVo) throws Exception;	// 로그인	
	public void logout(HttpSession session);	// 로그아웃

} // end MemberService
