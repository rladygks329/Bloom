package com.edu.blooming.service;

import java.util.List;
import java.util.Map;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.domain.LessonVO;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.exception.AlreadyExistException;
import com.edu.blooming.util.PageCriteria;

public interface LectureService {
  int create(LectureVO vo, List<LessonVO> lessons);

  int update(LectureVO vo, List<LessonVO> lessons);

  int getTotalCountsByMemberName(String memberName);

  int getTotalCountsByKeyword(String keyword);

  MemberVO getInstructorInfo(int memberId);

  LectureVO read(int lectureId);

  List<LectureVO> read(PageCriteria criteria, String keyword, int orderType);

  List<LectureVO> readHotLikeLectures(int month, int rank);

  List<LectureVO> readHotSaleLectures(int month, int rank);

  /*
   * @key: likeStatus, cartStatus, purchaseStatus 좋아요 유무, 장바구니 유무, 결제 유무 판별
   */
  Map<String, Object> getUserStatus(int memberId, int lectureId);

  // -- lecture Like ---
  int likeLecture(int memberId, int lectureId) throws AlreadyExistException;

  int dislikeLecture(int memberId, int lectureId);

  boolean checkIsLike(int memberId, int lectureId);

}
