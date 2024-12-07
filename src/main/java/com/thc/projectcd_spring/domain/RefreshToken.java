package com.thc.projectcd_spring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@ToString(callSuper = true)
// 해당 컬럼들에 인덱스 설정(DB)
@Table(indexes = {
        @Index(columnList = "createdAt")
        ,@Index(columnList = "modifiedAt")
})
public class RefreshToken extends AuditingFields {

    @Setter
    @Column(nullable = false)
    private String tbuserId;

    @Setter
    @Column(nullable = false)
    private String content;

    protected RefreshToken() {}
    private RefreshToken(String tbuserId, String content) {
        this.tbuserId = tbuserId;
        this.content = content;
    }

    public static RefreshToken of(String tbuserId, String content) {
        return new RefreshToken(tbuserId, content);
    }

}
