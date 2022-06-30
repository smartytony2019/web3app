package com.xinbo.chainblock.utils;

import cn.hutool.core.convert.Convert;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tony
 * @date 6/30/22 6:42 下午
 * @desc file desc
 */
public class MapperUtil {
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
        return Convert.convert(target, source);
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
        if (source == null) {
            return null;
        }
        if (source.size() == 0) {
            return new ArrayList<>();
        }
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        return source.stream().map(s -> mapper.map(s, target)).collect(Collectors.toList());
    }
}
