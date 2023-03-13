package com.example.account.controller;

import com.example.account.domain.Account;
import com.example.account.dto.AccountDto;
import com.example.account.dto.AccountInfo;
import com.example.account.dto.CreateAccount;
import com.example.account.dto.DeleteAccount;
import com.example.account.service.AccountService;
import com.example.account.service.RedisTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccountController {
    // accountService를 불러와서
    private final AccountService accountService;
    private final RedisTestService redisTestService;

    // POST /account
    //- 파라미터 : 사용자 아이디, 초기 잔액
    //- 정책 : 사용자가 없는 경우, 계좌가 10개(사용자당 최대 보유 가능 계좌 수)인 경우 실패 응답
    //- 성공 응답 : 사용자 아이디, 계좌번호, 등록일시
    @PostMapping("/account") // url 파라미터와 맵핑 해당 url로 들어가면 실행 메서드가 된다
    public CreateAccount.Response createAccount(
            @RequestBody @Valid CreateAccount.Request request) { // 해당 메서드로
        return CreateAccount.Response.from(
                accountService.createAccount(
                        request.getUserId(),
                        request.getInitialBalance())
        ); // accountService를 불러와서 생성을 시킴
    } // createAccount API

    //- DELETE /account
    //파라미터 : 사용자 아이디, 계좌 번호
    //- 정책 : 사용자 또는 계좌가 없는 경우, 사용자 아이디와 계좌 소유주가 다른 경우,
    //계좌가 이미 해지 상태인 경우, 잔액이 있는 경우 실패 응답
    //성공 응답 : 사용자 아이디, 계좌번호, 해지일시
    @DeleteMapping("/account") // url 파라미터와 맵핑 해당 url로 들어가면 실행 메서드가 된다
    public DeleteAccount.Response deleteAccount(
            @RequestBody @Valid DeleteAccount.Request request) { // 해당 메서드로
        return DeleteAccount.Response.from(
                accountService.deleteAccount(
                        request.getUserId(),
                        request.getAccountNumber())
        ); // accountService를 불러와서 생성을 시킴
    } // deleteAccount API

    //    계좌 확인 API
    //    검토한 정보
    //    GET / account?user_id={userId}
    //- 파라미터 : 사용자 아이디
    //    정책 : 사용자 없는 경우 실패 응답
    //    성공 응답 : List<계좌번호, 잔액> 구조로 응답(사용 중인 계좌만)
    //
    //    상세 검토
    //- 저장이 필요한 정보는 없음
    //- 요청/응답 구조
    //    요청 : GET /account?user_id={userId}
    @GetMapping("/account?user_id={userId}")
    public List<AccountInfo> getAccountsByUserId(
            @RequestParam("user_id") Long userId){

        return accountService.getAccountByUserId(userId)
                .stream().map(accountDto ->
                        AccountInfo.builder()
                .accountNumber(accountDto.getAccountNumber())
                .balance(accountDto.getBalance())
                .build())
                .collect(Collectors.toList());
    }

    // @RequestBody 애노테이션은 HTTP 요청의 body 부분을 자바 객체로 변환해주는
    // 역할을 합니다. 즉, 클라이언트가 전송한 JSON, XML 등의 데이터를 자바 객체로
    // 매핑해줍니다. 이를 통해 컨트롤러에서 자바 객체를 직접 사용할 수 있으며,
    // 해당 객체를 이용하여 로직을 처리할 수 있습니다.
    // 예를 들어, 다음과 같이 @RequestBody
    // 애노테이션을 사용하여 JSON 데이터를 매핑할 수 있습니다.

    // @Valid 애노테이션은 자바 Bean Validation(JSR-303)의 애노테이션 중 하나로,
    // 해당 객체가 유효한지 검사하는 역할을 합니다. 이 애노테이션을 사용하면
    // 컨트롤러에서 전달받은 객체의 유효성 검사를 쉽게 할 수 있습니다.
    // 예를 들어, 다음과 같이 @Valid 애노테이션을 사용하여 유효성 검사를 할 수 있습니다.
    // 만약 유효하지 않은 데이터가 전송된 경우 MethodArgumentNotValidException 예외 발생

    @GetMapping("/get-lock")
    public String getLock(){
        return redisTestService.getLock();
    }

    //- GET /account?user_id={userId}
    //- 파라미터 : 사용자 아이디
    //- 정책 : 사용자 없는 경우 실패 응답
    //- 성공 응답 : List<계좌번호, 잔액> 구조로 응답(사용 중인 계좌만
    @GetMapping("/account/{id}")
    public Account getAccount(
            @PathVariable Long id){
        return accountService.getAccount(id); // accountService를 불러와서 id로 값을 가져옴
    }

}
