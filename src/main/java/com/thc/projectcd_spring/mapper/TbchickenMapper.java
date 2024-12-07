package com.thc.projectcd_spring.mapper;

import com.thc.projectcd_spring.dto.DefaultDto;
import com.thc.projectcd_spring.dto.TbchickenCommentDto;
import com.thc.projectcd_spring.dto.TbchickenDto;

import java.util.List;


public interface TbchickenMapper {
    List<TbchickenDto.ChickenResDto> getFilteredChicken(TbchickenDto.ChickenReqDto param);
    List<TbchickenCommentDto.ChickenCommentResDto> getChickenComments(TbchickenCommentDto.ChickenCommentReqDto param);
}
