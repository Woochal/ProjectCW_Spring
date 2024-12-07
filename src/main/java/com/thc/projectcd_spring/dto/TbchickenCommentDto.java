package com.thc.projectcd_spring.dto;

import com.thc.projectcd_spring.domain.Tbchicken;
import com.thc.projectcd_spring.domain.TbchickenComment;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

public class TbchickenCommentDto {

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TextResDto {
        private String text;
    }


    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChickenCommentResDto {
        @NotNull
        @NotEmpty
        @Size(max=100)
        private String nickname;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String degree;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String content;

        @NotNull
        @NotEmpty
        private Integer likes;

        @NotNull
        @NotEmpty
        private Integer disLikes;

        @NotNull
        @NotEmpty
        private Integer score;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String createdAt;

    }


    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    // 필터 옵션으로 치킨 리스트 요청할 때 사용
    public static class ChickenCommentReqDto {

        @NotNull
        @NotEmpty
        @Size(max=40)
        private String chicken_id;

    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    // 필터 옵션으로 치킨 리스트 요청할 때 사용
    public static class AddChickenCommentReqDto {

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String chickenId;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String userId;

        @NotNull
        @NotEmpty
        @Size(max=2000)
        private String content;

        @NotNull
        @NotEmpty
        @Size(max=10)
        private Double score;

        public TbchickenComment toEntity() {
            return TbchickenComment.of(chickenId, userId, content, 0, 0, score );
        }

    }


}
