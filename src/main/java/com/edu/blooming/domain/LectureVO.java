package com.edu.blooming.domain;

import java.util.Date;

public class LectureVO {
  private int lectureId;
  private int memberId;
  private String lectureTitle;
  private int lecturePrice;
  private int lectureSalesCount;
  private int lectureTotalScore;
  private int lectureReplyCount;
  private int lectureLikeCount;
  private Date lectureDateCreated;

  public LectureVO() {}

  public LectureVO(int lectureId, int memberId, String lectureTitle, int lecturePrice,
      int lectureSalesCount, int lectureTotalScore, int lectureReplyCount, int lectureLikeCount,
      Date lectureDateCreated) {
    this.lectureId = lectureId;
    this.memberId = memberId;
    this.lectureTitle = lectureTitle;
    this.lecturePrice = lecturePrice;
    this.lectureSalesCount = lectureSalesCount;
    this.lectureTotalScore = lectureTotalScore;
    this.lectureReplyCount = lectureReplyCount;
    this.lectureLikeCount = lectureLikeCount;
    this.lectureDateCreated = lectureDateCreated;
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

  public String getLectureTitle() {
    return lectureTitle;
  }

  public void setLectureTitle(String lectureTitle) {
    this.lectureTitle = lectureTitle;
  }

  public int getLecturePrice() {
    return lecturePrice;
  }

  public void setLecturePrice(int lecturePrice) {
    this.lecturePrice = lecturePrice;
  }

  public int getLectureSalesCount() {
    return lectureSalesCount;
  }

  public void setLectureSalesCount(int lectureSalesCount) {
    this.lectureSalesCount = lectureSalesCount;
  }

  public int getLectureTotalScore() {
    return lectureTotalScore;
  }

  public void setLectureTotalScore(int lectureTotalScore) {
    this.lectureTotalScore = lectureTotalScore;
  }

  public int getLectureReplyCount() {
    return lectureReplyCount;
  }

  public void setLectureReplyCount(int lectureReplyCount) {
    this.lectureReplyCount = lectureReplyCount;
  }

  public int getLectureLikeCount() {
    return lectureLikeCount;
  }

  public void setLectureLikeCount(int lectureLikeCount) {
    this.lectureLikeCount = lectureLikeCount;
  }

  public Date getLectureDateCreated() {
    return lectureDateCreated;
  }

  public void setLectureDateCreated(Date lectureDateCreated) {
    this.lectureDateCreated = lectureDateCreated;
  }

  @Override
  public String toString() {
    return "LectureVO [lectureId=" + lectureId + ", memberId=" + memberId + ", lectureTitle="
        + lectureTitle + ", lecturePrice=" + lecturePrice + ", lectureSalesCount="
        + lectureSalesCount + ", lectureTotalScore=" + lectureTotalScore + ", lectureReplyCount="
        + lectureReplyCount + ", lectureLikeCount=" + lectureLikeCount + ", lectureDateCreated="
        + lectureDateCreated + "]";
  }

}
