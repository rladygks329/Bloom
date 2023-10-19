package com.edu.blooming.persistence;

import com.edu.blooming.domain.MemberVO;

public interface MemberDAO {
	public abstract int insert(MemberVO vo);	// 회원가입
	
	public MemberVO memberLogin(MemberVO vo);	// 로그인

	public abstract int emailCheck(String memberEmail);	// 이메일 중복체크
	public abstract MemberVO select(String memberEmail);	// 회원정보조회
	public abstract int update(String memberEmail, MemberVO vo);	// 회원정보업데이트
	public abstract int delete(String memberEmail);	// 회원탈퇴
	// 중복검사 등 제약조건
}
