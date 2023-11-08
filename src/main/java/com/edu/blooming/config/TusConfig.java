package com.edu.blooming.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import me.desair.tus.server.TusFileUploadService;

@Configuration
public class TusConfig {

  @Value("${uploadPath.video}")
  private String tusDataPath;

  @Bean
  public TusFileUploadService tus() {
    return new TusFileUploadService().withStoragePath(tusDataPath).withDownloadFeature()
        .withUploadExpirationPeriod(1000L * 60 * 60 * 12).withThreadLocalCache(true)
        .withUploadURI("/blooming/tus/upload");
  }
}
