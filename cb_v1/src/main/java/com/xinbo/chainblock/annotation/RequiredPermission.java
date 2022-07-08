package com.xinbo.chainblock.annotation;


import com.xinbo.chainblock.enums.PermissionCodeEnum;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {
    PermissionCodeEnum value();
}
