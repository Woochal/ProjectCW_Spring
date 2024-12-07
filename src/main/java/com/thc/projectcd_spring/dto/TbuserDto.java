package com.thc.projectcd_spring.dto;

import com.thc.projectcd_spring.domain.Tbuser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

public class TbuserDto {


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
    public static class UidReqDto {
        @NotNull
        @NotEmpty
        @Size(max=400)
        private String username;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConfirmReqDto {
        @NotNull
        @NotEmpty
        @Size(max=400)
        private String username;
        @NotNull
        @NotEmpty
        private String number;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignupReqDto {
        @NotNull
        @NotEmpty
        @Size(max=400)
        private String username;
        @NotNull
        @NotEmpty
        @Size(max=400)
        private String password;

        public Tbuser toEntity() {
            return Tbuser.of(username, password ,null);
        }
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginReqDto{
        @NotNull
        @NotEmpty
        @Size(max=400)
        private String username;
        @NotNull
        @NotEmpty
        @Size(max=100)
        private String password;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccessTokenReqDto {
        private String refreshToken;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterResDto {
        private String id;
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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserReqDto extends DefaultDto.GetReqDto {
        private String username;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserResDto extends DefaultDto.GetResDto {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserListReqDto{
        private String id;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserListResDto {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserNicknameReqDto {
        private String nickname;
    }


}
