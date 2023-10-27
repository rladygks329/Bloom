package com.edu.blooming.persistence;

import java.util.List;
import com.edu.blooming.domain.LectureVO;

public interface CartDAO {
  int insert(int memberId, int lectureId);

  int delete(int memberId, int lectureId);

  int selectExist(int memberId, int lectureId);

  List<LectureVO> select(int memberId);

  int calcTotal(int memberId);

}
