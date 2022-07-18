package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.consts.PermissionConst;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.ExpectEntity;
import com.xinbo.chainblock.mapper.AgentMapper;
import com.xinbo.chainblock.service.AdminUserService;
import com.xinbo.chainblock.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Override
    public List<String> findPermission() {
        return Arrays.asList(PermissionConst.test1);
    }
}
