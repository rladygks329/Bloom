package com.edu.blooming.service;

import java.util.Map;
import com.edu.blooming.domain.MemberVO;

public interface MemberService {

  int register(MemberVO vo);

  // 이메일 중복검사
  boolean checkEmail(String email);

  boolean checkNickname(String nickname);

  public MemberVO login(MemberVO member);

  int updatePassword(int memberId, String password);

  int updateNickname(int memberId, String nickname);

  int updateProfileUrl(int memberId, String profileUrl);

  int updateIntroduce(int memberId, String introduce);

  int deleteProfileUrl(int memberId);

  Map<String, Object> getInstuctorStatus(int memberId);

}

