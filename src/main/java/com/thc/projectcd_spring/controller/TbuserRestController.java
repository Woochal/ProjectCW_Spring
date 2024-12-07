package com.thc.projectcd_spring.controller;

import com.thc.projectcd_spring.dto.DefaultDto;
import com.thc.projectcd_spring.dto.TbuserDto;
import com.thc.projectcd_spring.service.TbuserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// @RestController은 @Controller와 @ResponseBody의 조합.
// 컨트롤러에서는 데이터를 반환하기 위해 @ResponseBody 어노테이션을 활용해주어야 하는데, @RestController는 없이도 가능하다.
@RequestMapping("/api/tbuser")
@RestController
public class TbuserRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private TbuserService tbuserService;

    // Spring은 여러 개의 생성자가 있을 경우 어떤 생성자를 사용할지 결정할 수 없어 에러가 발생한다.
    // @Autowired 어노테이션을 사용하여 Spring이 자동으로 의존성을 주입할 수 있도록 설정
    @Autowired
    public TbuserRestController(TbuserService tbuserService) {
        this.tbuserService = tbuserService;
    }

    @PostMapping("/email")
    public ResponseEntity<TbuserDto.TextResDto> sendEmail(@Valid @RequestBody TbuserDto.UidReqDto param){

        logger.info(param.toString());
        System.out.println(param.toString());
        System.out.println("Help!!");
        return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.sendEmail(param));
    }

    @PostMapping("/email_confirm")
    public ResponseEntity<TbuserDto.TextResDto> confirmEmail(@Valid @RequestBody TbuserDto.ConfirmReqDto param){

        return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.confirmEmail(param));
    }

    @PostMapping("/signup")
    public ResponseEntity<TbuserDto.TextResDto> signup(@Valid @RequestBody TbuserDto.SignupReqDto param){

        return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.signup(param));
    }

//    @PostMapping("/login")
//    public ResponseEntity<TbuserDto.TextResDto> login(@Valid @RequestBody TbuserDto.SignupReqDto param){
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.login(param));
//    }

    @PostMapping("/logout")
    public ResponseEntity<TbuserDto.CreateResDto> logout(HttpServletRequest request){
        System.out.println("logoutController");
        System.out.println("request: " + request.getAttribute("id"));
        String reqTbuserId = request.getAttribute("id") + "";
        //인터셉터에서 토큰이 없었을 경우!
        if (request.getAttribute("id") == null) {
            //return null;
            throw new RuntimeException("should login");
        }
        DefaultDto.GetReqDto param = DefaultDto.GetReqDto.builder().id(reqTbuserId).build();
        return ResponseEntity.status(HttpStatus.OK).body(tbuserService.logout(param));
    }

    @PostMapping("/accessToken")
    public ResponseEntity<TbuserDto.TextResDto> generateAccessToken(@Valid @RequestBody TbuserDto.AccessTokenReqDto param){

        return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.generateAccessToken(param));
    }

}
