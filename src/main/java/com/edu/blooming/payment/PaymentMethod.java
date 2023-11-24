package com.edu.blooming.payment;

import org.springframework.http.ResponseEntity;

public interface PaymentMethod<T> {
  public ResponseEntity<T> pay(int memberId, int price) throws Exception;
}
