package com.edu.blooming.service;

import java.util.Map;
import com.edu.blooming.domain.LectureReplyVO;
import com.edu.blooming.exception.AlreadyExistException;

public interface LectureReplyService {
  int create(int memberId, LectureReplyVO vo) throws AlreadyExistException;

  LectureReplyVO select(int memberId, int lectureId);

  int update(LectureReplyVO vo);

  int delete(int lectureReplyId);

  Map<String, Object> getReplies(int lectureId, int memberId, int pageSize, int lastReplyId);
}
