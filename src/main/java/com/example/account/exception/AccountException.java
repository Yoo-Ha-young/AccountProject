package com.example.account.exception;

import com.example.account.type.ErrorCode;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public AccountException(ErrorCode userNotFound) {
        this.errorCode = userNotFound; // 에러코드 : userNotFound
        this.errorMessage = errorCode.getDescription();
    }
}


//public class MyException extends Exception {
//    private int errorCode;
//    public MyException(String message, int errorCode) {
//        super(message);
//        this.errorCode = errorCode;
//    }
//    public int getErrorCode() {
//        return errorCode;
//    }
//}

//자바에서 커스텀 예외 클래스를 만드는 방법은 다음과 같습니다.
//1.Exception 클래스를 상속받은 커스텀 예외 클래스를 만듭니다.
//2.생성자를 정의하여 예외 메시지를 설정합니다.
//3.필요에 따라 예외에 대한 추가 정보를 저장하는 멤버 변수를 선언합니다.
//4.필요한 경우 예외를 던지는 메서드를 작성합니다.
//예를 들어, 다음과 같이 MyException이라는 커스텀 예외 클래스를 만들 수 있습니다.

//이 커스텀 예외 클래스는 예외 메시지와 에러 코드를 저장할 수 있습니다.
// 따라서 이 클래스를 사용하여 예외를 던질 때, 예외 메시지와 함께 추가적인 정보를
// 전달할 수 있습니다. 이를 통해 디버깅 및 예외 처리 작업이 더욱 쉬워집니다.
//커스텀 예외 클래스를 사용하는 가장 큰 장점은 시스템에서 발생하는 다양한 예외를
// 구체적으로 구분할 수 있다는 것입니다. 예를 들어, 데이터베이스 예외, 네트워크 예외,
// 파일 처리 예외 등 각각의 예외에 대해 별도의 커스텀 예외 클래스를 정의하여
// 사용할 수 있습니다. 이렇게 하면 예외 처리 코드를 더욱 정확하고 간결하게 작성할
// 수 있으며, 유지 보수성과 코드 가독성을 높일 수 있습니다.