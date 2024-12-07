package com.thc.projectcd_spring.service;


import com.thc.projectcd_spring.dto.DefaultDto;
import com.thc.projectcd_spring.dto.TbuserDto;

// service annotation은 클래스에서 해야한다.
public interface TbuserService {

    TbuserDto.TextResDto sendEmail(TbuserDto.UidReqDto param);
    TbuserDto.TextResDto confirmEmail(TbuserDto.ConfirmReqDto param);
    TbuserDto.TextResDto signup(TbuserDto.SignupReqDto param);
//    TbuserDto.TextResDto login(TbuserDto.SignupReqDto param);
    TbuserDto.CreateResDto logout(DefaultDto.GetReqDto param);
    TbuserDto.TextResDto generateAccessToken(TbuserDto.AccessTokenReqDto param);
}
