package com.xinbo.chainblock.service.impl;

import com.xinbo.chainblock.consts.PermissionConst;
import com.xinbo.chainblock.service.AdminUserService;
import org.springframework.stereotype.Service;

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
