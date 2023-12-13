package com.edu.blooming.persistence;

import com.edu.blooming.domain.MemberVO;

public interface MemberDAO {
  int insert(MemberVO vo);

  MemberVO login(MemberVO loginVo);

  boolean checkEmail(String email);

  boolean checkNickname(String nickname);

  MemberVO selectInstructor(int memberId);

  int updatePassword(int memberId, String password);

  int updateNickname(int memberId, String nickname);

  int updateIntroduce(int memberId, String introduce);

  int updateProfileUrl(int memberId, String profileUrl);

}
