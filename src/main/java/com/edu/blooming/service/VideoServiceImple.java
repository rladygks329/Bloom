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
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class VideoServiceImple implements ViedoService {
  private final static Logger logger = LoggerFactory.getLogger(VideoServiceImple.class);

  @Resource(name = "uploadVideoPath")
  private String uploadPath;

  public Map<String, String> chunkUpload(MultipartFile file, int chunkNumber, int totalChunks,
      String key) throws IOException {

    Map<String, String> map = new HashMap<String, String>();
    String tempDir = uploadPath + File.separator + key;
    String uploadDir = uploadPath;

    File dir = new File(tempDir);
    if (!dir.exists()) {
      logger.info("tempDir 이 없습니다. 생성");
      dir.mkdirs();
    }

    String filename = file.getOriginalFilename() + ".part" + chunkNumber;
    Path filePath = Paths.get(tempDir, filename);
    Files.write(filePath, file.getBytes());

    if (chunkNumber == totalChunks - 1) {
      String[] split = file.getOriginalFilename().split("\\.");
      String extension = split[1];

      String outputFilename = UUID.randomUUID() + "." + extension;
      logger.info(outputFilename);
      Path outputFile = Paths.get(uploadDir, outputFilename);
      Files.createFile(outputFile);

      // Merge and Delete
      for (int i = 0; i < totalChunks; i++) {
        Path chunkFile = Paths.get(tempDir, file.getOriginalFilename() + ".part" + i);
        Files.write(outputFile, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
        Files.delete(chunkFile);
      }
      deleteDirectory(Paths.get(tempDir));
      logger.info("File uploaded successfully");
      map.put("outputFileName", outputFilename);
      map.put("continue", "y");
    } else {
      map.put("continue", "n");
    }

    return map;
  }

  @Override
  public int getLastChunkNumber(String key) {
    logger.info("getLastChunkNumber() 호출 key : " + key);
    Path temp = Paths.get(uploadPath, key);
    String[] list = temp.toFile().list();
    return list == null ? 0 : Math.max(list.length - 2, 0);
  }

  private void deleteDirectory(Path directory) throws IOException {
    if (Files.exists(directory)) {
      try (Stream<Path> walk = Files.walk(directory)) {
        walk.map(Path::toFile).forEach(File::delete);
      }
    }
  }

}
