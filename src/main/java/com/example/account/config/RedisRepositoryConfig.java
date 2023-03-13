package com.example.account.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// 어노테이션을 사용하여 클래스를 선언하면 해당 클래스가 Spring의 구성 클래스임을 나타낼 수 있습니다.
@Configuration // 주입 하는 쪽
public class RedisRepositoryConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);

        return Redisson.create(config);
    }
}

//RedisRepositoryConfig 클래스는 RedissonClient를 생성하는 데
// 사용됩니다. 이 클래스는 Redis 호스트 및 포트를 가져와 Redisson
// 클라이언트를 구성합니다. 이 클래스에서 생성된 RedissonClient는
// 다른 Spring 구성 요소에서 사용할 수 있습니다.