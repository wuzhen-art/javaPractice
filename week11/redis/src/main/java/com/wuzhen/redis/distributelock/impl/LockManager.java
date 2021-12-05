package com.wuzhen.redis.distributelock.impl;


import com.wuzhen.redis.distributelock.DistributeLock;

/**
 * 分布式锁管理
 *
 */
public class LockManager {

    public static DistributeLock getReentrantDistributeLock(String lockKey) {
        return new ReentrantDistributeLock(lockKey);
    }

}
