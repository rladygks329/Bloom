package com.edu.blooming.event;

import org.springframework.context.ApplicationEvent;

public class VideoUploadedEvent extends ApplicationEvent {

  private String filename;

  public VideoUploadedEvent(Object source, String filename) {
    super(source);
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }

}
