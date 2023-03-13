package com.example.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisTestService {
    private final RedissonClient redissonClient;
    // RedisRepositoryConfig 클래스에 있는 RedissonClient에 Bean이 달려져 있고,
    // 이 Bean이 이곳에 주입된다.

    public String getLock(){
        RLock lock = redissonClient.getLock("sampleLock");

        try{                               // 락을 1초 동안 찾고, 3초 동안 갖고 있다가 풀어줌
            boolean isLock = lock.tryLock(1,5, TimeUnit.SECONDS);
            if(!isLock){
                log.error("=====Lock acquisition failed=====");
                return "Lock failed";
            }
        } catch (Exception e){
            log.error("Redis Lock failed");
        }
        return "Lock success";
    }
}