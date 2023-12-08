package com.edu.blooming.payment;

import java.util.Map;

// 다른 결제 수단을 쉽게 추가할 수 있도록 만든 인터페이스
public interface PaymentMethod {
  public Map<String, Object> readyForPay(int memberId, String name, int price);

  public Map<String, Object> approvePay(int memberId, String token);
}
