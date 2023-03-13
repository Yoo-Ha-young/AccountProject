package com.example.account.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity @EntityListeners(AuditingEntityListener.class)
public class AccountUser {
    @Id @GeneratedValue
    private Long id;
    private String name;

    @CreatedDate
    private LocalDateTime createdAt; // 생성일시

    @LastModifiedDate
    private LocalDateTime updatedAt; // 최종 수정일시
}
