package com.thc.projectcd_spring.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Table(name = "tbuser_degree",
        indexes = {
                @Index(name = "IDX_tbuserdegree_createdAt", columnList = "createdAt")
                ,@Index(name = "IDX_tbuserdegree_modifiedAt", columnList = "modifiedAt")
        }
)
@Entity
public class TbuserDegree extends AuditingFields {

    @ManyToOne //  TbuserDegree(Many) 엔티티와 tbuser(One)가 다대일 관계임을 표시.
    @JoinColumn(name = "tbuser_id") // 외래 키 컬럼을 정의. 이 경우 tbuser_id 컬럼이 Tbuser 엔티티를 참조합니다.
    private Tbuser tbuser;

    @ManyToOne
    @JoinColumn(name = "degree_id")
    private Degree degree;

    protected TbuserDegree(){}
    private TbuserDegree(Tbuser tbuser, Degree degree) {
        this.tbuser = tbuser;
        this.degree = degree;
    }

    public static TbuserDegree of(Tbuser tbuser, Degree degree) {
        return new TbuserDegree(tbuser, degree);
    }

}
