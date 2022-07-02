package com.xinbo.chainblock.core.algorithm;

import com.xinbo.chainblock.entity.HashResultEntity;
import com.xinbo.chainblock.entity.LotteryBetEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author tony
 * @date 6/23/22 5:55 下午
 * @desc 彩票算法
 */
@Component
public class LotteryAlgorithm {

    private static final String REGEX = "";
    private static final Integer MIDDLE = 5;
    private static final String BIG = "大";
    private static final String SMALL = "小";
    private static final String ODD = "单";
    private static final String EVEN = "双";
    private static final String BIG_ODD = "大单";
    private static final String BIG_EVEN = "大双";
    private static final String SMALL_ODD = "小单";
    private static final String SMALL_EVEN = "小双";

    private static final Integer SUM_MIDDLE = 13;  //和值中间数
    private static final String SUM_BIG = "总和大";
    private static final String SUM_SMALL = "总和小";
    private static final String SUM_ODD = "总和单";
    private static final String SUM_EVEN = "总和双";

    private static final String DRAGON = "龙";
    private static final String TIGER = "虎";
    private static final String DRAW = "和";
    private static final String BAOZI = "豹子";
    private static final String SHUNZI = "顺子";
    private static final String BANSHUN = "半顺";
    private static final String DUIZI = "对子";
    private static final String ZALIU = "杂六";

    private static final String NIU_NIU = "牛牛";
    private static final String NIU_BIG = "牛大";
    private static final String NIU_SMALL = "牛小";
    private static final String NIU_EVEN = "牛双";
    private static final String NIU_ODD = "牛单";


    private Stack<String> split(String code, boolean isOnlyNum, int len) {
        Stack<String> s1 = new Stack<>();
        String[] codes = code.split(REGEX);
        for (String c : codes) {
            s1.push(c);
        }

        Stack<String> s2 = new Stack<>();
        while (true) {
            if (s1.size() <= 0) {
                break;
            }
            String ele = s1.pop();
            boolean isNum = this.isNumeric(ele);
            if (!isOnlyNum) {
                s2.push(ele);
            } else if (isNum) {
                s2.push(ele);
            }

            if (s2.size() == len) {
                break;
            }
        }

        if (s2.size() != len) {
            throw new RuntimeException("split hash code exception");
        }

        return s2;
    }

    private boolean isNumeric(String input) {
        return Pattern.compile("[0-9]").matcher(input).matches();
    }


    private List<String> ballResult(String ball) {
        List<String> result = new ArrayList<>();
        int ballNum = Integer.parseInt(ball);
        result.add(ball);
        result.add(ballNum >= MIDDLE ? BIG : SMALL);
        result.add(ballNum % 2 == 0 ? EVEN : ODD);

        if (ballNum >= MIDDLE) {
            result.add(ballNum % 2 == 0 ? BIG_EVEN : BIG_ODD);
        } else {
            result.add(ballNum % 2 == 0 ? SMALL_EVEN : SMALL_ODD);
        }

        return result;
    }


