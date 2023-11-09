package com.edu.blooming.service;

import javax.servlet.http.HttpSession;
import com.edu.blooming.domain.MemberVO;

public interface MemberService {

  int create(MemberVO vo); // 회원가입

  int checkEmail(String email) throws Exception; // 이메일 중복검사

  int checkNickname(String nickname) throws Exception;

  public MemberVO login(MemberVO member) throws Exception; // 로그인

  public void logout(HttpSession session); // 로그아웃

  int updatePassword(int memberId, String memberPassword);

} // end MemberService

