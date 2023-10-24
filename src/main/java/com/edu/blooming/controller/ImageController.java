package com.edu.blooming.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.service.MemberService;

@Controller
@RequestMapping(value="/image")

public class ImageController {
	private static final Logger logger = 
			LoggerFactory.getLogger(ImageController.class);
	
	@PostMapping
	public ResponseEntity<String> uploadAjaxActionPost(@RequestParam("uploadFile") MultipartFile uploadFile) {
		logger.info("uploadAjaxActionPost 호출");
		
		String uploadFolder = "C:\\upload";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		String datePath = str.replace("-", File.separator);
		
		File uploadPath = new File(uploadFolder, datePath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
	
		// 파일 이름
		String memberProfileUrl = null;
		String uploadFileName = uploadFile.getOriginalFilename();
		logger.info("uploadFileName = " + uploadFileName);
		// 파일 이름에 uuid 적용
		String uuid = UUID.randomUUID().toString();
		uploadFileName = uuid + "_" + uploadFileName;
		logger.info("uploadFileName = " + uploadFileName);
			
		File saveFile = new File(uploadPath, uploadFileName);
		try {
			uploadFile.transferTo(saveFile);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		memberProfileUrl = uploadFolder + File.separator + datePath + File.separator + uploadFileName;
		logger.info(memberProfileUrl);
		return new ResponseEntity<String>(memberProfileUrl, HttpStatus.OK);
		
	} // end uploadAjaxActionPost()
		
} // end ImageController

















