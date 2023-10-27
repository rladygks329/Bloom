package com.edu.blooming.domain;

import java.util.Date;

public class LectureVOBuilder {
  private int lectureId;
  private int memberId;
  private String authorName;
  private String lectureTitle;
  private String lectureDescription;
  private int lecturePrice;
  private int lectureSalesCount;
  private int lectureTotalScore;
  private int lectureReplyCount;
  private int lectureLikeCount;
  private String lectureThumbnailUrl;
  private Date lectureDateCreated;

  public LectureVOBuilder lectureId(int lectureId) {
    this.lectureId = lectureId;
    return this;
  }

  public LectureVOBuilder memberId(int memberId) {
    this.memberId = memberId;
    return this;
  }

  public LectureVOBuilder authorName(String authorName) {
    this.authorName = authorName;
    return this;
  }

  public LectureVOBuilder lectureTitle(String lectureTitle) {
    this.lectureTitle = lectureTitle;
    return this;
  }

  public LectureVOBuilder lectureDescription(String lectureDescription) {
    this.lectureDescription = lectureDescription;
    return this;
  }

  public LectureVOBuilder lecturePrice(int lecturePrice) {
    this.lecturePrice = lecturePrice;
    return this;
  }

  public LectureVOBuilder lectureSalesCount(int lectureSalesCount) {
    this.lectureSalesCount = lectureSalesCount;
    return this;
  }

  public LectureVOBuilder lectureTotalScore(int lectureTotalScore) {
    this.lectureTotalScore = lectureTotalScore;
    return this;
  }

  public LectureVOBuilder lectureReplyCount(int lectureReplyCount) {
    this.lectureReplyCount = lectureReplyCount;
    return this;
  }

  public LectureVOBuilder lectureLikeCount(int lectureLikeCount) {
    this.lectureLikeCount = lectureLikeCount;
    return this;
  }

  public LectureVOBuilder lectureThumbnailUrl(String lectureThumbnailUrl) {
    this.lectureThumbnailUrl = lectureThumbnailUrl;
    return this;
  }

  public LectureVOBuilder lectureDateCreated(Date lectureDateCreated) {
    this.lectureDateCreated = lectureDateCreated;
    return this;
  }

  public LectureVO build() {
    return new LectureVO(lectureId, memberId, authorName, lectureTitle, lectureDescription,
        lecturePrice, lectureSalesCount, lectureTotalScore, lectureReplyCount, lectureLikeCount,
        lectureThumbnailUrl, lectureDateCreated);
  }
}
