package com.example.account.repository;

import com.example.account.domain.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountUserRepository extends JpaRepository<AccountUser, Long> {

}


// @Repository는 스프링 프레임워크에서 제공하는 애노테이션 중 하나로,
// 데이터 액세스 레이어에 해당하는 클래스에 사용됩니다.

//@Repository 애노테이션이 적용된 클래스는 데이터베이스나 파일 시스템 등의
// 데이터 저장소에 접근하고 데이터를 읽고 쓰는 작업을 수행하는 클래스입니다.
// 이를 통해 개발자는 데이터 액세스 작업을 보다 쉽게 개발할 수 있으며,
// 스프링이 제공하는 트랜잭션 관리 기능과의 연동을 통해 데이터 액세스 작업을
// 보다 안전하게 처리할 수 있습니다.

// @Repository 애노테이션이 붙은 클래스는 다음과 같은 기능을 제공합니다.

//예외 변환: 데이터 액세스 작업 중 발생하는 예외를
// 스프링이 제공하는 일반적인 예외로 변환하여 던집니다.

// 트랜잭션 관리: @Transactional 애노테이션이 적용된 메서드를 호출할 때
// 트랜잭션을 시작하고 종료합니다.

// 의존성 주입: 스프링 프레임워크에서 관리하는 빈을
// 의존성 주입(Dependency Injection) 받을 수 있습니다.

// 데이터 액세스 작업: 데이터베이스나 파일 시스템 등의 데이터 저장소에
// 접근하고 데이터를 읽고 쓰는 작업을 수행합니다.
// 따라서 @Repository 애노테이션을 이용하여 데이터 액세스 레이어에
// 해당하는 클래스를 개발할 경우, 스프링에서 제공하는 다양한 기능을 활용하여
// 데이터 액세스 작업을 편리하게 개발할 수 있습니다.