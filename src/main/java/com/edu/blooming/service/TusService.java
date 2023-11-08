package com.edu.blooming.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;

@Service
public class TusService {
  private Logger logger = LoggerFactory.getLogger(TusService.class);

  @Autowired
  private TusFileUploadService tusFileUploadService;

  @Value("${uploadPath.video}")
  private String uploadPath;

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
        String originFileName = uploadInfo.getFileName();
        String savedFileName = getSavedFileName(originFileName);

        File file = new File(uploadPath, savedFileName);
        FileUtils.copyInputStreamToFile(
            tusFileUploadService.getUploadedBytes(request.getRequestURI()), file);

        // 임시 파일 삭제
        tusFileUploadService.deleteUpload(request.getRequestURI());
        return savedFileName;
      }
      return null;
    } catch (IOException | TusException e) {
      logger.error("exception was occurred. message={}", e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  private String getSavedFileName(String originFileName) {
    String[] split = originFileName.split("\\.");
    String extention = split[split.length - 1];
    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    String savedFileName = uuid + "." + extention;
    return savedFileName;
  }
}
