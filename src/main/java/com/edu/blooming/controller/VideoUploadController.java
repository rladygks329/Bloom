package com.edu.blooming.controller;

import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.edu.blooming.service.ViedoUploadService;

@Controller
@RequestMapping(value = "/video")
public class VideoUploadController {
  private final static Logger logger = LoggerFactory.getLogger(VideoUploadController.class);

  @Autowired
  private ViedoUploadService videoUploadService;

  @GetMapping
  public String videoUploadPage() {
    logger.info("videoUploadPage");
    return "video-upload";
  }

  //@formatter:off
  /**
  * 비디오 분할 업로드 
  * @value result : key continue: 다음 조각 업로드 가능 여부, outputFileName : 합쳐진 file이름
  * @param chunk : 분할 파일, chunkNumner: 조각 위치, totalChunks: 전체 조각 수
  * @return status 206: 조각 업로드 성공, 200: 전체 업로드 성공 
  */
  @ResponseBody
  @PostMapping
  public ResponseEntity<String> chunkUpload(
      @RequestParam("chunk") MultipartFile file,
      @RequestParam("chunkNumber") int chunkNumber, 
      @RequestParam("totalChunks") int totalChunks
  ) throws IOException {
    Map<String, String> result = videoUploadService.chunkUpload(file, chunkNumber, totalChunks);
    boolean isDone = result.get("continue").equals("y");
    return isDone ? ResponseEntity.ok((String)result.get("outputFileName"))
        : ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
  }
}
