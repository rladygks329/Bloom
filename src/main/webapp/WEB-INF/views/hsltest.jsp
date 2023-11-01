<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Uploaded vod</title>

    <link href="https://vjs.zencdn.net/7.14.3/video-js.css" rel="stylesheet" />
    <script src="https://vjs.zencdn.net/7.14.3/video.min.js"></script>
</head>
<body>
<video id="my-video" class="video-js" controls preload="auto" width="720" height="480">
    <source src="http://localhost:8080/blooming/hls/c697c8c996e54c4b996c910732d3990e/master.m3u8" type="application/x-mpegURL">
</video>
<script>
    var player = videojs('my-video');
    player.play();
</script>
</body>
</html>