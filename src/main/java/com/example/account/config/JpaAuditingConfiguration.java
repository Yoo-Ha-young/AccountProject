package com.example.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// @EnableJpaAuditing은 스프링 데이터 JPA에서
// 엔티티를 생성, 수정, 삭제할 때 자동으로 일부 정보를
// 기록하는 기능을 활성화하는 애노테이션입니다.
// 이 애노테이션을 사용하면 엔티티 클래스에서
// @CreatedDate, @LastModifiedDate 애노테이션을 사용하여
// 엔티티의 생성일자와 마지막 수정일자를 저장할 수 있습니다.
// 또한 AuditorAware 인터페이스를 구현하여 엔티티의 생성자
// 또는 수정자 정보를 자동으로 저장할 수 있습니다.
// 이를 통해 엔티티의 변경 이력을 추적하거나,
// 생성자/수정자 정보를 활용하는 등의 다양한 용도로 활용할 수 있습니다.
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
}
