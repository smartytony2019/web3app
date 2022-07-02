package com.xinbo.chainblock.core.algorithm;

import com.xinbo.chainblock.entity.HashResultEntity;
import com.xinbo.chainblock.entity.LotteryBetEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * @author tony
 * @date 6/23/22 5:55 下午
 * @desc 彩票算法
 */
@Component
public class LotteryAlgorithm {

    private static final String REGEX = "";

    private Stack<String> split(String code, boolean isOnlyNum, int len) {
        Stack<String> s1 = new Stack<>();
        String[] codes = code.split(REGEX);
        for(String c : codes) {
            s1.push(c);
        }

        Stack<String> s2 = new Stack<>();
        while(true) {
            if(s1.size()<=0) {
                break;
            }
            String ele = s1.pop();
            boolean isNum = this.isNumeric(ele);
            if(!isOnlyNum) {
                s2.push(ele);
            } else if(isNum) {
                s2.push(ele);
            }

            if(s2.size() == len) {
                break;
            }
        }

        if(s2.size() != len) {
            throw new RuntimeException("split hash code exception");
        }

        return s2;
    }

    private boolean isNumeric(String input) {
        return Pattern.compile("[0-9]").matcher(input).matches();
    }


    private AlgorithmResult firstBall(HashResultEntity hr, LotteryBetEntity lb) {

        String blockHash = hr.getBlockHash();
        Stack<String> stack = this.split(blockHash, true, 3);
        return AlgorithmResult.builder().build();
    }


    private AlgorithmResult secondBall(HashResultEntity hr, LotteryBetEntity lb) {
        return AlgorithmResult.builder().build();
    }

    public AlgorithmResult settle(HashResultEntity hr, LotteryBetEntity lb) {
        switch (lb.getPlayId()) {
            case 1:
                return this.firstBall(hr,lb);
            case 2:
                return this.secondBall(hr,lb);
        }

        return AlgorithmResult.builder().build();
    }


}
