package com.edu.blooming.persistence;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import com.edu.blooming.domain.LectureReplyVO;

public interface LectureReplyDAO {
  int insert(LectureReplyVO vo) throws DataIntegrityViolationException;;

  int update(LectureReplyVO vo);

  int delete(int lectureReplyId);

  LectureReplyVO select(int memberId, int lectureId);

  LectureReplyVO selectByLectureReplyId(int lectureReplyId);

  List<LectureReplyVO> selectPageForInfiniteScroll(int memberId, int lectureId, int lastReplyId,
      int pageSize);

  List<LectureReplyVO> selectByLectureId(int lectureId);

  List<LectureReplyVO> selectByInstructorId(int memberId);
}
