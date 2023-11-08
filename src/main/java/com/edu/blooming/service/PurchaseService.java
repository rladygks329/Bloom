package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.exception.AlreadyExistException;

public interface PurchaseService {

  int purchase(int memberId) throws AlreadyExistException;

  int refund(int memberId, int lectureId);

  List<LectureVO> getPurchaseList(int memberId);

  boolean checkPurchase(int memberId, int lectureId);
}
