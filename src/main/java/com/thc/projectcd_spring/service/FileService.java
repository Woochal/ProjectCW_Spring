package com.thc.projectcd_spring.service;
import org.springframework.web.multipart.MultipartFile;

// service annotation은 클래스에서 해야한다.
public interface FileService {

    String uploadImage(MultipartFile param) ;
}
