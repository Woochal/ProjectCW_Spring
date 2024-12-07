package com.thc.projectcd_spring.service.impl;

import com.thc.projectcd_spring.service.FileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${file.upload.path}")
    private String uploadPath;

    public String uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            // 업로드 디렉토리 확인 및 생성
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 파일명 생성
            String originalFilename = file.getOriginalFilename();
            String storedFileName = UUID.randomUUID().toString() + getFileExtension(originalFilename);

            // 파일 저장
            File dest = new File(uploadDir, storedFileName);
            file.transferTo(dest);

            logger.info("File saved at: {}", dest.getAbsolutePath());

            // 클라이언트가 접근 가능한 URL 경로 반환
            return "/files/" + storedFileName;  // 웹 접근 경로로 반환
        } catch (IOException e) {
            logger.error("File upload failed", e);
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다", e);
        }
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }
}