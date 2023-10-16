package com.edu.blooming.persistence;

import com.edu.blooming.domain.MemberVO;

public interface MemberDAO {
	public abstract int insert(MemberVO vo);	// ȸ������
	public abstract MemberVO select(String memberEmail);	// ȸ��������ȸ
	public abstract int update(String memberEmail, MemberVO vo);	// ȸ������������Ʈ
	public abstract int delete(String memberEmail);	// ȸ��Ż��
	public abstract String select(String memberEmail, String memberPassword);	// �α���
	// �ߺ��˻� �� ��������
}
