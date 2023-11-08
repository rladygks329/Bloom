package com.edu.blooming.persistence;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import com.edu.blooming.domain.LectureVO;

public interface PurchaseDAO {
  int insert(int memberId, int lectureId, int price) throws DataIntegrityViolationException;

  int delete(int memberId, int lectureId);

  List<LectureVO> select(int memberId);

  boolean selectIsMemberBuyLecture(int memberId, int lectureId);
}
