package com.xinbo.chainblock.annotation;

import java.lang.annotation.*;

/**
 * @author tony
 * @date 2022/6/28 20:00
 * @desc 翻译注解
 */
@Documented
@Inherited
@Target({ ElementType.FIELD, ElementType.METHOD ,ElementType.TYPE_USE,ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Translate {
}
