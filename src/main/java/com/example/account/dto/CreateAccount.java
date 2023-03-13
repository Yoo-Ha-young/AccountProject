package com.example.account.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class CreateAccount {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        @Min(1)  // valid를 위해 조건을 달아줌
        private Long userId;

        @NotNull
        @Min(0) // valid를 위해 조건을 달아줌
        private Long initialBalance;
    }
//요청
//{
//"userId":1,
//"initBalance":100
//}

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long userId; // 사용자 아이디
        private String accountNumber; // 계좌 번호
        private LocalDateTime registeredAt; // 계좌 생성일

        public static Response from (AccountDto accountDto) {
            return Response.builder()
                    .userId(accountDto.getUserId())
                    .accountNumber(accountDto.getAccountNumber())
                    .registeredAt(accountDto.getRegisteredAt())
                    .build();
        }
    }
//응답
//{
//"userId":1,
//"accountNumber":"1234567890",
//"registeredAt":"2022-06-01T23:26:14.671859"
//}
}
