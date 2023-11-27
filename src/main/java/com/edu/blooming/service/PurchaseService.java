package com.edu.blooming.service;

import java.util.List;
import java.util.Map;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.exception.AlreadyExistException;
import com.edu.blooming.payment.PaymentMethod;

public interface PurchaseService {

  Map<String, Object> readyForPurchase(PaymentMethod method, int memberId);

  Map<String, Object> approvePurchase(PaymentMethod method, int memberId, String token);

  int success(int memberId) throws AlreadyExistException;

  int refund(int memberId, int lectureId);

  List<LectureVO> getPurchaseList(int memberId);

  boolean checkPurchase(int memberId, int lectureId);
}
