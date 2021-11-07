package com.geek.week7.batchinsert;

import org.springframework.util.StopWatch;

/**
 * 作业1
 * @author 吴振
 * @since 2021/11/8 上午2:06
 */
public class TestMain {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        new JdbcService().doInsert();
        stopWatch.stop();

        stopWatch.start();
        new JdbcService().doAddBatch();
        stopWatch.stop();

        stopWatch.start();
        new JdbcService().doAddBatchByStep(100);
        stopWatch.stop();
    }
}

