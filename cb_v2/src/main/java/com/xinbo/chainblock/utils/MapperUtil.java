package com.xinbo.chainblock.utils;

import cn.hutool.core.convert.Convert;
import com.xinbo.chainblock.annotation.Translate;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author tony
 * @date 6/30/22 6:42 下午
 * @desc file desc
 */
@Component
public class MapperUtil {

    private static Mapper mapper = new DozerBeanMapper();

    /**
     * domain转换dto
     *
     * @param source   domain类
     * @param target   dto类
     * @param <Source>
     * @param <Target>
     * @return
     */
    public static <Source, Target> Target to(Source source, Class<Target> target) {
        if (ObjectUtils.isEmpty(source)) {
            return null;
        }
        Target convert = Convert.convert(target, source);
        return translate(convert);
    }

    /**
     * domain转换dto(集合转换)
     *
     * @param source   domain类
     * @param target   dto类
     * @param <Source>
     * @param <Target>
     * @return
     */
    public static <Source, Target> List<Target> many(List<Source> source, Class<Target> target) {
        if (ObjectUtils.isEmpty(source)) {
            return null;
        }

        if (source.size() == 0) {
            return new ArrayList<>();
        }

        return source.stream().map(s -> translate(mapper.map(s, target))).collect(Collectors.toList());
    }


    private static <T> T translate(T t) {
        Field[] fields = t.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) { // 遍历字段数组
            fields[i].setAccessible(true);  // 将当前字段设置为可访问，不然后面就会报错
            Translate annotation = fields[i].getAnnotation(Translate.class); // 获得这个字段的注解
            Optional<Translate> annotationOptional = Optional.ofNullable(annotation);// 通过java8的Optional容器包裹
            if (annotationOptional.isPresent()) {    // 判断注解是否存在
                try {
                    Object o = fields[i].get(t);
                    String value = LanguageUtil.translate(o.toString());
                    fields[i].set(t, value);     // 给字段赋值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            fields[i].setAccessible(false);     // 重新将字段设置为不可访问
        }

        return t;
    }

}
