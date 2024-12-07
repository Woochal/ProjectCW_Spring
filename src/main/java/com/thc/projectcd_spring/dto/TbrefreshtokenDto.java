package com.thc.projectcd_spring.dto;

import com.thc.projectcd_spring.domain.Tbrefreshtoken;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

public class TbrefreshtokenDto {

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateReqDto{
        @Schema(description = "tbuserId", example="")
        @NotNull
        @NotEmpty
        @Size(max=400)
        private String tbuserId;
        @Schema(description = "token", example="")
        @NotNull
        @NotEmpty
        private String token;

        public Tbrefreshtoken toEntity(){
            return Tbrefreshtoken.of(tbuserId, token);
        }
    }
    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResDto{
        private String id;
    }

}
