package com.thc.projectcd_spring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
public class Tbuser extends AuditingFields {
    @Setter @Column(nullable = false, length = 400) private String username;
    @Setter @Column(nullable = true, length = 10000) private String password;
    @Setter @Column(nullable = true, length = 8) private String nickname;

    //fetch 타입 바꾸고, toString 순환 참조 수정
    @OneToMany(mappedBy = "tbuser", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<TbuserRoleType> tbuserRoleType = new ArrayList<>();

    //권한 관련한 기능 추가
    public List<TbuserRoleType> getRoleList(){
        if(!this.tbuserRoleType.isEmpty()){
            return tbuserRoleType;
        }
        return new ArrayList<>();
    }

    protected Tbuser() {}
    private Tbuser(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public static Tbuser of(String username, String password, String nickname) {
        return new Tbuser(username, password, nickname);
    }

//    public TbtestuserDto.RegisterResDto toRegisterResDto() {
//        return TbtestuserDto.RegisterResDto.builder()
//                .id(this.getId())
//                .build();
//    }
}
