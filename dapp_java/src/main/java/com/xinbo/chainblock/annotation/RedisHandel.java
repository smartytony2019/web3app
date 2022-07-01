package com.xinbo.chainblock.annotation;

import com.xinbo.chainblock.vo.BetSubmitVo;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ ElementType.FIELD, ElementType.METHOD ,ElementType.TYPE_USE,ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisHandel {
    String key() default "";

    String keyField() default "username";


    String[] kk() default {};
}
