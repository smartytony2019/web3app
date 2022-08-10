package com.xinbo.chainblock;

import com.xinbo.chainblock.core.algorithm.AlgorithmCode;
import com.xinbo.chainblock.core.algorithm.AlgorithmResult;
import com.xinbo.chainblock.core.algorithm.HashAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author tony
 * @date 6/23/22 6:48 下午
 * @desc file desc
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class AlgorithmTest {

    private String openHash = "757f6cc9c06aaf1e50cc68773fcd4bd19afb92336247e28a1dc285a792751ef7";

    private String code() {
        int len = openHash.length();
        return openHash.substring(len-5, len);
    }

    private boolean isNumeric(String input) {
        return Pattern.compile("[0-9]").matcher(input).matches();
    }

    private int num(String[] codes) {
        int result = 0;
        for(String code : codes) {
            if(this.isNumeric(code)) {
                result += Integer.parseInt(code);
            } else {
                result += 10;
            }
        }

        if(result == 0) {
            throw new RuntimeException("Algorithm num method error");
        }
        return result%10;
    }

    @Test
    public void NiuNiu() {
        AlgorithmResult r = new AlgorithmResult();
        int len = openHash.length();
        String code = openHash.substring(len-5, len);
        String[] split = code.split("");

        String[] platformCode = Arrays.copyOfRange(split, 0, 3);
        String[] playerCode = Arrays.copyOfRange(split, 2, 5);

        int platformNum = this.num(platformCode);
        int playerNum = this.num(playerCode);
        System.out.println(platformNum);
        System.out.println(playerNum);


        //点数相同
        if(platformNum == playerNum) {
            if(platformNum>=1 && platformNum<=5) {
                r.setStatus(AlgorithmCode.LOST);
            } else {
                r.setStatus(AlgorithmCode.DRAW);
            }
        }


        if(platformNum > playerNum) {
            r.setStatus(AlgorithmCode.LOST);
        } else {
            r.setStatus(AlgorithmCode.WIN);
            r.setNum(playerNum);
        }

        System.out.println(r.toString());
    }


    @Autowired
    private HashAlgorithm algorithm;

    @Test
    public void tes2() {
        System.out.println("--------------");
        System.out.println(new Date());
    }

}
