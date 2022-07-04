package com.xinbo.chainblock.consts;

/**
 * @author tony
 * @date 6/24/22 6:29 下午
 * @desc file desc
 */
public interface StatusCode {
    int SUCCESS = 0;

    int FAILURE = 1000;

    int SITE_MAINTAIN = 10001;         //站点维护
    int SPORT_FINALIZE = 10001;        //体育封盘

    int UNAUTHORIZED=2000;
    int EXCEPTION=3000;

    int REGISTER_ERROR = 5000;
}
