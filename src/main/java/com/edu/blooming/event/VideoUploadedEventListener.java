package com.edu.blooming.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.edu.blooming.service.HlsService;
import com.edu.blooming.service.LectureService;
import com.edu.blooming.service.LessonService;

@Component
public class VideoUploadedEventListener implements ApplicationListener<VideoUploadedEvent> {
  private Logger logger = LoggerFactory.getLogger(VideoUploadedEventListener.class);

  @Autowired
  private HlsService hlsService;

  @Autowired
  private LectureService lectureService;

  @Autowired
  private LessonService lessonService;

  @Async
  @Override
  public void onApplicationEvent(VideoUploadedEvent event) {
    logger.info("Start Event Handling");

    // 파일 변환
    String filename = event.getFilename();
    hlsService.convertToHls(filename);

    // 다 처리되면 갱신
    int lectureId = event.getLectureId();
    int lessonId = event.getLessonId();
    lessonService.handleLessonUploaded(lectureId, lessonId);

    logger.info("End Event Handling");
  }

}
