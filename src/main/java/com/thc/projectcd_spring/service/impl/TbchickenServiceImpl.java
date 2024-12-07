package com.thc.projectcd_spring.service.impl;

import com.thc.projectcd_spring.domain.*;
import com.thc.projectcd_spring.dto.TbchickenCommentDto;
import com.thc.projectcd_spring.dto.TbchickenDto;
import com.thc.projectcd_spring.dto.TbuserDto;
import com.thc.projectcd_spring.mapper.TbchickenMapper;
import com.thc.projectcd_spring.repository.TbchickenCommentRepository;
import com.thc.projectcd_spring.repository.TbchickenRepository;
import com.thc.projectcd_spring.repository.TbuserRepository;
import com.thc.projectcd_spring.service.TbchickenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbchickenServiceImpl implements TbchickenService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TbchickenMapper tbchickenMapper;
    public TbchickenRepository tbchickenRepository;
    public TbuserRepository tbuserRepository;
    public TbchickenCommentRepository tbchickenCommentRepository;


    // 의존성 주입 중 생성자 주입 방식. 생성자가 1개만 있을 경우에 @Autowired를 생략해도 주입이 가능.
    // repository와 mapper등이 interface인데 바로 사용 가능한 것 처럼 느껴지는 이유는,
    // Spring Data JPA와 같은 프레임워크를 사용하면 이 인터페이스를 구현하는 클래스가 자동으로 생성해주기 때문이다.
    // 생성자 파라미터를 이렇게 구성해두기만 하면, 스프링에서 알아서 자동 생성한 클래스와 생성자를 연결해준다. (컴포넌트이기 때문에)
    public TbchickenServiceImpl(TbchickenMapper tbchickenMapper, TbchickenRepository tbchickenRepository, TbuserRepository tbuserRepository, TbchickenCommentRepository tbchickenCommentRepository) {
      this.tbchickenMapper = tbchickenMapper;
      this.tbchickenRepository = tbchickenRepository;
      this.tbuserRepository = tbuserRepository;
      this.tbchickenCommentRepository = tbchickenCommentRepository;
    }



    @Override
    public List<TbchickenDto.ChickenResDto> getFilteredChicken(TbchickenDto.ChickenReqDto param){
        return tbchickenMapper.getFilteredChicken(param);
    }

    @Override
    public List<TbchickenCommentDto.ChickenCommentResDto> getChickenComments(TbchickenCommentDto.ChickenCommentReqDto param){
        return tbchickenMapper.getChickenComments(param);
    }

    @Override
    public TbchickenDto.AddChickenResDto addChicken(TbchickenDto.AddChickenReqDto param){

        Tbchicken tbchicken = tbchickenRepository.findByChickenname(param.getChickenname());

        if (tbchicken != null) {
            return TbchickenDto.AddChickenResDto.builder().chicken_id("false").build();
        } else {

            // 새로운 치킨 저장
            Tbchicken newChicken = tbchickenRepository.save(param.toEntity());


            return TbchickenDto.AddChickenResDto.builder().chicken_id(newChicken.getId()).build();
        }

    }

    @Override
    public TbchickenCommentDto.TextResDto addChickenComment(TbchickenCommentDto.AddChickenCommentReqDto param){
        tbchickenCommentRepository.save(param.toEntity());
        return TbchickenCommentDto.TextResDto.builder().text("True").build();
    }


}