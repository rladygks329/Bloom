package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.LectureReplyVO;
import com.edu.blooming.exception.AlreadyExistException;

public interface LectureReplyService {
  int create(int memberId, LectureReplyVO vo) throws AlreadyExistException;

  int update(LectureReplyVO vo);

  int delete(int lectureReplyId);

  List<LectureReplyVO> getReplies(int lectureId);
}
