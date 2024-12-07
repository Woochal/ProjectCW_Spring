package com.thc.projectcd_spring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
public class Tbemail extends AuditingFields {
    @Setter @Column(nullable = false, unique = true) private String username;
    @Setter @Column(nullable = false) private String number;
    @Setter @Column(nullable = false) private String due;

    protected Tbemail() {}
    private Tbemail(String username, String number, String due) {
        this.username = username;
        this.number = number;
        this.due = due;
    }

    public static Tbemail of(String username, String number, String due) {
        return new Tbemail(username, number, due);
    }

//    public TbtestuserDto.RegisterResDto toRegisterResDto() {
//        return TbtestuserDto.RegisterResDto.builder()
//                .id(this.getId())
//                .build();
//    }
}
