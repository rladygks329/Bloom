package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.LessonVO;

public interface LessonService {
  int create(LessonVO vo);

  int update(LessonVO vo);

  List<LessonVO> getByLectureId(int lectureId);

  LessonVO getByLessonId(int lessonId);
}
