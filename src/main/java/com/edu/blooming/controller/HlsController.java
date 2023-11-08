package com.edu.blooming.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.edu.blooming.service.HlsService;

@Controller
public class HlsController {
  private Logger logger = LoggerFactory.getLogger(HlsController.class);

  @Autowired
  private HlsService hlsService;

  // @formatter:off
  // master.m3u8 경로
  @ResponseBody
  @RequestMapping("/hls/{key}/{filename}")
  public ResponseEntity<InputStreamResource> getMaster(
      @PathVariable String key,
      @PathVariable String filename) throws FileNotFoundException {
    logger.info("/hls/{key}/{filename} 실행됨 key: " + key + " filename : " + filename);
    File file = hlsService.getHlsFile(key, filename);
    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("application/x-mpegURL"))
        .body(resource);
  }

  // 각 화질별 ts, m3u8 경로
  @ResponseBody
  @RequestMapping("/hls/{key}/{resolution}/{filename}")
  public ResponseEntity<InputStreamResource> getPlaylist(
      @PathVariable String key,
      @PathVariable String resolution, 
      @PathVariable String filename) throws FileNotFoundException {
    logger.info("/hls/{key}/{resolution}/{filename}실행됨 key: " + key + " resolution : " + resolution + " filename : " + filename);
    File file = hlsService.getHlsFile(key, resolution, filename);
    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("application/x-mpegURL"))
        .body(resource);
  }
}


