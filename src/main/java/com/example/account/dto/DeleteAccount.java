package com.example.account.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


public class DeleteAccount {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        @Min(1)  // valid를 위해 조건을 달아줌
        private Long userId;

        @NotBlank
        @Size(min = 10, max = 10) // valid를 위해 조건을 달아줌
        private String accountNumber;
    }
    //요청
    //userID : 1
    //accountNumber : 1000000000

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long userId; // 사용자 아이디
        private String accountNumber; // 계좌 번호
        private LocalDateTime unRegisteredAt; // 계좌 해지일

        public static Response from (AccountDto accountDto) {
            return Response.builder()
                    .userId(accountDto.getUserId())
                    .accountNumber(accountDto.getAccountNumber())
                    .unRegisteredAt(accountDto.getUnregisteredAt())
                    .build();
        }
    }
    //응답
    // userID : 1
    // accountNumber : 1000000000
    // unRegisteredAt :"2022-06-04T20:26:14.671859
}
