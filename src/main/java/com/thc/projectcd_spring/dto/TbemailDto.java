package com.thc.projectcd_spring.dto;

import com.thc.projectcd_spring.domain.Tbemail;
import lombok.*;

public class TbemailDto {



    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateReqDto {
        private String username;
        private String number;
        private String due;

        public Tbemail toEntity() {
            return Tbemail.of(username, number, due);
        }
    }




}
