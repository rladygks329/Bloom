package com.edu.blooming.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.edu.blooming.util.FileUploadUtil;


@Service
public class VideoUploadServiceImple implements ViedoUploadService {
  private final static Logger logger = LoggerFactory.getLogger(VideoUploadServiceImple.class);

  @Resource(name = "uploadVideoPath")
  private String uploadPath;

  public Map<String, String> chunkUpload(MultipartFile file, int chunkNumber, int totalChunks)
      throws IOException {
    Map<String, String> map = new HashMap<String, String>();
    String dateDir = FileUploadUtil.getUploadPath(uploadPath); // 2021/10/11
    String uploadDir = uploadPath + '/' + dateDir;

    File dir = new File(uploadDir);
    if (!dir.exists()) {
      dir.mkdirs();
    }

    // 임시 저장 파일 이름
    String filename = file.getOriginalFilename() + ".part" + chunkNumber;
    Path filePath = Paths.get(uploadDir, filename);

    // 임시 저장
    Files.write(filePath, file.getBytes());

    // 마지막 조각이 전송 됐을 경우
    if (chunkNumber == totalChunks - 1) {
      String[] split = file.getOriginalFilename().split("\\.");
      String outputFilename =
          UUID.randomUUID() + file.getOriginalFilename() + "." + split[split.length - 1];
      logger.info(outputFilename);
      Path outputFile = Paths.get(uploadDir, outputFilename);
      Files.createFile(outputFile);

      // 임시 파일들을 하나로 합침
      for (int i = 0; i < totalChunks; i++) {
        Path chunkFile = Paths.get(uploadDir, file.getOriginalFilename() + ".part" + i);
        Files.write(outputFile, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
        // 합친 후 삭제
        Files.delete(chunkFile);
      }
      logger.info("File uploaded successfully");
      map.put("outputFileName", outputFilename);
      map.put("continue", "y");
    } else {
      map.put("continue", "n");
    }

    return map;
  }
}
