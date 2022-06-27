package com.xinbo.chainblock;

import cn.hutool.core.date.*;
import com.xinbo.chainblock.entity.ExpectEntity;
import com.xinbo.chainblock.service.ExpectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class ExpectTest {

    @Autowired
    private ExpectService expectService;


    @Test
    public void gen1() {
        String d = "2022-06-26 00:00:00";
        Date date = DateUtil.parse(d);

        int gameId = 1;
        int minute = 1;
        int end = 60 * 24;

        int flag = 1;
        do {
            DateTime offset = DateUtil.offset(date, DateField.MINUTE, minute);

            String num = String.format("%04d", flag);
            String startTime = DateUtil.format(date, "HH:mm:ss");
            String endTime = DateUtil.format(offset, "HH:mm:ss");
            System.out.println(num + " - " + startTime + " - " + endTime);

            ExpectEntity entity = ExpectEntity.builder()
                    .gameId(gameId)
                    .num(num)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
            expectService.insert(entity);

            date = DateUtil.date(offset);
            flag += 1;
        } while (flag <= end);
    }

    @Test
    public void gen3() {
        String d = "2022-06-26 00:00:00";
        Date date = DateUtil.parse(d);

        int gameId = 2;
        int minute = 3;
        int end = 60 * 24 / minute;
        int flag = 1;

        do {
            DateTime offset = DateUtil.offset(date, DateField.MINUTE, minute);


            String num = String.format("%04d", flag);
            String startTime = DateUtil.format(date, "HH:mm:ss");
            String endTime = DateUtil.format(offset, "HH:mm:ss");
            System.out.println(num + " - " + startTime + " - " + endTime);

            ExpectEntity entity = ExpectEntity.builder()
                    .gameId(gameId)
                    .num(num)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
            expectService.insert(entity);

            date = DateUtil.date(offset);
            flag += 1;
        } while (flag <= end);
    }

    @Test
    public void gen5() {
        String d = "2022-06-26 00:00:00";
        Date date = DateUtil.parse(d);

        int gameId = 3;
        int minute = 5;
        int end = 60 * 24 / minute;
        int flag = 1;
        do {
            DateTime offset = DateUtil.offset(date, DateField.MINUTE, minute);

            String num = String.format("%04d", flag);
            String startTime = DateUtil.format(date, "HH:mm:ss");
            String endTime = DateUtil.format(offset, "HH:mm:ss");
            System.out.println(num + " - " + startTime + " - " + endTime);

            ExpectEntity entity = ExpectEntity.builder()
                    .gameId(gameId)
                    .num(num)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
            expectService.insert(entity);


            date = DateUtil.date(offset);
            flag += 1;
        } while (flag <= end);
    }
}

