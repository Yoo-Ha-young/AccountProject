package com.example.account.service;

import com.example.account.domain.Account;
import com.example.account.domain.AccountUser;
import com.example.account.dto.AccountDto;
import com.example.account.exception.AccountException;
import com.example.account.repository.AccountRepository;
import com.example.account.repository.AccountUserRepository;
import com.example.account.type.AccountStatus;
import com.example.account.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.example.account.type.AccountStatus.IN_USE;
import static com.example.account.type.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service // 서비스 타입이라고 명시 해줌
@RequiredArgsConstructor
public class AccountService {
    // 데이터를 저장하도록 함
    // 의존성으로 AccountRepository를 받아야함
    // 데이터는 생성자로 받는데, 생성자를 굳이 써주지 않아도
    // 롬복으로 자동 생성될 수 있게 할 수 있다.
    // @Required 는 반드시 필요한 생성자만을 생성해주는데,
    // final이 들어가면 변경이 될 수 없어서 무조건 값을 받을 수 밖에 없어서
    // @Reqi이 생성자가 반드시 필요하다 생각하고 생성해줌.
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository; // 사용자 정보 저장소

    /**
     * 사용자가 있는지 조회
     * 계좌의 번호를 생성하고
     * 계좌를 저장하고, 그 정보를 넘긴다.
     */
    @Transactional // Account 테이블에 데이터를 생성
    public AccountDto createAccount(Long userId, Long initialBalance) {
        AccountUser accountUser = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(USER_NOT_FOUND));
//                .orElseThrow(() -> new RuntimeException("User Not Found."));
        validateCreateAccount(accountUser);

        String newAccountNumber = accountRepository.findFirstByOrderByIdDesc()
                .map(account -> (Integer.parseInt(account.getAccountNumber())) + 1 + "")
                .orElse("10000000000"); // 현재 계좌가 없으면 임의 값

        return AccountDto.fromEntity(
                accountRepository.save(Account.builder()
                        .accountUser(accountUser)
                        .accountStatus(IN_USE)
                        .accountNumber(newAccountNumber)
                        .balance(initialBalance)
                        .registeredAt(LocalDateTime.now())
                        .build())
        );
    }

    private void validateCreateAccount(AccountUser accountUser) {
        if (accountRepository.countByAccountUser(accountUser) == 10) {
            throw new AccountException(ErrorCode.MAX_ACCOUNT_PER_USER_10);
        }
    }

//    @Transactional // Account 테이블에 데이터를 생성
//    public void createAccount(Long userId, Long initialBalance){
//        Optional<AccountUser> accountUser = accountUserRepository.findById(userId);
//        if(accountUser.isEmpty()) {
//            throw new RuntimeException("User Not Found.");
//        }
//    }

    // ()는 매개변수를 나타내고, ->는 람다식의 구분자
    // 람다식에서 ()->는 매개변수가 없는 람다식을 의미
    // account에 롬복으로 build를 심어줬기 때문에 여기서 Account를 불러올 수 있음.
    // 이것을 직접 호출하지 않고 AccountController를 통해 호출해주게 한다. Endpoint


    //- 파라미터 : 사용자 아이디, 초기 잔액
    //- 정책 : 사용자가 없는 경우, 계좌가 10개(사용자당 최대 보유 가능 계좌 수)인 경우 실패 응답
    //- 성공 응답 : 사용자 아이디, 계좌번호, 등록일시
    @Transactional // 데이터 가져오기
    public Account getAccount(Long id) {
        if (id < 0) {
            throw new RuntimeException("Minus");
        }
        return accountRepository.findById(id).get();
    }


    //파라미터 : 사용자 아이디, 계좌 번호
    //- 정책 : 사용자 또는 계좌가 없는 경우, 사용자 아이디와 계좌 소유주가 다른 경우,
    //계좌가 이미 해지 상태인 경우, 잔액이 있는 경우 실패 응답
    //성공 응답 : 사용자 아이디, 계좌번호, 해지일시
    @Transactional
    public AccountDto deleteAccount(Long userId, String accountNumber) {
        AccountUser accountUser = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(USER_NOT_FOUND));
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ACCOUNT_NOT_FOUND));
        validateDeleteAccount(accountUser, account);

        account.setAccountStatus(AccountStatus.UNREGISTERED);
        account.setUnregisteredAt(LocalDateTime.now());

        return AccountDto.fromEntity(account);
    }

    private void validateDeleteAccount(AccountUser accountUser, Account account) throws AccountException {
        if (!Objects.equals(accountUser.getId(), account.getAccountUser().getId())) {
            throw new AccountException(USER_ACCOUNT_UN_MATCH);
        }
        if (account.getAccountStatus() == AccountStatus.UNREGISTERED) {
            throw new AccountException(ACCOUNT_ALREADY_UNREGISTERED);
        }
        if (account.getBalance() > 0) {
            throw new AccountException(BALANCE_NOT_EMPTY);
        }
    }

    @Transactional
    public List<AccountDto> getAccountByUserId(Long userId) {
        AccountUser accountUser = accountUserRepository.findById(userId)
                .orElseThrow(()->new AccountException(USER_NOT_FOUND));
        List<Account> accounts = accountRepository.findByAccountUser(accountUser);
        return accounts.stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }
}

// @Transactional은 스프링 프레임워크에서 제공하는 애노테이션 중 하나로,
// 메서드 또는 클래스에 적용하여 해당 메서드나 클래스의 실행을 트랜잭션 단위로
// 묶어주는 역할을 합니다.
// 트랜잭션은 데이터베이스의 일관성과 무결성을 보장하기 위한 개념으로,
// 여러 개의 데이터베이스 작업을 논리적으로 하나의 작업으로 묶어서 실행하는 것을 의미합니다.
// 이를 통해 데이터베이스에 대한 안정성과 일관성을 유지할 수 있습니다.
// 또한 @Transactional 애노테이션을 클래스 레벨에 적용할 수도 있으며,
// 이 경우 클래스 내의 모든 public 메서드가 해당 클래스 레벨의
// 트랜잭션 범위 내에서 실행됩니다.
// @Transactional 애노테이션을 사용하면 스프링이 자동으로 트랜잭션을
// 시작하고 커밋 또는 롤백하는 등의 관리 작업을 대신 처리해줍니다.
// 따라서 개발자는 트랜잭션 관리에 대한 복잡한 작업을 하지 않고도 안정적으로
// 데이터베이스 작업을 수행할 수 있습니다.


// IllegalStateException은 자바에서 제공하는 예외 클래스 중 하나로,
// 메서드가 호출될 때 상태(state)가 적절하지 않을 경우 발생합니다.
// 일반적으로 메서드를 호출하기 전에 필요한 준비 작업이나 조건을 만족시키지
// 않은 상태에서 메서드를 호출하면 IllegalStateException 예외가 발생합니다.
//예를 들어, 파일을 열기 위해 FileInputStream 객체를 생성한 후 read()
// 메서드를 호출하려면, 파일이 존재하고 읽기 권한이 있어야 합니다.
// 파일이 존재하지 않거나 읽기 권한이 없는 상태에서 read() 메서드를 호출하면
// IllegalStateException 예외가 발생합니다.
//IllegalStateException은 런타임 예외(Runtime Exception)의 하나이기 때문에,
// 예외 처리를 강제하지 않습니다. 그러나 이 예외를 발생시키는 메서드를
// 사용할 때는 반드시 예외 처리를 해야 합니다.