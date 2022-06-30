package com.xinbo.chainblock.service;

import com.xinbo.chainblock.modal.Do.UserDo;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface UserService {

    boolean create();


    UserDo findById(int id);

    boolean increment(UserDo entity);

}
