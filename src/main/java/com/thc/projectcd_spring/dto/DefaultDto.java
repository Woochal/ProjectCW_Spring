package com.thc.projectcd_spring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

public class DefaultDto {

    // 생성자를 가독성 좋게 사용할 수 있게 해주는 어노테이션
    // https://pamyferret.tistory.com/67
    @SuperBuilder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetReqDto{
        @NotNull
        @NotEmpty
        private String id;
    }

    @SuperBuilder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetResDto{
        private String id;
        private String deleted;
        private String process;
        private String createdAt;
        private String modifiedAt;
    }
}
