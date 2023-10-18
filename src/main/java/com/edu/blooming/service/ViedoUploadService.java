package com.edu.blooming.service;

import java.io.IOException;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface ViedoUploadService {
  public abstract Map<String, String> chunkUpload(MultipartFile file, int chunkNumber,
      int totalChunks) throws IOException;
}
