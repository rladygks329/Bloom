package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.exception.AlreadyExistException;

public interface CartService {
  int add(int memberId, int lectureId) throws AlreadyExistException;

  int remove(int memberId, int lectureId);

  boolean isExist(int memberId, int lectureId);

  List<LectureVO> getItems(int memberId);

  int calcTotal(int memberId);
}
