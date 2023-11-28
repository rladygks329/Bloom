package com.edu.blooming.service;

import java.util.Map;
import com.edu.blooming.domain.MemberVO;

public interface MemberService {

  int register(MemberVO vo);

  // 이메일 중복검사
  int checkEmail(String email);

  int checkNickname(String nickname);

  public MemberVO login(MemberVO member);

  int updatePassword(int memberId, String memberPassword);

  int updateNickname(int memberId, String memberNickname);

  int updateIntroduce(int memberId, String memberIntroduce);

  Map<String, Object> getInstuctorStatus(int memberId);

}

