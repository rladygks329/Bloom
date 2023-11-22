package com.edu.blooming.event;

import org.springframework.context.ApplicationEvent;
import com.edu.blooming.domain.LessonVO;

public class VideoUploadedEvent extends ApplicationEvent {

  private LessonVO lessonVO;

  public VideoUploadedEvent(Object source, LessonVO lessonVO) {
    super(source);
    this.lessonVO = lessonVO;
  }

  public String getFilename() {
    return lessonVO.getLessonUrl();
  }

  public int getLectureId() {
    return lessonVO.getLectureId();
  }

  public int getLessonId() {
    return lessonVO.getLessonId();
  }

}
