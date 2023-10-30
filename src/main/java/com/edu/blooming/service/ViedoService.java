package com.edu.blooming.service;

import java.io.IOException;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface ViedoService {
  public abstract Map<String, String> chunkUpload(MultipartFile file, int chunkNumber,
      int totalChunks, String key) throws IOException;

  public abstract int getLastChunkNumber(String key);
}
