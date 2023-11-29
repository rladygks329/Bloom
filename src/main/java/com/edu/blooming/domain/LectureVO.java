package com.edu.blooming.domain;

import java.util.Date;

public class LectureVO {
  public static final int ORDER_TYPE_DEFAULT = 0;
  public static final int ORDER_TYPE_PRICE_DESC = 1;
  public static final int ORDER_TYPE_PRICE_ASC = 2;
  public static final int ORDER_TYPE_LIKE_COUNT_DESC = 3;
  public static final int ORDER_TYPE_SALES_COUNT_DESC = 4;
  public static final int ORDER_TYPE_REPLY_COUNT_DESC = 5;
  public static final int ORDER_TYPE_RATING_DESC = 6;

  private int lectureId;
  private int memberId;
  private String authorName;
  private String lectureTitle;
  private String lectureDescription;
  private int lecturePrice;
  private int lectureVideoProcessingLevel;
  private int lectureSalesCount;
  private int lectureTotalScore;
  private int lectureReplyCount;
  private int lectureLikeCount;
  private String lectureThumbnailUrl;
  private Date lectureDateCreated;

  public LectureVO() {}

  public LectureVO(int lectureId, int memberId, String authorName, String lectureTitle,
      String lectureDescription, int lecturePrice, int lectureVideoProcessingLevel,
      int lectureSalesCount, int lectureTotalScore, int lectureReplyCount, int lectureLikeCount,
      String lectureThumbnailUrl, Date lectureDateCreated) {
    super();
    this.lectureId = lectureId;
    this.memberId = memberId;
    this.authorName = authorName;
    this.lectureTitle = lectureTitle;
    this.lectureDescription = lectureDescription;
    this.lecturePrice = lecturePrice;
    this.lectureVideoProcessingLevel = lectureVideoProcessingLevel;
    this.lectureSalesCount = lectureSalesCount;
    this.lectureTotalScore = lectureTotalScore;
    this.lectureReplyCount = lectureReplyCount;
    this.lectureLikeCount = lectureLikeCount;
    this.lectureThumbnailUrl = lectureThumbnailUrl;
    this.lectureDateCreated = lectureDateCreated;
  }

  /*
   * @formatter:off
   * @param: number
   * @return: LectureVO order type (0 <= x <= 6)
   * @formatter:on
   */
  public static final int getOrderType(int n) {
    if (ORDER_TYPE_DEFAULT <= n && n <= ORDER_TYPE_RATING_DESC) {
      return n;
    }
    return ORDER_TYPE_DEFAULT;
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

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getLectureTitle() {
    return lectureTitle;
  }

  public void setLectureTitle(String lectureTitle) {
    this.lectureTitle = lectureTitle;
  }

  public String getLectureDescription() {
    return lectureDescription;
  }

  public void setLectureDescription(String lectureDescription) {
    this.lectureDescription = lectureDescription;
  }

  public int getLecturePrice() {
    return lecturePrice;
  }

  public void setLecturePrice(int lecturePrice) {
    this.lecturePrice = lecturePrice;
  }

  public int getLectureVideoProcessingLevel() {
    return lectureVideoProcessingLevel;
  }

  public void setLectureVideoProcessingLevel(int lectureVideoProcessingLevel) {
    this.lectureVideoProcessingLevel = lectureVideoProcessingLevel;
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

  public String getLectureThumbnailUrl() {
    return lectureThumbnailUrl;
  }

  public void setLectureThumbnailUrl(String lectureThumbnailUrl) {
    this.lectureThumbnailUrl = lectureThumbnailUrl;
  }

  public Date getLectureDateCreated() {
    return lectureDateCreated;
  }

  public void setLectureDateCreated(Date lectureDateCreated) {
    this.lectureDateCreated = lectureDateCreated;
  }

  @Override
  public String toString() {
    return "LectureVO [lectureId=" + lectureId + ", memberId=" + memberId + ", authorName="
        + authorName + ", lectureTitle=" + lectureTitle + ", lectureDescription="
        + lectureDescription + ", lecturePrice=" + lecturePrice + ", lectureVideoProcessingLevel="
        + lectureVideoProcessingLevel + ", lectureSalesCount=" + lectureSalesCount
        + ", lectureTotalScore=" + lectureTotalScore + ", lectureReplyCount=" + lectureReplyCount
        + ", lectureLikeCount=" + lectureLikeCount + ", lectureThumbnailUrl=" + lectureThumbnailUrl
        + ", lectureDateCreated=" + lectureDateCreated + "]";
  }

}
