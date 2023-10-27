package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.LectureReplyVO;

public interface LectureReplyService {
  int create(int memberId, LectureReplyVO vo);

  int update(LectureReplyVO vo);

  int delete(int lectureReplyId);

  List<LectureReplyVO> getReplies(int lectureId);
}
