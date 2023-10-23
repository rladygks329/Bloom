package com.edu.blooming.persistence;

public interface PurchaseDAO {
  int insert(int memberId, int lectureId, int price);

  int delete(int memberId, int lectureId);

}
