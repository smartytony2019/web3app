package com.xinbo.chainblock.core.algorithm;

import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.terminal.HashResultApiEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author tony
 * @date 6/23/22 5:55 下午
 * @desc 彩票算法
 */
@Component
public class HashAlgorithm {

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


    private AlgorithmResult firstBall(HashResultEntity hr, HashBetEntity lb) {
        AlgorithmResult ar = AlgorithmResult.builder().build();

        String blockHash = hr.getBlockHash();
        Stack<String> stack = this.split(blockHash, true, 3);

        String ball = stack.pop();
        List<String> result = this.ballResult(ball);

        String playCodeNameDefault = lb.getContentZh();
        if (result.contains(playCodeNameDefault)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else {
            ar.setStatus(AlgorithmCode.LOST);
        }

        return ar;
    }


    private AlgorithmResult secondBall(HashResultEntity hr, HashBetEntity lb) {
        AlgorithmResult ar = AlgorithmResult.builder().build();

        String blockHash = hr.getBlockHash();
        Stack<String> stack = this.split(blockHash, true, 3);

        stack.pop();
        String ball = stack.pop();
        List<String> result = this.ballResult(ball);

        String playCodeNameDefault = lb.getContentZh();
        if (result.contains(playCodeNameDefault)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else {
            ar.setStatus(AlgorithmCode.LOST);
        }

        return ar;
    }


    private AlgorithmResult thirdBall(HashResultEntity hr, HashBetEntity lb) {
        AlgorithmResult ar = AlgorithmResult.builder().build();

        String blockHash = hr.getBlockHash();
        Stack<String> stack = this.split(blockHash, true, 3);

        stack.pop();
        stack.pop();
        String ball = stack.pop();
        List<String> result = this.ballResult(ball);

        String playCodeNameDefault = lb.getContentZh();
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
    private AlgorithmResult sumValue(HashResultEntity hr, HashBetEntity lb) {
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

        String playCodeNameDefault = lb.getContentZh();
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
    private AlgorithmResult dragonTiger(HashResultEntity hr, HashBetEntity lb) {
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

        String playCodeNameDefault = lb.getContentZh();
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
    private AlgorithmResult special(HashResultEntity hr, HashBetEntity lb) {
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


        String playCodeNameDefault = lb.getContentZh();
        if (result.equals(playCodeNameDefault)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else {
            ar.setStatus(AlgorithmCode.LOST);
        }

        return ar;
    }


    /**
     * 牛牛
     *
     * @param hr
     * @param lb
     * @return
     */
    private AlgorithmResult niuNiu(HashResultEntity hr, HashBetEntity lb) {
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

        String playCodeNameDefault = lb.getContentZh();
        if (result.contains(playCodeNameDefault)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else {
            ar.setStatus(AlgorithmCode.LOST);
        }
        return ar;
    }





    /**
     * 哈希两面
     *
     * @param hashResult
     * @param bet
     * @return
     */
    private AlgorithmResult hashComb(HashResultEntity hashResult, HashBetEntity bet) {
        AlgorithmResult ar = AlgorithmResult.builder().status(AlgorithmCode.LOST).build();

        String content = bet.getContentZh();
        Stack<String> stack = this.split(hashResult.getBlockHash(), true, 1);
        int num = Integer.parseInt(stack.pop());

        String r1 = num % 2 == 0 ? "双" : "单";
        String r2 = num >= 5 ? "大" : "小";

        List<String> result = Arrays.asList(r1, r2);
        if (result.contains(content)) {
            ar.setStatus(AlgorithmCode.WIN);
        }

        return ar;
    }

    /**
     * 哈希百家乐
     *
     * @param hashResult
     * @param bet
     * @return
     */
    private AlgorithmResult hashBjl(HashResultEntity hashResult, HashBetEntity bet) {
        AlgorithmResult ar = AlgorithmResult.builder().status(AlgorithmCode.LOST).build();

        String content = bet.getContentZh();
        Stack<String> stack = this.split(hashResult.getBlockHash(), true, 4);
        int num1 = Integer.parseInt(stack.pop());
        int num2 = Integer.parseInt(stack.pop());
        int num3 = Integer.parseInt(stack.pop());
        int num4 = Integer.parseInt(stack.pop());

        int zhuang = num1 + num2;   //庄
        int xian = num3 + num4;     //闲

        List<String> result = new ArrayList<>();
        if (num1 == num2) {
            result.add("庄对");
        }
        if (num3 == num4) {
            result.add("闲对");
        }
        if (zhuang > xian) {
            result.add("庄");
        } else if (zhuang < xian) {
            result.add("闲");
        } else {
            result.add("和");
        }

        if (result.contains(content)) {
            ar.setStatus(AlgorithmCode.WIN);
        } else if (zhuang == xian && Arrays.asList("庄", "闲").contains(content)) { //当会员投注 `庄` 或 `闲`, 开奖为和则为平局需退还本金
            ar.setStatus(AlgorithmCode.DRAW);
        }

        return ar;
    }

    /**
     * 哈希PK拾
     *
     * @param hashResult
     * @param bet
     * @return
     */
    private AlgorithmResult hashPK10(HashResultEntity hashResult, HashBetEntity bet) {
        AlgorithmResult ar = AlgorithmResult.builder().status(AlgorithmCode.LOST).build();

        String content = bet.getContentZh();
        Stack<String> stack = this.split(hashResult.getBlockHash(), true, 1);
        int num = Integer.parseInt(stack.pop());

        List<Integer> collect = Arrays.stream(content.split(",")).map(Integer::parseInt).collect(Collectors.toList());

        if (collect.contains(num)) {
            ar.setStatus(AlgorithmCode.WIN);
        }

        return ar;
    }


    /**
     * 幸运哈希
     *
     * @param hashResult
     * @param bet
     * @return
     */
    private AlgorithmResult luckyHash(HashResultEntity hashResult, HashBetEntity bet) {
        AlgorithmResult ar = AlgorithmResult.builder().status(AlgorithmCode.LOST).build();

        Stack<String> stack = this.split(hashResult.getBlockHash(), false, 2);
        String s1 = stack.pop();
        String s2 = stack.pop();

        if (isNumeric(s1) != isNumeric(s2)) {
            ar.setStatus(AlgorithmCode.WIN);
        }

        return ar;
    }

    /**
     * 哈希牛牛
     *
     * @param hashResult
     * @param bet
     * @return
     */
    private AlgorithmResult hashBull(HashResultEntity hashResult, HashBetEntity bet) {
        AlgorithmResult ar = AlgorithmResult.builder().status(AlgorithmCode.LOST).build();

        Stack<String> stack = this.split(hashResult.getBlockHash(), false, 5);
        String s1 = stack.pop();
        String s2 = stack.pop();
        String s3 = stack.pop();
        String s4 = stack.pop();
        String s5 = stack.pop();

        int num1 = isNumeric(s1) ? Integer.parseInt(s1) : 10;
        int num2 = isNumeric(s2) ? Integer.parseInt(s2) : 10;
        int num3 = isNumeric(s3) ? Integer.parseInt(s3) : 10;
        int num4 = isNumeric(s4) ? Integer.parseInt(s4) : 10;
        int num5 = isNumeric(s4) ? Integer.parseInt(s5) : 10;

        int zhuang = (num1 + num2 + num3) % 10;     //庄
        int xian = (num3 + num4 + num5) % 10;       //闲

        String rr = "";
        if (zhuang > xian) {
            rr = "庄";
        } else if (zhuang < xian) {
            rr = "闲";
        } else {
            rr = "和";
        }

        String tt = "";
        String content = bet.getContent();
        if (Arrays.asList("400214", "400215", "400216").contains(content)) {
            tt = "庄";
            ar.setNum(zhuang);
        }
        if (Arrays.asList("400210", "400211", "400212").contains(content)) {
            tt = "闲";
            ar.setNum(xian);
        }
        if (Objects.equals("400213", content)) {
            tt = "和";
        }

        if (rr.equals(tt)) {
            ar.setStatus(AlgorithmCode.WIN);
        }
        return ar;
    }


    public AlgorithmResult settle(HashResultEntity hashResult, HashBetEntity bet) {
        switch (bet.getAlgorithm()) {
            case "1000":    //哈希两面
                return this.hashComb(hashResult, bet);

            case "2000":    //哈希百家乐
                return this.hashBjl(hashResult, bet);

            case "3000":    //哈希PK拾
                return this.hashPK10(hashResult, bet);

            case "4000":    //幸运哈希
                return this.luckyHash(hashResult, bet);

            case "5000":    //哈希牛牛
                return this.hashBull(hashResult, bet);

            default:
                return AlgorithmResult.builder().build();
        }

    }


}
