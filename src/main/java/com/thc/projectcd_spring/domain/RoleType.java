package com.thc.projectcd_spring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roleType") // 이 엔티티가 매핑될 데이터베이스 테이블의 이름을 roleType으로 지정
@Entity
public class RoleType {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @Column(length = 32, columnDefinition = "CHAR(32)")
    private String id;

    @Column(length = 191, nullable = false, unique = true)
    private String typeName;

    @Builder.Default
    // @OneToMany - RoleType 엔티티와(One) TbuserRoleType(Many) 엔티티 간의 일대다 관계임을 표시
    // fetch = FetchType.LAZY: RoleType 객체를 조회할 때 관련된 TbuserRoleType 객체는 필요할 때만 로드
    // cascade = CascadeType.REMOVE: RoleType이 삭제될 때 관련된 TbuserRoleType 객체도 함께 삭제
    @OneToMany(mappedBy = "roleType", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TbuserRoleType> tbuserRoleType = new ArrayList<>();

    public static RoleType of(String id, String typeName) {
        return new RoleType(id, typeName, null);
    }

    public boolean isAdmin() {
        return this.typeName.equals(ROLE_ADMIN);
    }

}