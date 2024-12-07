package com.thc.projectcd_spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thc.projectcd_spring.domain.Tbuser;
import com.thc.projectcd_spring.dto.TbuserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Transactional // 모든 데이터베이스 작업이 하나의 트랜잭션으로 처리
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final AuthService authService;
    private final ExternalProperties externalProperties;


    @Transactional
    // 로그인하려는 사용자의 자격을 확인해 토큰을 발급하는 함수. "/api/login" 으로 들어오는 요청에 실행된다.
    // 생성된 Authentication이 SecurityContextHolder에 등록되어 권한처리가 가능하게 한다.
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter executed");

        // 인증 결과를 저장할 변수
        Authentication authentication = null;
        // 로그인 요청의 사용자 정보를 담을 DTO
        TbuserDto.LoginReqDto tbuserLoginDto = null;

        // 1번. 로그인에 필요한 아이디(username)이랑 비번(password)가 입력 되었는지 먼저 확인!!!
        try {
            // objectMapper는 JSON 데이터를 Java 객체로 변환하거나 Java 객체를 JSON으로 변환하는 데 사용.
            // 이 경우, HTTP 요청 본문에 포함된 JSON 데이터를 Java 객체로 변환하는 데 사용
            // objectMapper.readValue(InputStream src, Class<T> valueType) 메서드는 입력 스트림의 내용을 지정된 클래스 타입으로 변환
            tbuserLoginDto = objectMapper.readValue(request.getInputStream(), TbuserDto.LoginReqDto.class);
        } catch (IOException e) {
            System.out.println("1. login attemptAuthentication : Not Enough Parameters?!");
            //e.printStackTrace();
        }

        // 2번. 로그인에 필요한 아이디(username)이랑 비번(password)으로 실제 존재하는 고객인지 확인!!!
        try {
//            System.out.println("check idpw" + tbuserLoginDto.getUserId() + tbuserLoginDto.getUserPw());

            // 입력한 정보로 Authentication 객체를 생성을 위한 토큰 객체 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tbuserLoginDto.getUsername(), tbuserLoginDto.getPassword());
            // DB에 실제 존재하는 고객인지 확인한 후 토큰 객체를 통해 Authentication 객체 생성.
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            System.out.println("2. login attemptAuthentication : username, password Not Matched?!");
            e.printStackTrace();
            throw new AuthenticationServiceException("Invalid username or password", e);
        }

        return authentication;
    }

    @Override
    // 로그인 완료시 호출되는 함수. refreshToken을 발급해 HttpServletResponse에 담는다.
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        System.out.println("Auth - successfulAuthentication");

        PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
        Tbuser user = principalDetails.getTbuser();

        // TbuserId로 리프레시토큰 발급
        System.out.println("principalDetails.getTbuser().getId() : " + principalDetails.getTbuser().getId());
        String refreshToken = authService.createRefreshToken(principalDetails.getTbuser().getId());
        System.out.println("Auth - refreshToken : " + refreshToken);
        String accessToken = authService.issueAccessToken(refreshToken);
        System.out.println("Auth - accessToken : " + accessToken);
        String userId = principalDetails.getTbuser().getId();
        System.out.println("Auth - userId : " + userId);

        // header에 담아서 전달!!
        response.addHeader(externalProperties.getRefreshKey(), externalProperties.getTokenPrefix() + refreshToken);
        response.addHeader(externalProperties.getAccessKey(), externalProperties.getTokenPrefix() + accessToken);
        response.addHeader("userId", userId);

        // role 추가
        String userRole = user.getRoleList().get(0).getRoleType().getTypeName();  // 단일 역할 가정
        response.addHeader("userRole", userRole);

        // CORS 정책에 따라 특정 헤더는 클라이언트에서 접근할 수 없다. 때문에 클라이언트에서도 지정한 헤더를 확인할 수 있게 설정해야 한다.
        response.setHeader("Access-Control-Expose-Headers", "Authorization, RefreshToken, userId, userRole");


        // 바디에도 담아줍시다.
        //TbuserDto.TbuserLoginDto tbuserLoginDto = new TbuserDto.TbuserLoginDto(externalProperties.getTokenPrefix() + refreshToken, externalProperties.getTokenPrefix() + accessToken);
        //response.getWriter().write(tbuserLoginDto.toString());
        //response.getWriter().write(externalProperties.getTokenPrefix() + refreshToken);

        System.out.println("successfulAuthentication : login success?!");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 비밀번호가 틀렸을 때 401 Unauthorized 상태 코드 반환
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        // 오류 메시지를 JSON 형태로 작성
        String errorMessage = "{\"error\": \"Invalid username or password\"}";
        response.getWriter().write(errorMessage);
    }

}
