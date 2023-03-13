package com.example.account.dto;


import com.example.account.domain.Account;
import lombok.*;

import java.time.LocalDateTime;

// DTO: 데이터 전송 객체로, 비즈니스 로직과 뷰 간의 데이터를 전송하는 클래스입니다.
// DTO 패키지: 데이터 전송 객체(DTO) 클래스를 작성합니다.
// 이 클래스들은 데이터베이스에서 가져온 데이터를 Controller에서 View로
// 전달할 때 사용됩니다.
@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class AccountDto {
    private Long userId;
    private String accountNumber;
    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;

    public static AccountDto fromEntity(Account account){
        return AccountDto.builder()
                .userId(account.getAccountUser().getId())
                .accountNumber(account.getAccountNumber())
                .registeredAt(account.getRegisteredAt())
                .unregisteredAt(account.getUnregisteredAt())
                .build();
    }
}
