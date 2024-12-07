package com.thc.projectcd_spring.controller;

import com.thc.projectcd_spring.dto.TbchickenDto;
import com.thc.projectcd_spring.dto.TbchickenCommentDto;
import com.thc.projectcd_spring.service.FileService;
import com.thc.projectcd_spring.service.TbchickenService;
import com.thc.projectcd_spring.service.impl.FileServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// @RestController은 @Controller와 @ResponseBody의 조합.
// 컨트롤러에서는 데이터를 반환하기 위해 @ResponseBody 어노테이션을 활용해주어야 하는데, @RestController는 없이도 가능하다.
@RequestMapping("/api/tbchicken")
@RestController
public class TbchickenRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private TbchickenService tbchickenService;
    private FileService fileService;

    // Spring은 여러 개의 생성자가 있을 경우 어떤 생성자를 사용할지 결정할 수 없어 에러가 발생한다.
    // @Autowired 어노테이션을 사용하여 Spring이 자동으로 의존성을 주입할 수 있도록 설정
    @Autowired
    public TbchickenRestController(TbchickenService tbchickenService, FileService fileService) {
        this.tbchickenService = tbchickenService;
        this.fileService = fileService;
    }



    @GetMapping("/getAllChicken")
    public ResponseEntity<List<TbchickenDto.ChickenResDto>> getFilteredChicken(@Valid TbchickenDto.ChickenReqDto param){

        return ResponseEntity.status(HttpStatus.OK).body(tbchickenService.getFilteredChicken(param));
    }

    @GetMapping("/getChickenComments")
    public ResponseEntity<List<TbchickenCommentDto.ChickenCommentResDto>> getChickenComments(@Valid TbchickenCommentDto.ChickenCommentReqDto param){

        return ResponseEntity.status(HttpStatus.OK).body(tbchickenService.getChickenComments(param));
    }

    @PostMapping("/addChicken")
    public ResponseEntity<TbchickenDto.AddChickenResDto> addChicken(
            @RequestPart("chickenData") TbchickenDto.AddChickenReqDto param,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        logger.info("Received image file: {}", image != null ? image.getOriginalFilename() : "null");

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileService.uploadImage(image);
            logger.info("Uploaded image URL: {}", imageUrl);
            param.setImageUrl(imageUrl);
            logger.info("Set image URL in param: {}", param.getImageUrl());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(tbchickenService.addChicken(param));
    }

    @PostMapping("/addChickenComment")
    public ResponseEntity<TbchickenCommentDto.TextResDto> addChickenComment(@Valid @RequestBody TbchickenCommentDto.AddChickenCommentReqDto param){

        return ResponseEntity.status(HttpStatus.OK).body(tbchickenService.addChickenComment(param));
    }


}
