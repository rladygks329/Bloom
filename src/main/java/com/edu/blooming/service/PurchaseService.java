package com.edu.blooming.service;

import java.util.List;

public interface PurchaseService {

  int purchase(int memberId, List<Integer> lectureIds);

  int purchase(int memberId, int lectureId);

  int refund(int memberId, int lectureId);

}
