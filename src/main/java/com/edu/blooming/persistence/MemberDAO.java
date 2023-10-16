package com.edu.blooming.persistence;

import com.edu.blooming.domain.MemberVO;

public interface MemberDAO {
	public abstract int insert(MemberVO vo);	// 회원가입
	public abstract MemberVO select(String memberEmail);	// 회원정보조회
	public abstract int update(String memberEmail, MemberVO vo);	// 회원정보업데이트
	public abstract int delete(String memberEmail);	// 회원탈퇴
	public abstract String select(String memberEmail, String memberPassword);	// 로그인
	// 중복검사 등 제약조건
}
