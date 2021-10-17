package java0;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RecursiveTask;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    public static void main(String[] args) {
        runWithExecutor();
        runWithFutureTask();
        runWithCompletableFuture();
        runWithForkJoinPool();
        runWithBlokeQueue();
    }

    private static void runWithBlokeQueue() {
        long start=System.currentTimeMillis();

        ArrayBlockingQueue<Integer> integers = new ArrayBlockingQueue<Integer>(1);
        new Thread(() -> {
            integers.add(sum());
        }).start();

        Integer take = 0;
        try {
            take = integers.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("bloke queue + " + take);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

    }

    private static void runWithForkJoinPool() {
        long start=System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        Integer invoke = forkJoinPool.invoke(new RecursiveTask<Integer>() {
            @Override
            protected Integer compute() {
                return sum();
            }
        });

        System.out.println("fork join pool结果" + invoke);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    private static void runWithCompletableFuture() {
        long start=System.currentTimeMillis();
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(Homework03::sum);
        try {
            System.out.println("completableFuture 结果 = " + completableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 确保  拿到result 并输出

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    private static void runWithFutureTask() {
        long start=System.currentTimeMillis();

        FutureTask<Integer> integerFutureTask = new FutureTask<>(Homework03::sum);
        new Thread(integerFutureTask).start();
        Integer integer = null;
        try {
            integer = integerFutureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("futureTask =" + integer);
        // 确保  拿到result 并输出

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    private static void runWithExecutor() {
        long start=System.currentTimeMillis();

        Future<Integer> result = executorService.submit(Homework03::sum);

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        try {
            if (result.isDone()) {
                System.out.println("第一次获取结果：" + result.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("第二次获取结果：" + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 确保  拿到result 并输出

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return fibo(35);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
