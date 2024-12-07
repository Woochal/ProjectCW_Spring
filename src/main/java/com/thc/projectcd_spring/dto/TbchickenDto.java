package com.thc.projectcd_spring.dto;

import com.thc.projectcd_spring.domain.Tbchicken;
import com.thc.projectcd_spring.domain.Tbuser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

public class TbchickenDto {


    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChickenResDto {

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String chicken_id;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String chickenname;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private Integer price;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String brand;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String type;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String batter;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private Integer spiciness;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String toping;

        @Size(max=100)
        private Integer gramsB;

        @Size(max=100)
        private Integer gramsA;

        @Size(max=100)
        private Integer gramsE;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String origin;

        @Size(max=100)
        private Double kcal;

        @Size(max=100)
        private Double protein;

        @Size(max=100)
        private Double sugar;

        @Size(max=100)
        private Double fat;

        @Size(max=100)
        private Double sodium;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private Double score;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private Integer commentCount;

        @Size(max=255)
        private String imageUrl;
    }


    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    // 필터 옵션으로 치킨 리스트 요청할 때 사용
    public static class ChickenReqDto {

        @NotNull
        @NotEmpty
        @Size(max=40)
        private String chickenname;

        @NotNull
        @NotEmpty
        @Size(max=40)
        private String sortType;

        @NotNull
        @NotEmpty
        @Size(max=40)
        private String type;

        @NotNull
        @NotEmpty
        @Size(max=40)
        private String brand;

        @NotNull
        @NotEmpty
        @Size(max=40)
        private String spiciness;

        @NotNull
        @NotEmpty
        @Size(max=40)
        private String price;

    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddChickenReqDto {


        @NotNull
        @NotEmpty
        @Size(max=100)
        private String chickenname;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private Integer price;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String brand;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String type;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String batter;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private Integer spiciness;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String toping;

        @Size(max=100)
        private Integer gramsB;

        @Size(max=100)
        private Integer gramsA;

        @Size(max=100)
        private Integer gramsE;

        @NotNull
        @NotEmpty
        @Size(max=100)
        private String origin;

        @Size(max=100)
        private Double kcal;

        @Size(max=100)
        private Double protein;

        @Size(max=100)
        private Double sugar;

        @Size(max=100)
        private Double fat;

        @Size(max=100)
        private Double sodium;

        @Size(max=255)
        private String imageUrl;

        public Tbchicken toEntity() {
            return Tbchicken.of(chickenname, price, brand, type, batter, spiciness, toping, gramsB, gramsA, gramsE, origin, kcal, protein, sugar, fat, sodium, 0.0,0, imageUrl);
        }

    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddChickenResDto {


        @NotNull
        @NotEmpty
        @Size(max=100)
        private String chicken_id;

    }


}
