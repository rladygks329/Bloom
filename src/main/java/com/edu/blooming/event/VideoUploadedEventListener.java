package com.edu.blooming.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.edu.blooming.service.HlsService;

@Component
public class VideoUploadedEventListener implements ApplicationListener<VideoUploadedEvent> {
  private Logger logger = LoggerFactory.getLogger(VideoUploadedEventListener.class);

  @Autowired
  private HlsService hlsService;

  @Async
  @Override
  public void onApplicationEvent(VideoUploadedEvent event) {
    logger.info("Start Event Handling");
    String filename = event.getFilename();
    hlsService.convertToHls(filename);
  }

}
