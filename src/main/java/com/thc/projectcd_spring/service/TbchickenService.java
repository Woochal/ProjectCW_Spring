package com.thc.projectcd_spring.service;


import com.thc.projectcd_spring.dto.DefaultDto;
import com.thc.projectcd_spring.dto.TbchickenCommentDto;
import com.thc.projectcd_spring.dto.TbchickenDto;
import com.thc.projectcd_spring.dto.TbuserDto;

import java.util.List;

// service annotation은 클래스에서 해야한다.
public interface TbchickenService {

    List<TbchickenDto.ChickenResDto> getFilteredChicken(TbchickenDto.ChickenReqDto param);
    List<TbchickenCommentDto.ChickenCommentResDto> getChickenComments(TbchickenCommentDto.ChickenCommentReqDto param);
    TbchickenDto.AddChickenResDto addChicken(TbchickenDto.AddChickenReqDto param);
    TbchickenCommentDto.TextResDto addChickenComment(TbchickenCommentDto.AddChickenCommentReqDto param);
}
