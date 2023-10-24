package com.edu.blooming.domain;

public class LectureReplyVO {
  private int lectureReplyId;
  private int memberId;
  private int lectureId;
  private String lectureReplyContent;
  private String authorName;
  private String authorProfileUrl;
  private int lectureReplyScore;

  public LectureReplyVO() {}

  public LectureReplyVO(int lectureReplyId, int memberId, int lectureId, String lectureReplyContent,
      String authorName, String authorProfileUrl, int lectureReplyScore) {
    super();
    this.lectureReplyId = lectureReplyId;
    this.memberId = memberId;
    this.lectureId = lectureId;
    this.lectureReplyContent = lectureReplyContent;
    this.authorName = authorName;
    this.authorProfileUrl = authorProfileUrl;
    this.lectureReplyScore = lectureReplyScore;
  }

  public int getLectureReplyId() {
    return lectureReplyId;
  }

  public void setLectureReplyId(int lectureReplyId) {
    this.lectureReplyId = lectureReplyId;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public int getLectureId() {
    return lectureId;
  }

  public void setLectureId(int lectureId) {
    this.lectureId = lectureId;
  }

  public String getLectureReplyContent() {
    return lectureReplyContent;
  }

  public void setLectureReplyContent(String lectureReplyContent) {
    this.lectureReplyContent = lectureReplyContent;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getAuthorProfileUrl() {
    return authorProfileUrl;
  }

  public void setAuthorProfileUrl(String authorProfileUrl) {
    this.authorProfileUrl = authorProfileUrl;
  }

  public int getLectureReplyScore() {
    return lectureReplyScore;
  }

  public void setLectureReplyScore(int lectureReplyScore) {
    this.lectureReplyScore = lectureReplyScore;
  }

  @Override
  public String toString() {
    return "LectureReplyVO [lectureReplyId=" + lectureReplyId + ", memberId=" + memberId
        + ", lectureId=" + lectureId + ", lectureReplyContent=" + lectureReplyContent
        + ", authorName=" + authorName + ", authorProfileUrl=" + authorProfileUrl
        + ", lectureReplyScore=" + lectureReplyScore + "]";
  }

}
