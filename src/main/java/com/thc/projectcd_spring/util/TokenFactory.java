package com.thc.projectcd_spring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenFactory {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 유저 id와 현재 시간을 사용해서 토큰 생성
    public String generate(String id, long sec){

        // 토큰이 만료되는 시간 설정
        Date nowDate = new Date();
        long expiredMillSec = nowDate.getTime() + sec * 1000;
//        logger.info("expiredMillSec : " + expiredMillSec);

        String token = "";

        try{
            String secretKey = "1234567890123456";
            // 암호화한 토큰 생성
            token = AES256Cipher.AES_Encode(secretKey, id + "_" + expiredMillSec);
        } catch (Exception e){
            throw new RuntimeException("AES encrypt failed");
        }
        return token;
    }

    // 토큰이 유효한지 확인
    public String verify(String token){
        logger.info("token : " + token);
        String decodeToken = "";
        try{
            String secretKey = "1234567890123456";
            decodeToken = AES256Cipher.AES_Decode(secretKey, token);

            // 토큰 만료시간을 찾기 위해 split. 지금 decodeToken은 id-expiredMillSec 형태다.
            String[] arrayToken = decodeToken.split("_");

            // 토큰 만료시간 추출
            long expiredMillSec = Long.parseLong(arrayToken[1]);

            // 현재 시간과 토큰 만료시간을 비교
            Date nowDate = new Date();
            long nowMillSec = nowDate.getTime();
            if(nowMillSec < expiredMillSec){
                // 토큰이 유효할 경우

                // 유저 id 반환
                return arrayToken[0];
            } else {
                // 토큰이 만료됬을 경우

                throw new RuntimeException("expired");
            }
        } catch (Exception e){
            throw new RuntimeException("AES decrypt failed");
        }
    }
    public String refreshToken(String id){
        // 유저 id를 활용해서 생성
        return generate(id, 60 * 60 * 24 * 7);
    }
    public String accessToken(String refreshToken){
        // refreshToken에서 만료기간 확인 후 유저 id 추출해서 생성
        return generate(verify(refreshToken), 60 * 60 * 24 * 2);
    }


}
