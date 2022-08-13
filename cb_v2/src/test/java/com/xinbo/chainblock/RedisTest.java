package com.xinbo.chainblock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author tony
 * @date 6/25/22 4:22 下午
 * @desc file desc
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Test
    public void test() {
        redisTemplate.opsForValue().set("a", "b");

        Long a = redisTemplate.opsForValue().increment("b", 1L);
        redisTemplate.expire("b", 100, TimeUnit.SECONDS);
        System.out.println(a);


        Boolean b = redisTemplate.hasKey("b");
        System.out.println(b);


    }

}
