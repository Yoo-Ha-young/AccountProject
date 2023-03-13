package com.example.account.dto;
import lombok.*;

// 계좌 정보

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AccountInfo {
        private String accountNumber;
        private Long balance;
}
