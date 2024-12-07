package com.thc.projectcd_spring.service.impl;

import com.thc.projectcd_spring.domain.*;
import com.thc.projectcd_spring.dto.DefaultDto;
import com.thc.projectcd_spring.dto.TbemailDto;
import com.thc.projectcd_spring.dto.TbuserDto;
import com.thc.projectcd_spring.repository.*;
import com.thc.projectcd_spring.service.TbuserService;
import com.thc.projectcd_spring.util.NowDate;
import com.thc.projectcd_spring.util.SendEmail;
import com.thc.projectcd_spring.util.TokenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thc.projectcd_spring.util.AES256Cipher;
import java.util.Arrays;
import java.util.List;

@Service
public class TbuserServiceImpl implements TbuserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TbuserRepository tbuserRepository;
    public TbemailRepository tbemailRepository;
    public SendEmail sendEmail;
    public TokenFactory tokenFactory;
    public RefreshTokenRepository refreshtokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleTypeRepository roleTypeRepository;
    private final TbuserRoleTypeRepository userRoleTypeRepository;

//    // 의존성 주입 중 생성자 주입 방식. 생성자가 1개만 있을 경우에 @Autowired를 생략해도 주입이 가능.
//    // repository와 mapper등이 interface인데 바로 사용 가능한 것 처럼 느껴지는 이유는,
//    // Spring Data JPA와 같은 프레임워크를 사용하면 이 인터페이스를 구현하는 클래스가 자동으로 생성해주기 때문이다.
//    // 생성자 파라미터를 이렇게 구성해두기만 하면, 스프링에서 알아서 자동 생성한 클래스와 생성자를 연결해준다. (컴포넌트이기 때문에)
    public TbuserServiceImpl(TbuserRepository tbuserRepository, TbemailRepository tbemailRepository, SendEmail sendEmail, TokenFactory tokenFactory,RefreshTokenRepository refreshtokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleTypeRepository roleTypeRepository, TbuserRoleTypeRepository userRoleTypeRepository) {
        this.tbuserRepository = tbuserRepository;
        this.tbemailRepository = tbemailRepository;
        this.sendEmail = sendEmail;
        this.tokenFactory = tokenFactory;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.refreshtokenRepository = refreshtokenRepository;
        this.roleTypeRepository = roleTypeRepository;
        this.userRoleTypeRepository = userRoleTypeRepository;

    }

    // 메서드 내에서 데이터베이스 작업이 하나의 트랜잭션으로 처리되도록 보장.
    @Transactional
    @Override
    public TbuserDto.TextResDto sendEmail(TbuserDto.UidReqDto param) {

        // 유저가 입력한 이메일아이디가 이미 존재하는지 확인
        Tbuser tbuser = tbuserRepository.findByUsername(param.getUsername());

        if (tbuser == null) {

            // 첫 유저인 경우

            // 인증번호 만들기
            String number = "";
            for (int i = 0; i < 6; i++) {
                int random_0to9 = (int) (Math.random() * 10);
                number += random_0to9 + "";

            }

            // 인증번호 보내기
            try {

                // 인증번호 만료시간 생성. 180초로
                String due = new NowDate().getDue(180);

                // 이미 인증번호를 생성한 적이 있는 아이디(이메일)인지 확인
                Tbemail tbemail = tbemailRepository.findByUsername(param.getUsername());

                if (tbemail == null) {
                    // 처음 인증번호를 생성하는 경우
                    tbemailRepository.save(TbemailDto.CreateReqDto.builder().username(param.getUsername()).number(number).due(due).build().toEntity());
                } else {

                    // 회원가입은 안한 유저이지만 인증번호는 생성한적이 있는 경우

                    // 새로운 인증번호와 만료시간 할당
                    tbemail.setNumber(number);
                    tbemail.setDue(due);

                    // 인증까지 하고 나서 회원가입을 안한 유저가 있을 수 있어서
                    if("done".equals(tbemail.getProcess())){
                        tbemail.setProcess("yet");
                    }

                    // 새로운 인증번호와 만료시간 DB에 저장
                    tbemailRepository.save(tbemail);
                }
                System.out.println("number : " + number);

                // 유저 이메일로 인증번호 발송
                sendEmail.send(param.getUsername(), "이메일 인증입니다", "인증번호 : " + number);
            } catch (Exception e) {
                System.out.println("error : " + e.getMessage());
            }
            return TbuserDto.TextResDto.builder().text("true").build();
        } else {
            // 첫 유저가 아닌경우 경우
            return TbuserDto.TextResDto.builder().text("false").build();
        }

    }

    @Override
    public TbuserDto.TextResDto confirmEmail(TbuserDto.ConfirmReqDto param) {

        // 유저가 입력한 정보로 이메일 엔티티 검색
        Tbemail tbemail = tbemailRepository.findByUsernameAndNumber(param.getUsername(), param.getNumber());

        if (tbemail == null) {

            // 인증번호가 올바르지 않거나 이메일이 없는 경우
            return TbuserDto.TextResDto.builder().text("false").build();

        } else {

            String now = new NowDate().getNow();
            String due = tbemail.getDue();

            // 현재 시간이랑, 만료 시간이랑 정렬
            String[] arrayTemp = {now, due};
            Arrays.sort(arrayTemp);
            System.out.println(now + "//" + due + "//  => " + arrayTemp[0]);

            if (now.equals(arrayTemp[1])) {
                // 만료시간이 지났을 때
                return TbuserDto.TextResDto.builder().text("late").build();
            } else {

                // 인증이 다 통과한 경우
                tbemail.setProcess("done");
                tbemailRepository.save(tbemail);
                return TbuserDto.TextResDto.builder().text("true").build();
            }


        }

    }

    @Transactional
    public TbuserDto.TextResDto signup(TbuserDto.SignupReqDto param) {
        Tbemail tbemail = tbemailRepository.findByUsername(param.getUsername());

        if (tbemail == null || !"done".equals(tbemail.getProcess())) {
            return TbuserDto.TextResDto.builder().text("false").build();
        } else {
            // 비밀번호 암호화
            param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));

            // 새로운 유저 저장
            Tbuser newUser = tbuserRepository.save(param.toEntity());

            // 기본 USER 역할 찾기
            RoleType userRole = roleTypeRepository.findByTypeName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("기본 사용자 역할이 없습니다"));

            // 사용자-역할 관계 생성 및 저장
            TbuserRoleType userRoleType = TbuserRoleType.of(newUser, userRole);
            userRoleTypeRepository.save(userRoleType);

            // 이메일 인증 정보 삭제
            tbemailRepository.delete(tbemail);

            return TbuserDto.TextResDto.builder().text("true").build();
        }
    }

