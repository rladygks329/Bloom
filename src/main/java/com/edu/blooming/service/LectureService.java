package com.edu.blooming.service;

import java.util.List;
import java.util.Map;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.domain.LessonVO;
import com.edu.blooming.exception.AlreadyExistException;
import com.edu.blooming.util.PageCriteria;

public interface LectureService {
  int create(LectureVO vo, List<LessonVO> lessons);

  int update(LectureVO vo);

  List<LectureVO> read(PageCriteria criteria, int orderType);

  List<LectureVO> read(PageCriteria criteria, String keyword, int orderType);

  List<LectureVO> readHotLikeLectures(int month, int rank);

  List<LectureVO> readHotSaleLectures(int month, int rank);

  List<LectureVO> findLectureByAuthorId(PageCriteria criteria, int authorId);

  LectureVO read(int lectureId);

  /*
   * @key: isPurchase, isLike, isCart
   */
  Map<String, Object> getUserStatus(int memberId, int lectureId);

  boolean checkIsLike(int memberId, int lectureId);

  int likeLecture(int memberId, int lectureId) throws AlreadyExistException;

  int dislikeLecture(int memberId, int lectureId);

  int getTotalCounts();

  int getTotalCounts(int authorId);

  int getTotalCounts(String keyword);
}
