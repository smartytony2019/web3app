package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.CategoryDto;
import com.xinbo.chainblock.dto.GameDto;
import com.xinbo.chainblock.entity.CategoryEntity;
import com.xinbo.chainblock.entity.GameEntity;
import com.xinbo.chainblock.service.CategoryService;
import com.xinbo.chainblock.service.GameService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("ApiCategoryController")
@RequestMapping("api/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GameService gameService;


    @JwtIgnore
    @Operation(summary = "findAll", description = "查找所有")
    @GetMapping("findAll")
    public R<Object> findAll() {
        List<CategoryEntity> cateList = categoryService.findAll();
        List<CategoryDto> cates = MapperUtil.many(cateList, CategoryDto.class);

        List<GameEntity> gameList = gameService.findAll();
        List<GameDto> games = MapperUtil.many(gameList, GameDto.class);

        for (CategoryDto cate : cates) {
            List<GameDto> collect = games.stream().filter(f -> f.getCateId().equals(cate.getId())).collect(Collectors.toList());
            cate.setList(collect);
        }

        return R.builder().code(StatusCode.SUCCESS).data(cates).build();
    }

}