    private AlgorithmResult firstBall(HashResultEntity hr, LotteryBetEntity lb) {
        AlgorithmResult ar = AlgorithmResult.builder().build();

        String blockHash = hr.getBlockHash();
        Stack<String> stack = this.split(blockHash, true, 3);

        String ball = stack.pop();
        List<String> result = this.ballResult(ball);

        String playCodeNameDefault = lb.getPlayCodeNameDefault();
        if (result.contains(playCodeNameDefault)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else {
            ar.setStatus(AlgorithmCode.LOST);
        }

        return ar;
    }


    private AlgorithmResult secondBall(HashResultEntity hr, LotteryBetEntity lb) {
        AlgorithmResult ar = AlgorithmResult.builder().build();

        String blockHash = hr.getBlockHash();
        Stack<String> stack = this.split(blockHash, true, 3);

        stack.pop();
        String ball = stack.pop();
        List<String> result = this.ballResult(ball);

        String playCodeNameDefault = lb.getPlayCodeNameDefault();
        if (result.contains(playCodeNameDefault)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else {
            ar.setStatus(AlgorithmCode.LOST);
        }

        return ar;
    }


    private AlgorithmResult thirdBall(HashResultEntity hr, LotteryBetEntity lb) {
        AlgorithmResult ar = AlgorithmResult.builder().build();

        String blockHash = hr.getBlockHash();
        Stack<String> stack = this.split(blockHash, true, 3);

        stack.pop();
        stack.pop();
        String ball = stack.pop();
        List<String> result = this.ballResult(ball);

        String playCodeNameDefault = lb.getPlayCodeNameDefault();
        if (result.contains(playCodeNameDefault)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else {
            ar.setStatus(AlgorithmCode.LOST);
        }

        return ar;
    }

    /**
     * 合值
     *
     * @param hr
     * @param lb
     * @return
     */
    private AlgorithmResult sumValue(HashResultEntity hr, LotteryBetEntity lb) {
        AlgorithmResult ar = AlgorithmResult.builder().build();

        String blockHash = hr.getBlockHash();
        Stack<String> stack = this.split(blockHash, true, 3);

        int ball1 = Integer.parseInt(stack.pop());
        int ball2 = Integer.parseInt(stack.pop());
        int ball3 = Integer.parseInt(stack.pop());

        int sum = ball1 + ball2 + ball3;

        List<String> result = new ArrayList<>();
        result.add(String.valueOf(sum));
        result.add(sum % 2 == 0 ? SUM_EVEN : SUM_ODD);
        result.add(sum <= SUM_MIDDLE ? SUM_SMALL : SUM_BIG);

        String playCodeNameDefault = lb.getPlayCodeNameDefault();
        if (result.contains(playCodeNameDefault)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else {
            ar.setStatus(AlgorithmCode.LOST);
        }

        return ar;
    }


    /**
     * 龙虎
     *
     * @param hr
     * @param lb
     * @return
     */
    private AlgorithmResult dragonTiger(HashResultEntity hr, LotteryBetEntity lb) {
        AlgorithmResult ar = AlgorithmResult.builder().build();

        String blockHash = hr.getBlockHash();
        Stack<String> stack = this.split(blockHash, true, 3);

        int ball1 = Integer.parseInt(stack.pop());
        stack.pop();
        int ball3 = Integer.parseInt(stack.pop());

        String result = "";
        if (ball1 == ball3) {
            result = DRAW;
        } else if (ball1 > ball3) {
            result = DRAGON;
        } else {
            result = TIGER;
        }

        String playCodeNameDefault = lb.getPlayCodeNameDefault();
        if (result.equals(playCodeNameDefault)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else {
            ar.setStatus(AlgorithmCode.LOST);
        }

        return ar;
    }


    /**
     * 龙虎
     *
     * @param hr
     * @param lb
     * @return
     */
    private AlgorithmResult special(HashResultEntity hr, LotteryBetEntity lb) {
        AlgorithmResult ar = AlgorithmResult.builder().build();

        String blockHash = hr.getBlockHash();
        Stack<String> stack = this.split(blockHash, true, 3);

        int ball1 = Integer.parseInt(stack.pop());
        int ball2 = Integer.parseInt(stack.pop());
        int ball3 = Integer.parseInt(stack.pop());

        List<Integer> sorted = Arrays.asList(ball1, ball2, ball3);
        List<Integer> collect = sorted.stream().sorted().collect(Collectors.toList());

        ball1 = collect.get(0);
        ball2 = collect.get(1);
        ball3 = collect.get(2);

        int middle = (int) Math.ceil((double) (ball1 + ball3) / 2);
        String tmp = String.format("%s%s%s", ball1, ball2, ball3);

        String result = "";
        if (ball1 == ball2 && ball1 == ball3) {
            result = BAOZI;
        } else if (middle == ball2 || tmp.equals("089") || tmp.equals("019")) {
            result = SHUNZI;
        } else if (ball1 == ball2 || ball1 == ball3 || ball2 == ball3) {
            result = DUIZI;
        } else if ((ball2 - ball1) == 1 || (ball3 - ball2) == 1 || (ball3 - ball1) == 9) {
            result = BANSHUN;
        } else {
            result = ZALIU;
        }


        String playCodeNameDefault = lb.getPlayCodeNameDefault();
        if (result.equals(playCodeNameDefault)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else {
            ar.setStatus(AlgorithmCode.LOST);
        }

        return ar;
    }


    /**
     * 牛牛
     * @param hr
     * @param lb
     * @return
     */
    private AlgorithmResult niuNiu(HashResultEntity hr, LotteryBetEntity lb) {
        AlgorithmResult ar = AlgorithmResult.builder().build();

        String blockHash = hr.getBlockHash();
        Stack<String> stack = this.split(blockHash, true, 3);
        List<String> list = Arrays.asList(stack.pop(), stack.pop(), stack.pop());
        Integer reduce = list.stream().map(Integer::parseInt).reduce(0, Integer::sum);
        List<String> result = new ArrayList<>();

        int num = reduce % 10;
        result.add(num == 0 ? NIU_NIU : String.format("牛%s", num));
        result.add(num % 2 == 0 ? NIU_EVEN : NIU_ODD);
        result.add(num >= 1 && num <= 5 ? NIU_SMALL : NIU_BIG);

        String playCodeNameDefault = lb.getPlayCodeNameDefault();
        if (result.contains(playCodeNameDefault)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else {
            ar.setStatus(AlgorithmCode.LOST);
        }
        return ar;
    }



    public AlgorithmResult settle(HashResultEntity hr, LotteryBetEntity lb) {
        switch (lb.getPlayId()) {
            case 1:
                return this.firstBall(hr, lb);
            case 2:
                return this.secondBall(hr, lb);
            case 3:
                return this.thirdBall(hr, lb);
            case 4:
                return this.sumValue(hr, lb);
            case 5:
                return this.dragonTiger(hr, lb);
            case 6:
                return this.special(hr, lb);
            case 7:
                return this.niuNiu(hr, lb);
            default:
                return AlgorithmResult.builder().build();
        }
    }


}
