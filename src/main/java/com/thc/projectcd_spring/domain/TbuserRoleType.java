package com.thc.projectcd_spring.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Table(name = "tbuser_role_type",
        indexes = {
                @Index(name = "IDX_tbuserroletype_createdAt", columnList = "createdAt")
                ,@Index(name = "IDX_tbuserroletype_modifiedAt", columnList = "modifiedAt")
        }
)
@Entity
public class TbuserRoleType extends AuditingFields {

    @ManyToOne //  TbuserRoleType(Many) 엔티티와 tbuser(One)가 다대일 관계임을 표시.
    @JoinColumn(name = "tbuser_id") // 외래 키 컬럼을 정의. 이 경우 tbuser_id 컬럼이 Tbuser 엔티티를 참조합니다.
    private Tbuser tbuser;

    @ManyToOne
    @JoinColumn(name = "role_type_id")
    private RoleType roleType;

    protected TbuserRoleType(){}
    private TbuserRoleType(Tbuser tbuser, RoleType roleType) {
        this.tbuser = tbuser;
        this.roleType = roleType;
    }

    public static TbuserRoleType of(Tbuser tbuser, RoleType roleType) {
        return new TbuserRoleType(tbuser, roleType);
    }

}
