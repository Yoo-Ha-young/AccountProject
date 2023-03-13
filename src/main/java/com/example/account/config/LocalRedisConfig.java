package com.example.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Configuration
public class LocalRedisConfig {
// application.yml 에서 spring:
//  redis:
//    host: 127.0.0.1
//    port: 6379 의 설정, 값을 가져와서 사용하겠다는 뜻
    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis(){
        redisServer = new RedisServer(redisPort);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis(){
        if(redisServer != null){
            redisServer.stop();
        }
    }
}

//LocalRedisConfig 클래스는 Embedded Redis 서버를 시작하고 중지하는 방법을
// 정의합니다. 이 클래스는 RedissonClient를 생성하지 않으며,
// RedissonClient를 사용하기 위해 RedisRepositoryConfig
// 클래스에서 주입받습니다.
