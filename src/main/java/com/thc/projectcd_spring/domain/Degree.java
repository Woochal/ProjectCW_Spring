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
@Table(name = "degree") // 이 엔티티가 매핑될 데이터베이스 테이블의 이름을 지정
@Entity
public class Degree {

    @Id
    @Column(length = 32, columnDefinition = "CHAR(32)")
    private String id;

    @Column(length = 191, nullable = false, unique = true)
    private String degreeName;

    @Builder.Default
    // @OneToMany - RoleType 엔티티와(One) TbuserRoleType(Many) 엔티티 간의 일대다 관계임을 표시
    // fetch = FetchType.LAZY: RoleType 객체를 조회할 때 관련된 TbuserRoleType 객체는 필요할 때만 로드
    // cascade = CascadeType.REMOVE: RoleType이 삭제될 때 관련된 TbuserRoleType 객체도 함께 삭제
    @OneToMany(mappedBy = "degree", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TbuserDegree> tbuserDegree = new ArrayList<>();

    public static Degree of(String id, String degreeName) {
        return new Degree(id, degreeName, null);
    }

}