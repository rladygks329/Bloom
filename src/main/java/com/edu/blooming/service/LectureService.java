package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.util.PageCriteria;

public interface LectureService {
  int create(LectureVO vo);

  int update(LectureVO vo);

  List<LectureVO> read(PageCriteria criteria);

  List<LectureVO> findLectureByAuthorId(PageCriteria criteria, int authorId);

  LectureVO read(int lectureId);

  int likeLecture(int lectureId, int memberId);

  int dislikeLecture(int lectureId, int memberId);

  int getTotalCounts();

  int getTotalCounts(int authorId);
}
