package com.edu.blooming.event;

import org.springframework.context.ApplicationEvent;

public class VideoUploadedEvent extends ApplicationEvent {

  private String filename;
  private int lectureId;
  private int lessonId;

  public VideoUploadedEvent(Object source, String filename, int lectureId, int lessonId) {
    super(source);
    this.filename = filename;
    this.lectureId = lectureId;
    this.lessonId = lessonId;
  }

  public String getFilename() {
    return filename;
  }

  public int getLectureId() {
    return lectureId;
  }

  public int getLessonId() {
    return lessonId;
  }

}
