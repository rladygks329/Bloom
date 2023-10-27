package com.edu.blooming.domain;

import java.util.Date;

public class PurchaseVO {
  private int lectureId;
  private int memberId;
  private int purchsePrice;
  private Date purchaseDateCreated;

  public PurchaseVO() {}

  public PurchaseVO(int lectureId, int memberId, int purchsePrice, Date purchaseDateCreated) {
    this.lectureId = lectureId;
    this.memberId = memberId;
    this.purchsePrice = purchsePrice;
    this.purchaseDateCreated = purchaseDateCreated;
  }

  public int getLectureId() {
    return lectureId;
  }

  public void setLectureId(int lectureId) {
    this.lectureId = lectureId;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public int getPurchsePrice() {
    return purchsePrice;
  }

  public void setPurchsePrice(int purchsePrice) {
    this.purchsePrice = purchsePrice;
  }

  public Date getPurchaseDateCreated() {
    return purchaseDateCreated;
  }

  public void setPurchaseDateCreated(Date purchaseDateCreated) {
    this.purchaseDateCreated = purchaseDateCreated;
  }

  @Override
  public String toString() {
    return "PurchaseVO [lectureId=" + lectureId + ", memberId=" + memberId + ", purchsePrice="
        + purchsePrice + ", purchaseDateCreated=" + purchaseDateCreated + "]";
  }

}
