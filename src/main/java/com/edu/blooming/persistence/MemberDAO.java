package com.edu.blooming.persistence;

import com.edu.blooming.domain.MemberVO;

public interface MemberDAO {
  public abstract int insert(MemberVO vo); // 회원가입

  public abstract MemberVO login(MemberVO loginVo); // 로그인

  public abstract int checkEmail(String email); // 이메일 중복체크

  public abstract MemberVO select(String email); // 회원정보조회

  public abstract int update(String email, MemberVO vo); // 회원정보업데이트

  public abstract int delete(String email); // 회원탈퇴
}
