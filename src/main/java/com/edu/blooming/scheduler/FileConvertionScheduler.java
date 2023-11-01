package com.edu.blooming.scheduler;

import java.io.IOException;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import me.desair.tus.server.TusFileUploadService;

@Component
public class FileConvertionScheduler {
  private Logger logger = LoggerFactory.getLogger(FileConvertionScheduler.class);

  @Autowired
  private TusFileUploadService tus;

  @Scheduled(cron = "0 0 * * * *") // every hour
  public void cleanup() throws IOException {
    logger.info("clean up : " + LocalDateTime.now().toString());
    tus.cleanup();
  }
}
