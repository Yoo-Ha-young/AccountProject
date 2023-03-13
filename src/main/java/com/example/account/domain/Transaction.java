package com.example.account.domain;

import com.example.account.type.TransactionResultType;
import com.example.account.type.TransactionType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction {

    @Id @GeneratedValue
    private Long id; // pk
    private TransactionType transactionType; // 거래의 종류(사용, 사용취소)
    private TransactionResultType transactionResultType; // 거래 결과(성공, 실패)

    @ManyToOne
    private Account account; // 거래가 발생한 계좌(N:1 연결)

    private Long amount; // 거래 금액
    private Long balanceSnapshot; // 거래 후 계좌 잔액
    private String transactionId; // 계좌 해지일시
    private LocalDateTime transactedAt; // 거래일시
    private LocalDateTime createdAt; // 생성일시
    private LocalDateTime updatedAt; // 최종 수정일시
}
