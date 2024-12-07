package com.thc.projectcd_spring.domain;

import com.thc.projectcd_spring.dto.RefreshtokenDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Table(indexes = {
        @Index(columnList = "deleted")
        ,@Index(columnList = "process")
        ,@Index(columnList = "createdAt")
        ,@Index(columnList = "modifiedAt")
})
@Entity
public class Tbrefreshtoken extends AuditingFields {

    @Setter @Column(nullable = false, unique = true) private String tbuserId;
    @Setter @Column(nullable = false) private String token;

    protected Tbrefreshtoken(){}
    private Tbrefreshtoken(String tbuserId, String token) {
        this.tbuserId = tbuserId;
        this.token = token;
    }
    public static Tbrefreshtoken of(String tbuserId, String token) {
        return new Tbrefreshtoken(tbuserId, token);
    }

    public RefreshtokenDto.CreateResDto toCreateResDto() {
        return RefreshtokenDto.CreateResDto.builder().id(this.getId()).build();
    }
}
