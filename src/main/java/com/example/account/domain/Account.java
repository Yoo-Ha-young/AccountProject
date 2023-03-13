package com.example.account.domain;

import com.example.account.type.AccountStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity // 엔터티를 바탕으로 데이터베이스의 테이블과 매핑
@EntityListeners(AuditingEntityListener.class)
public class Account { // Account 테이블이 생성됨

    @Id // 애노테이션은 해당 필드가 엔티티의 기본 키(primary key)임을 나타냅니다.
    @GeneratedValue // 기본 키(primary key) 값을 자동으로 생성하기 위해 사용되는 애노테이션 중 하나
    private Long id;
    @ManyToOne
    private AccountUser accountUser;
    private String accountNumber; // 계좌번호
    @Enumerated(EnumType.STRING) // 원래는 값이 숫자로 들어오는데 이걸 사용해서 enum에 적용했던 문자열로 반환
    private AccountStatus accountStatus;

    private Long balance; // 계좌 잔액
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    @CreatedDate
    private LocalDateTime createdAt; // 생성일시
    @LastModifiedDate
    private LocalDateTime updatedAt; // 최종 수정일시

    public Account(Long id, AccountUser accountUser, String accountNumber, AccountStatus accountStatus, Long balance, LocalDateTime registeredAt, LocalDateTime unregisteredAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.accountUser = accountUser;
        this.accountNumber = accountNumber;
        this.accountStatus = accountStatus;
        this.balance = balance;
        this.registeredAt = registeredAt;
        this.unregisteredAt = unregisteredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Account() {
    }
}
// AccountRepository : JPA 인터페이스로 연결
// 엔터티의 DB를 저장해주기 위해서는 JPA의 repository를 사용
// JPA는 객체를 데이터베이스 테이블에 매핑하는 방식으로 동작합니다.
// 이를 위해 JPA에서는 엔티티(Entity)라는 개념을 사용합니다.
// 엔티티는 데이터베이스 테이블에 대응되는 객체입니다.
// JPA에서는 객체와 테이블 간의 매핑 정보를 애노테이션으로 지정하거나
// XML 파일로 정의합니다.
// JPA는 SQL을 사용하지 않고도 객체를 데이터베이스에 저장하고,
// 검색하고, 수정하고, 삭제하는 등의 작업을 수행할 수 있습니다.
//JPA는 객체를 데이터베이스에 저장하는 과정에서 내부적으로 SQL을 생성하고
// 실행합니다. 개발자는 SQL을 직접 작성할 필요가 없으며,
// 객체지향적인 방식으로 데이터를 다룰 수 있습니다.
// JPA는 객체 간의 관계를 관리하고, 객체를 로딩할 때 즉시 연관된
// 객체들도 함께 로딩할 수 있는 기능을 제공합니다.
// 또한, JPA는 지연 로딩(Lazy Loading)을 지원하여 성능을
// 최적화할 수 있습니다.