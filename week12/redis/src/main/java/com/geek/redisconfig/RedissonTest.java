package com.geek.redisconfig;


import lombok.SneakyThrows;
import org.redisson.Redisson;
import org.redisson.RedissonMap;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author 吴振
 * @since 2021/12/12 下午8:58
 */
public class RedissonTest {

    @SneakyThrows
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        //config.useSingleServer().setPassword("");

        final RedissonClient client = Redisson.create(config);
        RMap<String, String> rmap = client.getMap("map1");
        RLock lock = client.getLock("lock1");

        try{
            lock.lock();

            for (int i = 0; i < 15; i++) {
                rmap.put("rkey:"+i, "rvalue:"+i);
            }

            // 如果代码块 W1 在这里会怎么样？
            // 无限循环，锁得不到释放

        }finally{
            lock.unlock();
        }

        // 代码块 W1
        while(true) {
            Thread.sleep(2000);
            System.out.println(rmap.get("rkey:10"));
        }

    }
}
