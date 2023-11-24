package com.edu.blooming.payment;

/**
 * 결제 금액 정보
 */
public class KakaoAmount {
  private int total; // 총 결제 금액
  private int tax_free; // 비과세 금액
  private int tax; // 부가세 금액
  private int point; // 사용한 포인트
  private int discount; // 할인금액
  private int green_deposit; // 컵 보증금

  public KakaoAmount() {}

  public KakaoAmount(int total, int tax_free, int tax, int point, int discount, int green_deposit) {
    this.total = total;
    this.tax_free = tax_free;
    this.tax = tax;
    this.point = point;
    this.discount = discount;
    this.green_deposit = green_deposit;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getTax_free() {
    return tax_free;
  }

  public void setTax_free(int tax_free) {
    this.tax_free = tax_free;
  }

  public int getTax() {
    return tax;
  }

  public void setTax(int tax) {
    this.tax = tax;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public int getDiscount() {
    return discount;
  }

  public void setDiscount(int discount) {
    this.discount = discount;
  }

  public int getGreen_deposit() {
    return green_deposit;
  }

  public void setGreen_deposit(int green_deposit) {
    this.green_deposit = green_deposit;
  }

  @Override
  public String toString() {
    return "KakaoAmount [total=" + total + ", tax_free=" + tax_free + ", tax=" + tax + ", point="
        + point + ", discount=" + discount + ", green_deposit=" + green_deposit + "]";
  }
}
