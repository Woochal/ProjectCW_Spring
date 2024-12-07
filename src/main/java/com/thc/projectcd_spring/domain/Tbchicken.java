package com.thc.projectcd_spring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
public class Tbchicken extends AuditingFields {
    @Setter @Column(nullable = false, length = 100) private String chickenname;
    @Setter @Column(nullable = false, length = 100) private Integer price;
    @Setter @Column(nullable = false,  length = 100) private String brand;
    @Setter @Column(nullable = false,  length = 100) private String type;
    @Setter @Column(nullable = false,  length = 100) private String batter;
    @Setter @Column(nullable = false,  length = 100) private Integer spiciness;
    @Setter @Column(nullable = false,  length = 100) private String toping;
    @Setter @Column(nullable = true,  length = 100) private Integer gramsB;
    @Setter @Column(nullable = true,  length = 100) private Integer gramsA;
    @Setter @Column(nullable = true,  length = 100) private Integer gramsE;
    @Setter @Column(nullable = false,  length = 100) private String origin;
    @Setter @Column(nullable = true,  length = 100) private Double kcal;
    @Setter @Column(nullable = true,  length = 100) private Double protein;
    @Setter @Column(nullable = true,  length = 100) private Double sugar;
    @Setter @Column(nullable = true,  length = 100) private Double fat;
    @Setter @Column(nullable = true,  length = 100) private Double sodium;
    @Setter @Column(nullable = false,  length = 100) private Double score;
    @Setter @Column(nullable = false,  length = 100) private Integer commentCount;
    @Setter @Column(length = 255) private String imageUrl;


    protected Tbchicken() {}
    private Tbchicken(String chickenname, Integer price, String brand, String type, String batter, Integer spiciness, String toping, Integer gramsB, Integer gramsA, Integer gramsE, String origin, Double kcal, Double protein, Double sugar, Double fat, Double sodium, Double score, Integer commentCount, String imageUrl ) {
        this.chickenname = chickenname;
        this.price = price;
        this.brand = brand;
        this.type = type;
        this.batter = batter;
        this.spiciness = spiciness;
        this.toping = toping;
        this.gramsB = gramsB;
        this.gramsA = gramsA;
        this.gramsE = gramsE;
        this.origin = origin;
        this.kcal = kcal;
        this.protein = protein;
        this.sugar = sugar;
        this.fat = fat;
        this.sodium = sodium;
        this.score = score;
        this.commentCount = commentCount;
        this.imageUrl = imageUrl;

    }

    public static Tbchicken of(String chickenname, Integer price, String brand, String type, String batter, Integer spiciness, String toping, Integer gramsB, Integer gramsA, Integer gramsE, String origin, Double kcal, Double protein, Double sugar, Double fat, Double sodium, Double score, Integer commentCount, String imageUrl ) {
        return new Tbchicken(chickenname, price, brand, type, batter, spiciness, toping, gramsB, gramsA, gramsE, origin, kcal, protein, sugar, fat, sodium, score, commentCount, imageUrl );
    }

//    public TbtestuserDto.RegisterResDto toRegisterResDto() {
//        return TbtestuserDto.RegisterResDto.builder()
//                .id(this.getId())
//                .build();
//    }
}
