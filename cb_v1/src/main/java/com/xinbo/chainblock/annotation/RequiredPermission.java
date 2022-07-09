package com.xinbo.chainblock.annotation;

import com.xinbo.chainblock.enums.PermissionCodeEnum;
import java.lang.annotation.*;

/**
 * @author tony
 * @date 2022/6/28 20:00
 * @desc 权限验证
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {
    PermissionCodeEnum value();
}
