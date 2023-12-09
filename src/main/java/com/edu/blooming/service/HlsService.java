package com.edu.blooming.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.progress.Progress;

@Service
public class HlsService {
  private Logger logger = LoggerFactory.getLogger(HlsService.class);

  @Autowired
  private FFmpeg fFmpeg;

  @Autowired
  private FFprobe fFprobe;

  @Value("${uploadPath.video}")
  private String savedPath;

  @Value("${uploadPath.video}")
  private String hlsOutputPath;

  @Value("${uploadPath.video}")
  private String outputPath;


  public File getHlsFile(String key, String resolution, String filename) {
    logger.info("getHlsFile() 호출");
    filename = addExtention(filename);
    return new File(outputPath + File.separator + key + File.separator + resolution + File.separator
        + filename);
  }

  public File getHlsFile(String key, String filename) {
    logger.info("getHlsFile() 호출");
    filename = addExtention(filename);
    return new File(outputPath + File.separator + key + File.separator + filename);
  }

  private String addExtention(String filename) {
    if (filename.equals("master") || filename.equals("playlist")) {
      return filename + ".m3u8";
    }
    return filename + ".ts";
  }

  /// @formatter:off
  public void convertToHls(String filename) {
    // 입력 파일 경로 설정
    Path inputFilePath = Paths.get(savedPath + File.separator + filename);

    // 출력 폴더 경로 설정
    Path outputFolderPath = Paths.get(hlsOutputPath + File.separator + filename.split("\\.")[0]);

    // 화질 별 폴더 생성
    File prefix = outputFolderPath.toFile();
    File _1080 = new File(prefix, "1080");
    File _720 = new File(prefix, "720");
    File _480 = new File(prefix, "480");
    
    if (!_1080.exists()) {
      _1080.mkdirs();
    }

    if (!_720.exists()) {
      _720.mkdirs();
    }

    if (!_480.exists()) {
      _480.mkdirs();
    }

    // ffmpeg 명령어 생성
    FFmpegBuilder builder = 
        new FFmpegBuilder().setInput(inputFilePath.toAbsolutePath().toString())
        .addExtraArgs("-y")
        .addOutput(outputFolderPath.toAbsolutePath() + "/%v/playlist.m3u8") // 출력 위치
        .setFormat("hls")
        .addExtraArgs("-hls_time", "10") // chunk 시간
        .addExtraArgs("-hls_list_size", "0")
        .addExtraArgs("-hls_segment_filename", outputFolderPath.toAbsolutePath() + "/%v/output_%03d.ts") // ts 파일 이름 (ex: output_000.ts)
        .addExtraArgs("-master_pl_name", "master.m3u8") // 마스터 재생 파일
        .addExtraArgs("-map", "0:v")
        .addExtraArgs("-map", "0:v")
        .addExtraArgs("-map", "0:v")
        
        .addExtraArgs("-map", "0:a")
        .addExtraArgs("-map", "0:a")
        .addExtraArgs("-map", "0:a")
        
        //.addExtraArgs("-var_stream_map", "v:0,name:480") 
        .addExtraArgs("-var_stream_map", "v:0,a:0,name:1080 v:1,a:1,name:720 v:2,a:2,name:480") // 출력 매핑
        
        // 1080 화질 옵션
        .addExtraArgs("-b:v:0", "5000k")
        .addExtraArgs("-maxrate:v:0", "5000k")
        .addExtraArgs("-bufsize:v:0", "10000k")
        .addExtraArgs("-s:v:0", "1920x1080")
        .addExtraArgs("-crf:v:0", "15")
        .addExtraArgs("-b:a:0", "128k")

        // 720 화질 옵션
        .addExtraArgs("-b:v:1", "2500k")
        .addExtraArgs("-maxrate:v:1", "2500k")
        .addExtraArgs("-bufsize:v:1", "5000k")
        .addExtraArgs("-s:v:1", "1280x720")
        .addExtraArgs("-crf:v:1", "22")
        .addExtraArgs("-b:a:1", "96k")

        // 480 화질 옵션
        .addExtraArgs("-b:v:2", "1000k")
        .addExtraArgs("-maxrate:v:2", "1000k")
        .addExtraArgs("-bufsize:v:2", "2000k")
        .addExtraArgs("-s:v:2", "854x480")
        .addExtraArgs("-crf:v:2", "28")
        .addExtraArgs("-b:a:2", "64k")
        .done();
    
    run(builder);
  }

  private void run(FFmpegBuilder builder) {
    FFmpegExecutor executor = new FFmpegExecutor(fFmpeg, fFprobe);
    executor.createJob(builder, progress -> {
      //logger.info("progress ==> {}", progress);
      if (progress.status.equals(Progress.Status.END)) {
        logger.info("================================= JOB FINISHED =================================");
      }
    }).run();
  }
}
