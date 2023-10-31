package com.edu.blooming.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;

@Service
public class TusService {
  private Logger logger = LoggerFactory.getLogger(TusService.class);

  @Autowired
  private TusFileUploadService tusFileUploadService;

  @Resource(name = "uploadVideoPath")
  private String savePath;

  public String tusUpload(HttpServletRequest request, HttpServletResponse response) {
    logger.info("tusFileUploadService : " + tusFileUploadService.toString());
    try {
      // 업로드
      tusFileUploadService.process(request, response);

      // 현재 업로드 정보
      UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(request.getRequestURI());

      // 완료 된 경우 파일 저장
      if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
        // 파일 저장
        createFile(tusFileUploadService.getUploadedBytes(request.getRequestURI()),
            uploadInfo.getFileName());

        // 임시 파일 삭제
        tusFileUploadService.deleteUpload(request.getRequestURI());

        return "success";
      }
      return null;
    } catch (IOException | TusException e) {
      logger.error("exception was occurred. message={}", e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  // 파일 업로드 (날짜별 디렉토리 하위에 저장)
  private void createFile(InputStream is, String filename) throws IOException {
    LocalDate today = LocalDate.now();

    String uploadedPath = savePath + "/" + today;

    String vodName = getVodName(filename);

    File file = new File(uploadedPath, vodName);

    FileUtils.copyInputStreamToFile(is, file);
  }

  // 파일 이름은 랜덤 UUID 사용
  private String getVodName(String filename) {
    String[] split = filename.split("\\.");
    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    return uuid + "." + split[split.length - 1];
  }
}
