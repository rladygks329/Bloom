package com.edu.blooming.persistence;

import com.edu.blooming.domain.MemberVO;

public interface MemberDAO {
  int insert(MemberVO vo); // 회원가입

  MemberVO login(MemberVO loginVo); // 로그인

  int checkEmail(String email); // 이메일 중복체크

  int checkNickname(String nickname);

  MemberVO select(String email); // 회원정보조회

  int update(String email, MemberVO vo); // 회원정보업데이트

  int delete(String email); // 회원탈퇴

  int updatePassword(int memberId, String memberPassword);
}
