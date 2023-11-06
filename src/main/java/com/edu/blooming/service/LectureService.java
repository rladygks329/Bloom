package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.domain.LessonVO;
import com.edu.blooming.exception.AlreadyExistException;
import com.edu.blooming.util.PageCriteria;

public interface LectureService {
  int create(LectureVO vo, List<LessonVO> lessons);

  int update(LectureVO vo);

  List<LectureVO> read(PageCriteria criteria);

  List<LectureVO> read(PageCriteria criteria, String keyword);

  List<LectureVO> findLectureByAuthorId(PageCriteria criteria, int authorId);

  LectureVO read(int lectureId);

  boolean checkIsLike(int memberId, int lectureId);

  int likeLecture(int lectureId, int memberId) throws AlreadyExistException;

  int dislikeLecture(int lectureId, int memberId);

  int getTotalCounts();

  int getTotalCounts(int authorId);

  int getTotalCounts(String keyword);
}
