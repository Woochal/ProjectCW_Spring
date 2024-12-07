package com.thc.projectcd_spring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
// 이 클래스가 상속되는 다른 엔티티 클래스에서 해당 필드와 메서드를 사용할 수 있도록 해주는 annotation
// 때문에 이 클래스는 직접적으로 데이터베이스 테이블과 매핑되지 않지만, 공통 필드를 제공하는 역할을 할 수 있게 된다.
@MappedSuperclass
// @EntityListeners를 사용하면 엔티티의 생명주기 이벤트(엔티티가 생성되거나 수정될 때)를 감지하여 특정 메서드를 호출할 수 있다
// AuditingEntityListener.class 감사(Auditing) 기능을 사용하여 생성 및 수정 날짜를 자동으로 넣게해주는 리스너
// Application 클래스에 @EnableJpaAuditing 어노테이션을 추가로 넣어줘야한다.
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingFields {

    // @Id가 붙은 필드를 기본 키로 인식하고, 해당 필드에 대한 매핑을 자동으로 처리함.
    // 따라서 @Column 애너테이션을 생략해도 기본 키 필드로서의 속성이 유지된다.
    // 또한 기본으로 nullable = false 이다.
    @Id
    // id 값은 PK이기 떄문에 다른 상속한 클래스에서 제어할 수 없게 하기 위해 private
    private String id;

    @Column(nullable = false)
    @Setter
    protected String deleted;

    @Column(nullable = true)
    @Setter
    protected String process;

    @Column(nullable = false)
    // 엔티티가 생성될 때 자동으로 현재 날짜와 시간으로 설정
    @CreatedDate
    // 날짜/시간 형식을 지정
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    protected LocalDateTime createdAt;

    @Column(nullable = false)
    // 엔티티가 수정될 때 자동으로 현재 날짜와 시간으로 설정
    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    protected LocalDateTime modifiedAt;

    //  엔티티가 저장되기 전에 호출되는 메서드를 지정하는 annotation
    @PrePersist
    public void onPrePersist() {
        this.id = UUID.randomUUID().toString().replace("-", "");
        // deleted 필드 No로 초기화
        this.deleted = "N";
    }
}
