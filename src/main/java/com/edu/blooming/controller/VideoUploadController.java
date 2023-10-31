package com.edu.blooming.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.edu.blooming.service.LessonService;
import com.edu.blooming.service.ViedoService;

@Controller
@RequestMapping(value = "/video")
public class VideoUploadController {

  @javax.annotation.Resource(name = "uploadVideoPath")
  private String uploadPath;

  private final static Logger logger = LoggerFactory.getLogger(VideoUploadController.class);

  @Autowired
  private ViedoService videoUploadService;

  @Autowired
  private LessonService lessonService;

  @GetMapping
  public String videoUploadPage() {
    logger.info("videoUploadPage");
    return "video-upload";
  }

  @GetMapping(value = "video-watch")
  public String watchVideo() {
    logger.info("watchVideo");
    return "video-watch";
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
      @RequestParam("totalChunks") int totalChunks,
      @RequestParam("key") String key
  ) throws IOException {
    Map<String, String> result = videoUploadService.chunkUpload(file, chunkNumber, totalChunks, key);
    boolean isDone = result.get("continue").equals("y");
    return isDone ? ResponseEntity.ok((String)result.get("outputFileName"))
        : ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
  }
  
  @ResponseBody
  @GetMapping("/chunk/upload/{key}")
  public ResponseEntity<?> getLastChunkNumber(@PathVariable String key) {
      return ResponseEntity.ok(videoUploadService.getLastChunkNumber(key));
  }

  @ResponseBody
  @GetMapping(value = "/vod")
  public ResponseEntity<Resource> resource(HttpServletRequest request) throws IOException {
      String filename = request.getParameter("filename");
      String extension = ".mp4";
      String path = uploadPath + File.separator + filename;
      
      logger.info("resource() 호출 : filename : " + filename);
      
      Resource resource = new FileSystemResource(path);
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .contentLength(resource.contentLength())
          .body(resource);
  }
 
  // @formatter:off
  @ResponseBody
  @GetMapping(value = "/vod/chunk/{filename}")
  public ResponseEntity<ResourceRegion> chunkResource(
      @RequestHeader HttpHeaders headers,
      @PathVariable String filename) throws IOException {
    logger.info(headers.toString());
    logger.info("chunkResource() 호출 filename : " + filename);
    String extension = ".mp4";
    String path = uploadPath + File.separator + filename + extension;

    Resource resource = new FileSystemResource(path);

    long chunkSize = 1024 * 1024;
    long contentLength = resource.contentLength();

    logger.info("contentLength : " + contentLength) ;
    HttpRange httpRange = headers.getRange().stream().findFirst()
        .orElse(HttpRange.createByteRange(0, contentLength - 1));

    long rangeLength = calculateRangeLength(httpRange, contentLength, chunkSize);
    ResourceRegion region =
        new ResourceRegion(resource, httpRange.getRangeStart(contentLength), rangeLength);
    
    logger.info("region : " + region.toString());
    
    ResponseEntity<ResourceRegion> result = ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
        .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
        .contentType(MediaType.parseMediaType("video/mp4"))
        .header("Content-Range", httpRange.toString()) // Set Content-Range header
        .eTag(path)
        .body(region);
    
    logger.info("result : " + result.toString());
    return result; 
  }

  private long calculateRangeLength(HttpRange httpRange, long contentLength, long chunkSize) {
    long start = httpRange.getRangeStart(contentLength);
    long end = httpRange.getRangeEnd(contentLength);
    return Long.min(chunkSize, end - start + 1);
  }
}