//    // 양방향 암호화하는 메서드
//    public String encryptPw(String pw) {
//        String newPw = "";
//        try {
//            String secretKey = "1234567890123456";
//            // 비밀번호 암호화
//            newPw = AES256Cipher.AES_Encode(secretKey, pw);
//        } catch (Exception e) {
//            throw new RuntimeException("AES encrypt failed");
//        }
//        return newPw;
//    }

//    public TbuserDto.TextResDto login(TbuserDto.SignupReqDto param) {
//
//        // 유저가 입력한 비밀번호 암호화
//        param.setPassword(encryptPw(param.getPassword()));
//        // logger.info("S userId : " + param.getUserId() + " encryptPw : " + param.getUserPw());
//
//        // 유저 정보가 있는지 확인
//        Tbuser tbuser = tbuserRepository.findByUsernameAndPassword(param.getUsername(), param.getPassword());
//
//        if(tbuser == null) {
//            // 유저 정보가 없는 경우
//            return TbuserDto.TextResDto.builder().text("false").build();
//        }
//        else {
//            // 유저 정보가 있는 경우
//
//            // refreshToken 생성
//            String token = tokenFactory.refreshToken(tbuser.getId());
//
//            // refreshToken이 이미 있는지 확인
//            Tbrefreshtoken tbrefreshtoken = tbrefreshtokenRepository.findByUserId(tbuser.getId());
//
//            if(tbrefreshtoken == null) {
//
//                // refreshToken이 없었다면
//
//                // refreshToken 생성후 저장
//                tbrefreshtokenRepository.save(TbrefreshtokenDto.CreateReqDto.builder().tbuserId(tbuser.getId()).token(token).build().toEntity());
//            }
//            else {
//
//                // refreshToken이 있었다면
//
//                // refreshToken 갱신
//                tbrefreshtoken.setToken(token);
//                tbrefreshtokenRepository.save(tbrefreshtoken);
//            }
//
//
//            // 컨트롤러에게 text: 토큰값 을 리턴함
//            return TbuserDto.TextResDto.builder().text(token).build();
//
//            // return TbuserDto.TextResDto.builder().text("true").build();
//        }
//
//    }


    @Override
    public TbuserDto.CreateResDto logout(DefaultDto.GetReqDto param){
        List<RefreshToken> refreshtoken = refreshtokenRepository.findByTbuserId(param.getId());
        if(refreshtoken == null){ throw new RuntimeException("no data"); }
        refreshtokenRepository.deleteAll(refreshtoken);
        return TbuserDto.CreateResDto.builder().id("logout").build();
    }

    public TbuserDto.TextResDto generateAccessToken(TbuserDto.AccessTokenReqDto param) {

        // AccessToken은 저장할 필요가 없다?
        String accessToken = tokenFactory.accessToken(param.getRefreshToken());
        logger.info("S accessToken : " + accessToken);
        return TbuserDto.TextResDto.builder().text(accessToken).build();
    }
}