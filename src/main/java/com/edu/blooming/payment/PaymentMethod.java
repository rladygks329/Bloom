package com.edu.blooming.payment;

import java.util.Map;

public interface PaymentMethod {
  public Map<String, Object> readyForPay(int memberId, String name, int price);

  public Map<String, Object> approvePay(int memberId, String token);
}
