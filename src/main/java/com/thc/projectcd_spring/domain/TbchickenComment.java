package com.thc.projectcd_spring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
public class TbchickenComment extends AuditingFields {
    @Setter @Column(nullable = false, length = 100) private String chickenId;
    @Setter @Column(nullable = false, length = 100) private String userId;
    @Setter @Column(nullable = false, length = 2000) private String content;
    @Setter @Column(nullable = false) private Integer likes;
    @Setter @Column(nullable = false) private Integer disLikes;
    @Setter @Column(nullable = false) private Double score;


    protected TbchickenComment() {}
    private TbchickenComment(String chickenId, String userId, String content, Integer likes, Integer disLikes, Double score) {
        this.chickenId = chickenId;
        this.userId = userId;
        this.content = content;
        this.likes = likes;
        this.disLikes = disLikes;
        this.score = score;
    }

    public static TbchickenComment of(String chickenId, String userId, String content, Integer likes, Integer disLikes, Double score) {
        return new TbchickenComment(chickenId, userId, content, likes, disLikes, score);
    }

//    public TbtestuserDto.RegisterResDto toRegisterResDto() {
//        return TbtestuserDto.RegisterResDto.builder()
//                .id(this.getId())
//                .build();
//    }
}
