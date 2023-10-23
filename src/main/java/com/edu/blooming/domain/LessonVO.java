package com.edu.blooming.domain;

public class LessonVO {
  private int lessonId;
  private int lectureId;
  private String lessonName;
  private String lessonUrl;

  public LessonVO() {}

  public LessonVO(int lessonId, int lectureId, String lessonName, String lessonUrl) {
    this.lessonId = lessonId;
    this.lectureId = lectureId;
    this.lessonName = lessonName;
    this.lessonUrl = lessonUrl;
  }

  public int getLessonId() {
    return lessonId;
  }

  public void setLessonId(int lessonId) {
    this.lessonId = lessonId;
  }

  public int getLectureId() {
    return lectureId;
  }

  public void setLectureId(int lectureId) {
    this.lectureId = lectureId;
  }

  public String getLessonName() {
    return lessonName;
  }

  public void setLessonName(String lessonName) {
    this.lessonName = lessonName;
  }

  public String getLessonUrl() {
    return lessonUrl;
  }

  public void setLessonUrl(String lessonUrl) {
    this.lessonUrl = lessonUrl;
  }

  @Override
  public String toString() {
    return "LessonVO [lessonId=" + lessonId + ", lectureId=" + lectureId + ", lessonName="
        + lessonName + ", lessonUrl=" + lessonUrl + "]";
  }

}
